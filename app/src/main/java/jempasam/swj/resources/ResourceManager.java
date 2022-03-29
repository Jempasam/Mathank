package jempasam.swj.resources;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Time;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class ResourceManager<T> {
	private Map<String,ResourceBundle<T>> resource_bundles;
	Function<InputStream,T> resource_loader;
	String resourcefolder;
	 Supplier<ResourceBundle<T>> bundler;
	
	public ResourceManager(Function<InputStream,T> resource_loader, String resourcefolder, Supplier<ResourceBundle<T>> bundler) {
		resource_bundles=new HashMap<>();
		this.resource_loader=resource_loader;
		this.resourcefolder=resourcefolder;
		this.bundler=bundler;
		
		Set<String> resources_to_load=new HashSet<>();
		
		//Then load
		for(String bundlename : listInDir(resourcefolder)) {
			resource_bundles.put(bundlename, new ResourceBundle<T>(this,bundlename));
		}
	}
	public ResourceBundle<T> getResourceBundle(String name) {
		return resource_bundles.get(name);
	}
	
	public void setUnfinded(String bundle_name, BiFunction<ResourceBundle<T>,String,T> unfinded) {
		ResourceBundle<T> bundle=getResourceBundle(bundle_name);
		if(bundle!=null)bundle.setUnfindedHandler(unfinded);
	}
	
	public T getResource(String bundle_name, String image_name) {
		ResourceBundle<T> bundle=resource_bundles.get(bundle_name);
		if(bundle==null)return null;
		else {
			return bundle.getResource(image_name);
		}
	}
	
	static Set<String> listInDir(String dir){
		Map<String,String> files=new HashMap<>();
		Class<?> clazz=ResourceManager.class;
		
		BufferedReader reader;
		InputStream inputstream;
		String line;
		
		//Load from inside of the jar
		inputstream=clazz.getResourceAsStream("/"+dir+"/index.txt");
		if(inputstream!=null) {
			reader=new BufferedReader(new InputStreamReader(inputstream));
			try {
				while( (line=reader.readLine()) != null ) {
					String[] fileparam=line.split(";");
					String name=fileparam[0];
					String option=fileparam.length>1 ? fileparam[1] : fileparam[0];
					files.put(name,option);
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//Load from extern files
		File dirf=new File(dir+"/");
		if(dirf.isDirectory()) {
			for(String file : dirf.list()){
				files.put(file,"");
			}
		}
		
		//Treat options
		Set<String> ret=new HashSet<>();
		eachfile:for(Entry<String,String> file : files.entrySet()) {
			String namefile=file.getKey();
			String options[]=file.getValue().split(",");
			
			//For each option
			for(String option : options) {
				String opt[]=option.split(";");
				//Zero parameter
				if(opt.length==3) {
					if(opt[0].equals("date")) {
						try {
							Time from=Time.valueOf(opt[1]);
							Time to=Time.valueOf(opt[2]);
							Time actual=new Time(System.currentTimeMillis());
							if(actual.before(from)||to.before(actual))continue eachfile;
						}catch(Exception e){ }
					}
				}
				//One parameter
				else if(opt.length==2) {
					if(opt[0].equals("name")) {
						namefile=opt[1];
					}
				}
			}
			ret.add(namefile);
		}
		
		ret.remove("index.txt");
		return ret;
	}
}
