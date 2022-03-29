package jempasam.swj.objectmanager;

public interface PrototypeManager<T extends Prototype> extends ObjectManager<T>{
	
	T get(String name);
	
	default T create(String name) {
		T prototype=get(name);
		if(prototype==null)return null;
		else return (T)prototype.clone();
	}
	
	T register(String name, T obj);
	
}
