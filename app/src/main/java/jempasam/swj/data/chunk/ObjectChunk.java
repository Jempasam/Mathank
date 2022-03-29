package jempasam.swj.data.chunk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

import jempasam.swj.container.Container;

public class ObjectChunk extends DataChunk implements Container<DataChunk>, Cloneable{
	
	private List<DataChunk> content;
	
	public ObjectChunk(String name) {
		super(name);
		content=new ArrayList<>();
	}
	
	public ObjectChunk clone(){
		ObjectChunk newchunk=new ObjectChunk(getName());
		for(DataChunk d : this)newchunk.add(d.clone());
		return newchunk;
	}
	
	/**
	 * @return A clone of the object by adding number after name of value having same name.
	 */
	public void numerateSameName(){
		Map<String, Integer> count=new HashMap<>();
		for(DataChunk d : this) {
			int number=count.getOrDefault(d.getName(), 0);
			count.put(d.getName(), number+1);
			
			if(d instanceof ObjectChunk)
				((ObjectChunk)d).numerateSameName();
			
			if(number>0)
				d.setName(d.getName()+Integer.toString(number));
		}
	}
	
	public void rename(String from, String to) {
		for(DataChunk d : this) {
			if(d instanceof ObjectChunk)
				((ObjectChunk)d).rename(from, to);
			if(d.getName().equals(from))d.setName(to);
		}
	}
	
	public void forEachRecursively(Consumer<DataChunk> action) {
		for(DataChunk d : this) {
			if(d instanceof ObjectChunk)
				((ObjectChunk)d).forEachRecursively(action);
			action.accept(d);
		}
	}
	
	//--Container--
	//	Get
	@Override public DataChunk get(int position) {return content.get(position);}
	
	public DataChunk get(String name) {
		int firstpoint;
		if((firstpoint=name.indexOf('.'))!=-1) {
			String parentname=name.substring(0,firstpoint);
			String childname=name.substring(firstpoint+1);
			DataChunk dc=getInChilds(parentname);
			if(dc instanceof ObjectChunk)return ((ObjectChunk) dc).get(childname);
			else return null;
		}
		else return getInChilds(name);
	}
	
	private DataChunk getInChilds(String name) {
		for(DataChunk d : this) {
			if(d.getName().equals(name))return d;
		}
		return null;
	}
	
	// Add/Insert
	@Override public void insert(int position, DataChunk obj) {
		int oldpos=content.indexOf(obj);
		if(oldpos!=-1)content.remove(oldpos);
		
		content.add(position, obj);
	}
	// Remove
	@Override public DataChunk remove(int position) {
		DataChunk removed=content.remove(position);
		return removed;
	}
	// Set
	@Override public void set(int position, DataChunk obj) {
		DataChunk old=get(position);
		if(old!=null) {
			content.set(position, obj);
		}
	}
	
	// Size
	@Override public int size() {return content.size();}
	
	/**
	 * 
	 * @return number of value in the tree
	 */
	//TODO Tester cette méthode
	public int totalSize() {
		int size=0;
		for(DataChunk c : this) {
			if(c instanceof ObjectChunk) {
				size+=((ObjectChunk)c).totalSize();
			}
			else size++;
		}
		return size;
	}
	
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append("\""+getName()+"\":").append("(");
		for(DataChunk d : this)sb.append(d.toString()).append(" ");
		sb.append(")");
		return sb.toString();
	}
	
	public void fillWithValues(String nameprefix, List<ValueChunk> values) {
		for(DataChunk d : this) {
			if(d instanceof ValueChunk)values.add(new ValueChunk(nameprefix+d.getName(), ((ValueChunk) d).getValue()));
			else if(d instanceof ObjectChunk)((ObjectChunk) d).fillWithValues(nameprefix+d.getName()+".", values);
		}
	}
	
	public void fillWithValues(List<ValueChunk> values) { fillWithValues("",values); }
	
	public List<ValueChunk> getAsPathList() {
		List<ValueChunk> values=new ArrayList<>();
		fillWithValues("",values);
		return values;
	}
	
	public ValueChunk[] toValueArray() {
		List<ValueChunk> list=new ArrayList<>();
		fillWithValues(list);
		return list.toArray(new ValueChunk[0]);
	}
}
