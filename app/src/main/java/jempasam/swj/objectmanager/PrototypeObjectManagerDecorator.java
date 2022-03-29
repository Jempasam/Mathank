package jempasam.swj.objectmanager;

import java.util.Iterator;
import java.util.Map.Entry;

public class PrototypeObjectManagerDecorator<T extends Prototype> implements PrototypeManager<T>{
	
	ObjectManager<T> manager;
	
	public PrototypeObjectManagerDecorator(ObjectManager<T> manager) {
		this.manager=manager;
	}
	
	@Override
	public T get(String name) {
		return manager.get(name);
	}
	
	@Override
	public String idOf(T name) {
		return manager.idOf(name);
	}
	
	@Override
	public Iterator<Entry<String, T>> iterator() {
		return manager.iterator();
	}
	
	@Override
	public int size() {
		return manager.size();
	}
	
	@Override
	public T register(String name, T obj) {
		return manager.register(name, obj);
	}

}
