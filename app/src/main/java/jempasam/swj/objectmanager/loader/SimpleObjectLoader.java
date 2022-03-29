package jempasam.swj.objectmanager.loader;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import jempasam.swj.data.chunk.DataChunk;
import jempasam.swj.data.chunk.ObjectChunk;
import jempasam.swj.data.chunk.ValueChunk;
import jempasam.swj.data.deserializer.DataDeserializer;
import jempasam.swj.logger.SLogger;
import jempasam.swj.objectmanager.ObjectManager;
import jempasam.swj.objectmanager.loader.tags.LoadableParameter;
import jempasam.swj.objectmanager.loader.tags.Loadable;
import jempasam.swj.parsing.ValueParser;
import jempasam.swj.reflection.ReflectionUtils;

public class SimpleObjectLoader<T> extends ObjectLoader<T>{
	
	protected Class<T> clazz;
	protected String prefix;
	protected ValueParser valueParser;
	
	
	public SimpleObjectLoader(ObjectManager<T> manager, SLogger logger, DataDeserializer deserializer, ValueParser valueParser, Class<T> clazz, String prefix) {
		super(manager, logger, deserializer);
		this.clazz=clazz;
		this.prefix=prefix;
		this.valueParser=valueParser;
	}

	
	@Override
	protected void interpret(ObjectChunk data) {
		for(DataChunk d : data) {
			logger.enter(data.getName());
			if(d instanceof ObjectChunk) {
				Object o=createObject((ObjectChunk)d, clazz);
				if(o!=null) {
					manager.register(d.getName(), (T)o);
					logger.log("RESULT: registred");
				}
				else logger.log("RESULT: Ignored");
			}
			logger.exit();
		}
	}
	
	private static class InsideException extends Exception{
		public InsideException(String message) { super(message); }
	}
	private interface ThrowingConsumer<T>{
		void accept(T t) throws Exception;
	}
	private interface ThrowingBiConsumer<T,Y>{
		void accept(T t, Y y) throws Exception;
	}
	private class ObjectParam{
		public Class<?> type=null;
		public ThrowingBiConsumer<Object,Object> setter=null;
	}
	private Object createObject(ObjectChunk data, Class<?> rootclass) {
		
		DataChunk classchunk=null;
		String classname="";
		Class<?> objectclass=null;
		Object newobject=null;
		ClassLoader loader=null;
		
		logger.enter(data.getName());
		try {
			loader=getClass().getClassLoader();
			
			//Get the object type
			objectclass=getType(data, rootclass);
			
			//Instantiate the object
			Constructor<?> constructor=objectclass.getDeclaredConstructor();
			constructor.setAccessible(true);
			newobject=constructor.newInstance();
			constructor.setAccessible(false);
			
			//Load parameters
			for(DataChunk d : data) {
				if(!d.getName().equals("type")) {
					logger.enter("parameter \""+d.getName()+"\"");
					try {
						//Get parameter setter and type
						ObjectParam param=getParameter(objectclass, d.getName());
						if(param==null)throw new NoSuchMethodException();
						
						//Set field value
						if(d instanceof ValueChunk) {
							ValueChunk oc=(ValueChunk)d;
							//Try to parse
							Object parsed=valueParser.parse(param.type, oc.getValue());
							if(parsed!=null) {
								param.setter.accept(newobject, parsed);
								logger.log("Parameter registred as \""+oc.getName()+"\"=\""+oc.getValue()+"\"");
							}
							else {
								if(param.type.isPrimitive()) throw new InsideException("Parameter \""+oc.getName()+"\" is of unparseable primitive type");
								Object loadedobj=manager.get(oc.getValue());
								if(loadedobj==null) throw new InsideException("The object \""+loadedobj+"\" does not exist");
								if(!param.type.isAssignableFrom(loadedobj.getClass())) throw new InsideException("The object \""+loadedobj+"\" is not of the right type.");
								param.setter.accept(newobject,loadedobj);
								logger.log("Parameter registred as \""+oc.getName()+"\"=Object named \""+oc.getValue()+"\"");
							}
						}else if(d instanceof ObjectChunk){
							//If is object
							if(param.type.isPrimitive() || param.type==String.class)
								throw new InsideException("This parameter should be primitive not an object.");
							Object obj=createObject((ObjectChunk)d, param.type);
							if(obj==null)throw new InsideException("Invalid object parameter");
							param.setter.accept(newobject,obj);
						}
						
					}catch(NoSuchMethodException e){
						logger.log("This parameter does not exist");
					}catch (NumberFormatException e) {
						logger.log("Primitive parameter value is malformed or not of the good type");
					}catch (InsideException e) {
						logger.log(e.getMessage());
					}catch(Exception e) {
						logger.log("Unexpexted error of type \""+e.getClass().getName()+"\"");
						e.printStackTrace();
					}
					logger.exit();
				}
			}
		} catch (ClassNotFoundException e) {
			logger.log(e.getMessage());
		} catch(NoSuchMethodException | InstantiationException e) {
			logger.log("The type \""+classname+"\" is not instantiable");
		} catch (Exception e) {
			logger.log("Unexpexted error of type \""+e.getClass().getName()+"\"");
			e.printStackTrace();
		}
		
		logger.exit();
		
		//Init
		try {
			for(Method m : ReflectionUtils.getAllMethods(objectclass)) {
				if(m.getName().equals("init") && m.isAnnotationPresent(LoadableParameter.class) && m.getParameterCount()==0) {
					m.setAccessible(true);
						m.invoke(newobject);
					m.setAccessible(false);
				}
			}
		}catch(Exception e) {}
		
		return newobject;
	}
	
