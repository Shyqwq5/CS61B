package deque;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    private class IntNode {
        public T item;
        public IntNode next;
        public IntNode front;

        public IntNode(T i, IntNode f,IntNode n) {
            item = i;
            front = f;
            next = n;
        }
    }

    private int size;
    private IntNode sentinel;

    public LinkedListDeque() {
        sentinel = new IntNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.front = sentinel;
        size = 0;
    }

    public LinkedListDeque(T x) {
        sentinel = new IntNode(null, null,null);
        sentinel.next = sentinel;
        sentinel.front = sentinel;
        sentinel.next = new IntNode(x, sentinel ,sentinel);
        sentinel.front = sentinel.next;

        size = 1;
    }

    public void addFirst(T item){
        /*IntNode newness = new IntNode(item,sentinel,sentinel.next);
        sentinel.next.front = newness;
        sentinel.next = newness;*/

        sentinel.next = new IntNode(item,sentinel,sentinel.next);
        sentinel.next.next.front = sentinel.next;

        size += 1;
    } //Adds an item of type T to the front of the deque. You can assume that item is never null.
    public void addLast(T item){
        sentinel.front = new IntNode(item,sentinel.front,sentinel);
        sentinel.front.front.next = sentinel.front;
        size += 1;
    } //Adds an item of type T to the back of the deque. You can assume that item is never null.


   /* public boolean isEmpty(){
        if (size ==0) {return true;}
        return false;}*/
    //Returns true if deque is empty, false otherwise.

    public int size(){
        return size;
    } //Returns the number of items in the deque.

    public void printDeque(){
        IntNode copy = sentinel;
        for(int i = 1; i <= size; i++){
            System.out.print(copy.next.item);
            System.out.print(" ");
            copy = copy.next;
        }
        System.out.println();
    } //Prints the items in the deque from first to last, separated by a space. Once all the items have been printed, print out a new line.

    public T removeFirst(){
        if (isEmpty()) {return null;}
        T first = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.front = sentinel;
        size -=1;
        return first;
    } // Removes and returns the item at the front of the deque. If no such item exists, returns null.
    public T removeLast(){
        if (isEmpty()) {return null;}
        T last = sentinel.front.item;
        sentinel.front = sentinel.front.front;
        sentinel.front.next = sentinel;
        size -=1;
        return last;
    } //Removes and returns the item at the back of the deque. If no such item exists, returns null.


    public T get(int index){
        IntNode copy = sentinel;
        /*if (index ==0 && size>0){
            return copy.front.item;}*/

        if (index >= 0 && index <= size) {
            for (int i=0;i<=index; i+=1) {
                copy = copy.next;}
            return copy.item;}

        return null;
        }

     //Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth. If no such item exists, returns null. Must not alter the deque!
     public T getRecursive(int index) {
         if (index < 0 || index >= size) {
             return null;
         }
         return getRecursiveHelper(index, sentinel.next);  //
     }

    private T getRecursiveHelper(int index, IntNode node) {
        if (index == 0) {
            return node.item;
        }
        else {  //
            return getRecursiveHelper(index - 1, node.next);
        }
    }

    private class LinkedSetIterator implements Iterator<T> {
        private int index = 0;
        public LinkedSetIterator() {}
        public boolean hasNext(){
            return index < size;
        }
        public T next() {
            T returnItem = get(index);
            index += 1;
            return returnItem;
        }
    }

    public Iterator<T> iterator(){
        return new LinkedSetIterator();}

    public boolean equals(Object o){
        if(this == o){return true;}
        if(o instanceof LinkedListDeque){if(size != ((LinkedListDeque)o).size()){return false;}
            for(int i = 0; i < size; i++){
                if(!get(i).equals(((LinkedListDeque)o).get(i))){return false;}
            }
        }
        return false;}
}
    // Returns whether or not the parameter o is equal to the Deque. o is considered equal if it is a Deque and if it contains the same contents (as goverened by the generic T’s equals method) in the same order.
    // (ADDED 2/12: You’ll need to use the instance of keywords for this. Read here for more information)




    //public Iterator<T> iterator(){}
    //The Deque objects we’ll make are iterable (i.e. Iterable<T>) so we must provide this method to return an iterator.
