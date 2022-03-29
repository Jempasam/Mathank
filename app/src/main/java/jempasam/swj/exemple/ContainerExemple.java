package jempasam.swj.exemple;

import java.util.Iterator;

import jempasam.swj.container.ReadableContainer;
import jempasam.swj.container.adapter.ArrayAdapterContainer;
import jempasam.swj.iterator.CompositeIterator;

public class ContainerExemple {
	public static void main(String[] args) {
		ReadableContainer<Integer> a=new ArrayAdapterContainer<>(new Integer[] {1,2,3});
		ReadableContainer<Integer> b=new ArrayAdapterContainer<>(new Integer[] {4,5,6});
		ReadableContainer<Integer> c=new ArrayAdapterContainer<>(new Integer[] {7,8,9});
		Iterator<Integer> it=new CompositeIterator<>(a.iterator(), b.iterator(), c.iterator());
		while(it.hasNext())System.out.println(it.next());
	}
}