	private ObjectParam getParameter(Class<?> type, String name) {
		ObjectParam ret=new ObjectParam();
		//Get field
		for(Field f : ReflectionUtils.getAllFields(type)) {
			if(f.isAnnotationPresent(LoadableParameter.class) && (name.equals(f.getAnnotation(LoadableParameter.class).name()) || name.equals(f.getName())) ){
				ret.setter=(o,v)->{
					f.setAccessible(true);
					f.set(o, v);
					f.setAccessible(false);
				};
				ret.type=f.getType();
				return ret;
			}
		}
		
		//Get method
		for(Method m : ReflectionUtils.getAllMethods(type)) {
			if(		m.isAnnotationPresent(LoadableParameter.class) &&
					(name.equals(m.getAnnotation(LoadableParameter.class).name()) || name.equals(m.getName())) &&
					m.getParameterCount()==1
					){
				ret.setter=(o,v)->{
					m.setAccessible(true);
					m.invoke(o,v);
					m.setAccessible(false);
				};
				ret.type=m.getParameterTypes()[0];
				return ret;
			}
		}
		return null;
	}
	
	private Class<?> getType(ObjectChunk data, Class<?> rootclass) throws ClassNotFoundException{
		ClassLoader loader=getClass().getClassLoader();
		Class<?> type=null;
		
		//Get the object class name
		DataChunk classchunk=data.get("type");
		
		if(classchunk==null || !(classchunk instanceof ValueChunk)) {
			//Without no type precised
			
			try {
				//Check if rootclass have default class defined
				Method defaultclass=ReflectionUtils.getMethod(rootclass, "defaultSubclass");
				if(defaultclass==null || !Modifier.isStatic(defaultclass.getModifiers())) throw new Exception();
				defaultclass.setAccessible(true);
				type=(Class<?>)defaultclass.invoke(null);
				defaultclass.setAccessible(false);
			}catch(Exception e) {
				//Check if root type is a valid type
				type=rootclass;
				try {
					if(!type.isAnnotationPresent(Loadable.class))
						throw new NoSuchMethodException();
					type.getDeclaredConstructor();
				}catch(NoSuchMethodException ee) {
					throw new ClassNotFoundException("Miss the parameter \"type\".");
				}
			}
		}
		else {
			//With type precised
			String classname=((ValueChunk)classchunk).getValue();
			
			//Get the type by name
			try {
				type=loader.loadClass(prefix+classname);
			}catch(ClassNotFoundException e) {
				type=loader.loadClass(classname);
			}
			
			//Check if type exist
			if(type==null || !type.isAnnotationPresent(Loadable.class))
				throw new ClassNotFoundException("Class \""+prefix+classname+"\" or \""+classname+"\" does not exist.");
		}
		//Check if type is valid (instantiable)
		try {
			type.getDeclaredConstructor();
		}catch(NoSuchMethodException e) {
			throw new ClassNotFoundException("Class \""+type.getName()+"\" is malformed. Ask the software developper.");
		}
		
		//Check if type is child of roottype
		if(!rootclass.isAssignableFrom(type))
			throw new ClassNotFoundException("The type \""+type.getName()+"\" cannot be used here.");
		
		return type;
	}

}
