package hashmap;

import java.util.*;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private Set<K> keySet = new HashSet<>();
    private int size = 16;
    private double maxLoad = 0.75;
    private int load;
    /* Instance Variables */
    private Collection<Node>[] buckets;

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {

        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /** Constructors */
    public MyHashMap() {
        buckets = createTable(size);
    }

    public MyHashMap(int initialSize) {
        this.size = initialSize;
        buckets = createTable(initialSize);
    }

    /**
     * MyHashMap constructor that creates a backing array of initialSize.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialSize initial size of backing array
     * @param maxLoad maximum load factor
     */
    public MyHashMap(int initialSize, double maxLoad) {
        this.size = initialSize;
        this.maxLoad = maxLoad;
        buckets = createTable(initialSize);
    }

    /**
     * Returns a new node to be placed in a hash table bucket
     */
    private Node createNode(K key, V value) {
        return new Node(key, value);
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<>();
    }

    /**
     * Returns a table to back our hash table. As per the comment
     * above, this table can be an array of Collection objects
     *
     * BE SURE TO CALL THIS FACTORY METHOD WHEN CREATING A TABLE SO
     * THAT ALL BUCKET TYPES ARE OF JAVA.UTIL.COLLECTION
     *
     * @param tableSize the size of the table to create
     */
    private Collection<Node>[] createTable(int tableSize) {
        Collection<Node>[] table = new Collection[tableSize];
        for (int i = 0; i < tableSize; i++) {
            table[i] = createBucket();
        }
        return table;
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!
    /** Removes all of the mappings from this map. */

    private void resize(int tableSize) {
        size = tableSize;
        Collection<Node>[] table = createTable(tableSize);
        for (Collection<Node> col: buckets){
            for (Node node : col) {
                int index = Math.floorMod(node.key.hashCode(), size);
                table[index].add(node);
            }
        }
        buckets = table;

    }

    public void clear(){
        buckets = createTable(size);
        load = 0;
        keySet = new HashSet<>();
    }

    /** Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key){
        return keySet.contains(key);
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    public V get(K key){

        if (keySet.contains(key)){
            int index = Math.floorMod(key.hashCode(), size);
            for (Node node : buckets[index]) {
                if (key.equals(node.key)){
                    return node.value;}}
        }
        return null;}
    /** Returns the number of key-value mappings in this map. */
    public int size(){
        return load;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map previously contained a mapping for the key,
     * the old value is replaced.
     */
    public void put(K key, V value){
        int index = Math.floorMod(key.hashCode(), size);
        if (keySet.contains(key)){for (Node node : buckets[index])
        {if (key.equals(node.key)){
            node.value = value;
            return;}
        }
        }
        else{ keySet.add(key);
        buckets[index].add(createNode(key, value));
        load +=1;
        int loadFactor = load/size;
        if (loadFactor > maxLoad){
            size = size *  2;
            resize(size);
        }}
    }

    /** Returns a Set view of the keys contained in this map. */
    public Set<K> keySet(){
        return keySet;
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    public V remove(K key){
        if (keySet.contains(key)){
        int index = Math.floorMod(key.hashCode(), size);
        for (Node node : buckets[index]) {
            if (key.equals(node.key)) {
                keySet.remove(node.key);
                buckets[index].remove(node);
                return node.value;
            }
        }}
        return null;
    }

    /**
     * Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.
     */
    public V remove(K key, V value){
        if (keySet.contains(key)){
            int index = Math.floorMod(key.hashCode(), size);
            for (Node node : buckets[index]) {
                if (key.equals(node.key)) {
                    if (node.value.equals(value)) {
                        keySet.remove(node.key);
                        buckets[index].remove(node);
                        return node.value;
                    }
                }
            }}
        return null;
    }

    public Iterator<K> iterator(){
        return new hashiterator();
    }

    private class hashiterator implements Iterator<K> {
        int t = load;
        int i =0;
        public boolean hasNext(){
            if (t > 0){return true;}
            return false;
        }
        public K next(){t -=1;
            i+=1;
            int t=0;
            for (Collection<Node> col: buckets){
                for (Node node : col) {
                    t+=1;
                    if (t == i){return node.key;}
            }


        }

            return null;
        }

}
}