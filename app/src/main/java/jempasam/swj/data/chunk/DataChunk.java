package jempasam.swj.data.chunk;

import java.util.function.Consumer;
import java.util.function.Function;

public abstract class DataChunk {
	private String name;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public DataChunk(String name) {
		this.name=name;
	}
	
	public abstract DataChunk clone();
	
	public String getValueElse(Function<ObjectChunk,String> elsedo) {
		if(this instanceof ValueChunk)return ((ValueChunk)this).getValue();
		else return elsedo.apply(((ObjectChunk)this));
	}
}
