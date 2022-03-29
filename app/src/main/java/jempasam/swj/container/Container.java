package jempasam.swj.container;

import java.util.function.Consumer;
import java.util.function.Predicate;

import jempasam.swj.container.iterator.ReadableContainerIterator;

/**
 * Un conteneur g�n�rique iterable avec des index num�riques
 * @author Samuel Demont
 *
 * @param <T>
 */
public interface Container<T> extends ReadableContainer<T>{
	/**
	 * Change une valeur dans dans le conteneur
	 * @param position Position de la valeur
	 * @param obj Nouvelle valeur
	 */
    public void set(int position,T obj);
    
    /**
     * Insert une valeur dans le conteneur � l'index indiqu� en d�calant les autres valeurs
     * @param position Position ou ins�rer la valeur
     * @param card Valeur � ins�rer
     */
    public void insert(int position,T obj);
    
    /**
     * Supprime une valeur du conteneur � une position donn�e
     * @param position Position de la valeur � supprimer
     * @return
     */
    public T remove(int position);
    
    /**
     * Supprime la valeur indiqu�e
     * @param obj La valeur � supprimer
     * @return true si une valeur a bien �t� supprim�e
     */
    public default boolean remove(T obj) {
    	int index=indexOf(obj);
    	if(index==-1)return false;
    	remove(index);
    	return true;
    }
    
    /**
     * Supprime un ensemble de valeurs
     * @param obj Le conteneur contenant les valeurs � supprimer
     * @return Le nombre de valeurs supprim�es
     */
    public default int removeAll(ReadableContainer<T> objs) {
    	int counter=0;
    	for(T o : objs) {
    		if(remove(o))counter++;
    	}
    	return counter;
    }
    
    /**
     * Supprime toutes les valeurs qui correspondent aux conditions du pr�dicat
     * @param pred Le pr�dicat
     * @return Le nombre de valeurs supprim�es
     */
    public default int removeAllThat(Predicate<T> pred) {
    	int count=0;
    	for(int i=size(); i>=0; i--) if(pred.test(get(i))) {
    		remove(i);
    		count++;
    	}
    	return count;
    }
    
    /**
     * Supprime toutes les valeurs du conteneur sauf celles contenus dans un autre conteneur
     * @param obj Le conteneur contenant les valeurs � garder
     * @return Le nombre de valeurs gard�es
     */
    public default int retainAll(ReadableContainer<T> objs) {
    	int counter=0;
    	for(T e : this) {
    		if(objs.contains(e)) counter++;
    		else remove(e);
    	}
    	return counter;
    }
    
    /**
     * Ajoute une valeur dans le conteneur
     * @param obj La valeur � ajouter
     */
    public default void add(T obj) {
    	insert(size(), obj);
    }
    
    /**
     * Ajoute plusieurs valeurs dans le conteneur
     * @param obj Les valeurs � ajouter
     */
    public default void addAll(T ...obj) {
    	for(T o : obj) insert(size(), o);
    }
    
    /**
     * Echange la position de deux valeurs
     * @param pos1 La position d'une des valeurs � �changer
     * @param pos2 La position de l'autres valeur � �changer
     */
    public default void swap(int pos1,int pos2) {
    	T a=get(pos1);
    	set(pos1, get(pos2));
    	set(pos2, a);
    }
    
    /**
     * Effectue une op�ration sur toutes les valeurs du conteneur
     * @param cons L'op�ration � effectuer
     */
    public default void compute(Consumer<T> cons) {
    	for(T c : this)cons.accept(c);
    }
    
    /**
     * Supprime le dernier �l�ment du conteneur
     * @return l'�l�ment supprim�
     */
    default T pop() {
    	if(size()>0)return remove(size()-1);
    	else return null;
    }
    
    /**
     * M�lange le conteneur
     */
    default void shuffle() {
    	int size=size();
    	for(int i=0; i<size-1; i++) {
    		for(int y=i; y<size ; y++) {
    			swap( i, (int)(y+Math.random()*(size-y)) );
    		}
    	}
    }
    
    default ContainerIterator<T> containerIterator(){
    	return new ContainerIterator<>(this);
    }
}
