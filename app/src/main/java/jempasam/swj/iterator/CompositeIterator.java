package jempasam.swj.iterator;

import java.util.Iterator;
import java.util.List;

public class CompositeIterator<T> implements Iterator<T>{
	
	Iterator<T>[] iterators;
	int itnumber;
	
	private CompositeIterator() {
		itnumber=0;
	}
	
	public CompositeIterator(Iterator<T> ...its) {
		this();
		iterators=its;
	}
	
	public CompositeIterator(Iterable<T> ...its) {
		this();
		iterators=new Iterator[its.length];
		for(int i=0; i<its.length; i++)iterators[i]=its[i].iterator();
	}
	
	public CompositeIterator(List<Iterable<T>> its) {
		this();
		iterators=new Iterator[its.size()];
		for(int i=0; i<its.size(); i++)iterators[i]=its.get(i).iterator();
	}
	
	public static <T> CompositeIterator<T> ofIteratorList(List<Iterator<T>> its) {
		CompositeIterator<T> ret=new CompositeIterator<>();
		ret.iterators=its.toArray(new Iterator[0]);
		return ret;
	}
	
	public static <T> CompositeIterator<T> ofIterableList(List<Iterable<T>> its) {
		return new CompositeIterator<>(its);
	}
	
	public static <T> CompositeIterator<T> ofIterators(Iterator<T> ...its) {
		return new CompositeIterator<>(its);
	}
	
	public static <T> CompositeIterator<T> ofIterables(Iterable<T> ...its) {
		return new CompositeIterator<>(its);
	}
	

	@Override
	public boolean hasNext() {
		if(iterators[itnumber].hasNext())return true;
		else return itnumber<iterators.length-1;
	}

	@Override
	public T next() {
		if(!iterators[itnumber].hasNext() && itnumber<iterators.length-1) {
			itnumber++;
		}
		return iterators[itnumber].next();
	}
}
