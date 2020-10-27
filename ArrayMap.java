/**							J + M + J
 * @author Joseph Gauthier
 * 
 * This is an extention of the AbstractMap abstract class that implements a custom,
 * templated, ArrayMap class that. This class treats mapping in a unique way, 
 * by having two, different arrays in fields (one of keys, the 
 * other values) where the pairs are signified by indexes of the two.
 * EX) 
 * 		keys = ["a", "b", "c"]
 * 		values = [1, 2, 3]
 * 		
 * 		The pairs would be a:1, b:2, c:3; because they share the same indexes amongst
 * 		themselves between the two arrays.
 * 
 * 
 */

import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Set;
import java.util.Iterator;
import java.lang.IllegalStateException;

public class ArrayMap<K, V> extends AbstractMap<K, V>{
	private Object[] keys;
	private Object[] values;
	private int size;
	
	/**
	 * This is the constructor that sets the fields of the object. Each of the 
	 * two arrays start off at length 10, but the actual size of the ArrayMap is
	 * 0, since nothing has been placed into the Map at this point.
	 */
	public ArrayMap() {
		this.keys = new Object[10];
		this.values = new Object[10];
		this.size = 0;
	}
	
	/**
	 * This implementation of the put method overrides AbstractMaps's default put.
	 * It loops through the keys field to make sure that what is being
	 * put in isn't already in the map, and places the pairs at the end of the map
	 * (Not the end of the arrays, mind you). If the key already exists, the value
	 * paired with that key is replaced with the value passed in and the old value is
	 * returned. 
	 * @param key, The key of the new mapping.
	 * 
	 * @param value, The value of the new mapping.
	 * 
	 * @return, null if this is a truly new mapping, or the value that was
	 * 			replaced by the value passed in if this is an update to an
	 * 			existing mapping.
	 */
	@Override
	public V put(K key, V value) {
		int indexCount = 0;
		for(int i = 0; i < size; i++) {
			Object currKey = this.keys[i];
			if (currKey.equals(key)) {
				Object holder = values[indexCount];
				values[indexCount] = value;
				return (V) holder;
			}
			indexCount++;
		}
		if (this.size == keys.length) {
			increaseArraylength();
		}
		
		this.keys[size] = key;
		this.values[size] = value;
		this.size++;
		return null;
	}
	
	/**
	 * This private method is only called in the put method and doubles the size
	 * of the arrays in the keys and values fields. This enables the map itself to 
	 * keeping growing beyond the current size of its arrays. 
	 * NOTE: This increases the capacity of the arrays used to 
	 * 		 back up the ArrayMap, not the size of the mappings.
	 */
	private void increaseArraylength() {
		Object[] newKeys = new Object[size * 2];
		Object[] newVals = new Object[size * 2];
		
		for (int i = 0; i < this.size; i++) {
			newKeys[i] = this.keys[i];
			newVals[i] = this.values[i];
		}
		
		this.keys = newKeys;
		this.values = newVals;
	}
	
	/**
	 * This method overrides the default size method in the AbstracMap interface.
	 * It returns whatever the size of the mapping is, which is stored in the size
	 * filed.
	 * 
	 * @return, The size of the mappings, not the size of the keys and values fields.
	 */
	@Override
	public int size() {
		return this.size;
	}
	
	/**
	 * All this method does is it returns a new ArrayMapEntrySet. There is no actual
	 * Set Object of Entry Objects here, however, ArrayMapEntrySet abstractly 
	 * represents a Set of Entry Objects that can be iterated over thanks to the
	 * custom Iterator it uses.
	 * 
	 * @return, an ArrayMapEntrySet Object that represents a Set of Entry Objects.
	 */
	@Override
	public Set<Entry<K, V>> entrySet() {
		return new ArrayMapEntrySet();
	}
	
	
	/**
	 * @author Joseph Gauthier
	 * 
	 *
	 */
	private class ArrayMapEntrySet extends AbstractSet<Entry<K,V>> {
		
		/**
		 * This method returns an Iterator object that allows the AbstractMap object
		 * to be iterable in a general way. This involves the use of another
		 * class implemented below.
		 * 
		 *  @return An Iterator object that has the iteration implementation
		 *  		specific to the ArrayMap object.
		 */
		@Override
		public Iterator<Entry<K, V>> iterator() {
			return new ArrayMapEntrySetIterator<Entry<K, V>>();
		}
		
		/**
		 * This method overrides the default size method in the AbstracMap interface.
		 * It returns whatever the size of the mapping is, which is stored in the size
		 * filed.
		 * 
		 * @return, The size of the mappings, not the size of the keys and values fields.
		 */
		@Override
		public int size() {
			return size;
		}
		
		/**
		 * Loops through the keys and fields arrays to find whether or not the passed
		 * in Object is contained in the ArrayMap.
		 * 
		 * @param o, The object passed in to see if the ArrayMap contains it. NOTE:
		 * 			 Takes in any Object, but only Entry Object will be considered in
		 * 			 the implementation.
		 * 
		 * @return, True if is contained in the ArrayMap, false otherwise.
		 */
		@Override
		public boolean contains(Object o) {
			if (!(o instanceof SimpleEntry)) {
				return false;
			}
			SimpleEntry<K, V> obj = (SimpleEntry<K, V>) o;
			K keyToCheck = obj.getKey();
			V valToCheck = obj.getValue();
			
			for (int i = 0; i < size; i++) {
				System.out.println(keys[i]);
				System.out.println(keyToCheck);
				System.out.println();
				System.out.println(values[i]);
				System.out.println(valToCheck);
				if (keys[i].equals(keyToCheck) && values[i].equals(valToCheck)) {
					return true;
				}
			}
			return false;
		}
		
	}
	
	private class ArrayMapEntrySetIterator<T> implements Iterator<T> {
		private int pointer = 0;
		private boolean wasNextCalled = true;
		
		@Override
		public boolean hasNext() {
			return size >= pointer;
		}

		@Override
		public T next() {
			SimpleEntry<K, V> result = new SimpleEntry<K, V>((K) keys[pointer], (V) values[pointer]);
			wasNextCalled = false;
			pointer ++;
			return (T) result;
		}
		
		@Override
		public void remove() throws IllegalStateException {	
			if (wasNextCalled) {
				throw new IllegalStateException("Invalid remove");
			}
			
			Object[] keyResult = new Object[keys.length];
			Object[] valResult = new Object[values.length];
			for (int i = 0; i < pointer - 1; i++) {
				keyResult[i] = keys[i];
				valResult[i] = values[i];
			}
			for (int i = pointer; i < keys.length; i ++) {
				keyResult[i] = keys[i];
				valResult[i] = values[i];
			}
			keys = keyResult;
			values = valResult;
			size --;
			wasNextCalled = true;
		}
		
	}
}
