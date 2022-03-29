package jempasam.swj.container;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface SizedIterable<T> extends Iterable<T>{
	
	/**
	 * @return le nombre de valeurs contenues dans le conteneur
	 */
    public int size();
	
	default Stream<T> stream() {
        return StreamSupport.stream(spliterator(), false);
    }
	
	/**
     * Retourne une valeur du conteneur �gale � celle pass�e en param�tre
     * @param comp La valeur � comparer
     * @return La valeur trouv�e ou null si la valeur n'existe pas dans le conteneur
     */
    public default T findEquals(T comp){
    	for(T c : this)if(comp.equals(c))return c;
    	return null;
    }
    
    /**
     * Retourne une liste des valeurs du conteneur �gales � celle pass�e en param�tre
     * @param comp La valeur � comparer
     * @return La liste des valeurs trouv�es ou null si la valeur n'existe pas dans le conteneur
     */
    public default List<T> findAllEquals(T comp){
    	List<T> ret=new ArrayList<>();
    	for(T c : this)if(comp.equals(c))ret.add(c);
    	return ret;
    }
    
    /**
     * Trouve la premi�re valeur qui correspondent aux conditions du pr�dicat
     * @param predicate Le pr�dicat
     * @return La premi�re valeur trouv�e
     */
    public default T findFor(Predicate<T> predicate) {
    	for(T c : this)if(predicate.test(c))return c;
    	return null;
    }
    
    /**
     * Trouve toutes les valeurs qui correspondent aux conditions du pr�dicat
     * @param predicate Le pr�dicat
     * @return Une liste des valeurs trouv�es
     */
    public default List<T> findAllFor(Predicate<T> predicate) {
    	List<T> ret=new ArrayList<>();
    	for(T c : this)if(predicate.test(c))ret.add(c);
    	return ret;
    }
    
    /**
     * Teste si une valeur du conteneur correspond aux conditions du pr�dicat
     * @param predicate
     * @return
     */
    public default boolean testIfOne(Predicate<T> predicate) {
    	for(T c : this)if(predicate.test(c))return true;
    	return false;
    }
    
    /**
     * Teste si toutes les valeurs du conteneur correspond aux conditions du pr�dicat
     * @param predicate
     * @return
     */
    public default boolean testIfAll(Predicate<T> predicate) {
    	for(T c : this)if(!predicate.test(c))return false;
    	return true;
    }
    
	 /**
     * Calcule la somme des retours de la fonction sur chaque �l�ment
     * @param func La fonction
     * @return La somme des retours
     */
    default int sum(ToIntFunction<T> func){
    	int sum=0;
    	for(T a : this)sum+=func.applyAsInt(a);
    	return sum;
    }
    
    /**
     * Compte le nombre d'�l�ment qui r�pondent aux conditions du pr�dicat
     * @param pred Le pr�dicat
     * @return Le nombre d'�l�ment
     */
    default int count(Predicate<T> pred){
    	int sum=0;
    	for(T a : this)if(pred.test(a))sum++;
    	return sum;
    }
    
    /**
     * R�cup�re le meilleur �l�ment d'apr�s un comparateur
     * @param isbetter Le comparateur qui d�termine si le premier �l�ment pass� en param�tre est meilleur que le deuxi�me
     * @return Le meilleur
     */
    default T getBetter(BiPredicate<T,T> isbetter){
    	Iterator<T> it=iterator();
    	if(!it.hasNext())return null;
    	T better=it.next();
    	for(T a : this) if(isbetter.test(a, better)) better=a;
    	return better;
    }
    
    /**
     * Test si un objet existe dans le conteneur
     * @param obj L'objet � trouver
     * @return true si l'objet existe dans le conteneur.
     */
    public default boolean contains(Object obj) {
		for(T o : this)if(o==obj)return true;
		return false;
	}
    
    /**
     * Test si un ensemble d'objet est inclus dans le conteneur
     * @param obj L'ensemble qui doit �tre inclus
     * @return true si l'ensemble est inclus
     */
    default boolean containsAll(ReadableContainer<?> c) {
    	for( Object e : c ) {
    		if(!contains(e))return false;
    	}
    	return true;
    }
    
    /**
     * Test si un objet �gal existe dans le conteneur
     * @param obj L'objet � comparer
     * @return true si un objet �gal � l'objet pass� en param�tre existe dans le tableau
     */
    public default boolean containsEquals(Object obj) {
		for(T o : this)if(obj.equals(o))return true;
		return false;
	}
    
    /**
     * @return true si le conteneur est vide sinon false
     */
    default boolean isEmpty() {
    	return size()==0;
    }
    
    /**
     * @return un tableau contenant les m�mes objet de le conteneur
     */
    default Object[] toArray() {
    	Object[] ret=new Object[size()];
    	int i=0;
    	for(Object o : this) {
    		ret[i]=o;
    		i++;
    	}
    	return ret;
    }
}
