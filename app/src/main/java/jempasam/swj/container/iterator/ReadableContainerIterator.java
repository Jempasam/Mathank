package jempasam.swj.container.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

import jempasam.swj.container.ReadableContainer;

public class ReadableContainerIterator<T> implements Iterator<T>{
	protected ReadableContainer<T> target;
	protected int pos;
	protected int last;
	
	public ReadableContainerIterator(ReadableContainer<T> target) {
		super();
		this.target = target;
		this.pos=0;
	}

	@Override
	public boolean hasNext() {
		return pos<target.size();
	}
	
	@Override
	public T next() {
		if(hasNext()){
			pos++;
			last=pos-1;
			return target.get(last);
		}
		throw new NoSuchElementException();
	}
	
	public boolean hasPrevious() {
		return pos>0;
	}
	
	public T previous() throws NoSuchElementException {
		if(hasPrevious()){
			pos--;
			last=pos;
			return target.get(last);
		}
		throw new NoSuchElementException();
	}
	
	public int actualIndex() {
		return last;
	}
	
	public T actual() {
		return target.get(last);
	}
	
	
}
