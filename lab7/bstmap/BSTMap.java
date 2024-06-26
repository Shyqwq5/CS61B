package bstmap;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private node root;
    private int size;

    private class node {
        private K key;
        private V value;
        private node l, r;

        public node(K key, V value) {
            this.key =key;
            this.value = value;
        }
        public K getKey() {
            return key;
        }
        public V getValue() {
            return value;
        }
        public node left() {
            return l;
        }
        public node right() {
            return r;
        }

    }

    public BSTMap(){
    size=0;
    root = null;}


    /** Removes all of the mappings from this map. */
    public void clear(){
        this.root = null;
        size = 0;

    }

    /* Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key){
        return contain(this.root,key);
    }



    private boolean contain(node t,K key){
        if (t == null){return false;}
        int com = key.compareTo(t.getKey());
        if (com < 0){
            return contain(t.left(), key);
        }
        if (com > 0){
            return contain(t.right(), key);
        }
        else return true;
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    public V get(K key){
        return private_get(this.root,key);
    }

    private V private_get(node t,K key){
        if (t == null){return null;}
        int com = key.compareTo(t.getKey());
        if (com < 0){
            return private_get(t.left(), key);
        }
        if (com > 0){
            return private_get(t.right(), key);
        }
        else return t.value;
    }

    /* Returns the number of key-value mappings in this map. */
    public int size(){
        return size;
    }

    /* Associates the specified value with the specified key in this map. */
    public void put(K key, V value){
        this.root = private_put(this.root, key, value);
    }

    private node private_put(node t,K key,V value){
        if (t == null){size+=1; return new node(key, value);}
        int com = key.compareTo(t.getKey());
        if (com < 0){
            t.l = private_put(t.left(), key,value);
        }
        if (com > 0){
            t.r = private_put(t.right(), key,value);
        }
        else t.value = value;
        return t;
    }
    public void printInOrder(){
        throw new UnsupportedOperationException();
    }



    /* Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException. */
    public Set<K> keySet(){
        throw new UnsupportedOperationException();
    }

    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException. */
    public V remove(K key){
        throw new UnsupportedOperationException();
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 7. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    public V remove(K key, V value){
        throw new UnsupportedOperationException();
    }

    public Iterator<K> iterator(){
        throw new UnsupportedOperationException();
    }



    /*private class bstiterator implements Iterator<K> {
        public boolean hasNext(){

        }
        public K next(){

        }

    }*/


}