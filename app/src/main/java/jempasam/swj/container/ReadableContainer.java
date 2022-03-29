package jempasam.swj.container;

import java.util.Iterator;

import jempasam.swj.container.iterator.ReadableContainerIterator;

/**
 * Un conteneur g�n�rique immuable
 * @author Samuel Demont
 *
 * @param <T>
 */
public interface ReadableContainer<T> extends SizedIterable<T>{
	
	/**
	 * @param position
	 * @return la valeur � la position donn�e
	 */
	public T get(int position);
    
    /**
     * R�cup�re l'�l�ment � la position indiqu�e mais en partant de la fin
	 * @param position
	 * @return la valeur � la position donn�e
	 */
	default T getFromEnd(int position) {
		return get(size()-1-position);
	}
    
    /**
     * @return Une valeur al�atoire du conteneur
     */
    public default T getRandom() {
        return get((int)(Math.random()*size()));
    }
    
    /**
     * R�cup�re la position d'une valeur dans le conteneur
     * @param obj La valeur � trouver
     * @return La position de la valeur ou -1 si la valeur n'existe pas dans le conteneur
     */
    public default int indexOf(Object obj) {
    	int size=size();
    	for(int i=0; i<size; i++) {
    		if(get(i)==obj)return i;
    	}
    	return -1;
    }
    
    /**
     * @return Le dernier objet du conteneur ou null si le conteneur est vide
     */
    default T last() {
    	if(size()>0)return get(size()-1);
    	else return null;
    }
    
    /**
     * Remplie un conteneur avec les �l�ments avec les �l�ments d'index index_min � index_min+size .
     * @param c Le conteneur � remplir
     * @param index_min L'index du premier �l�ment
     * @param size Le nombre d'�l�ment
     * @return Le conteneur rempli.
     */
    default Container<T> subContainer(Container<T> c, int index_min, int size){
    	for(int i=0; i<size && i+index_min<size(); i++) {
    		c.add(get(index_min+i));
    	}
    	return c;
    }
    
    @Override
    default Iterator<T> iterator() {
    	return new ContainerIterator<>(this);
    }
    
    default ReadableContainerIterator<T> readableContainerIterator(){
    	return new ReadableContainerIterator<>(this);
    }
    
    default String asString() {
    	StringBuilder sb=new StringBuilder();
    	for(T o : this)sb.append("[").append(o).append("]");
    	return sb.toString();
    }
}
