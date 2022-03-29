package jempasam.swj.objectmanager;

import java.util.Map.Entry;

import jempasam.swj.container.SizedIterable;

public interface ObjectManager<T> extends SizedIterable<Entry<String,T>> {
	T get(String name);
	String idOf(T name);
	T register(String name, T obj);
}
