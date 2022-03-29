package jempasam.swj.container.iterator;

import jempasam.swj.container.Container;

public class ContainerIterator<T> extends ReadableContainerIterator<T> {

	public ContainerIterator(Container<T> target) {
		super(target);
	}
	
	public void set(T newvalue) {
		((Container<T>)target).set(last, newvalue);
	}
	
	@Override
	public void remove() {
		((Container<T>)target).remove(last);
		if(pos>last)pos--;
	}
	
}
