package deque;

public class ArrayDeque<T> {
    private T[] items;
    private int start;
    private int size;
    private int rear;

    public ArrayDeque(){
        items = (T[]) new Object[8];
        start = 4;
        size = 0;
        rear = 7;
    }
    private void resize(int newSize){
        int t = newSize/3;
        T[] newItems = (T[]) new Object[newSize];
        System.arraycopy(items, start, newItems,t, size);
        items = newItems;
        start = t;
        rear = newSize - 1;
    }

    public void addFirst(T item){
        if(start == 0){
            resize(size*3);
        }
        start -=1;
        items[start] = item;
        size +=1;
    }
    // Adds an item of type T to the front of the deque. You can assume that item is never null.
    public void addLast(T item){
        if( start + size ==rear){
            resize(size*4);
        }
        items[start + size] = item;
        size +=1;
       
    }
    //Adds an item of type T to the back of the deque. You can assume that item is never null.
    public boolean isEmpty(){
        return size==0;
    }
    // Returns true if deque is empty, false otherwise.
    public int size(){
        return size;
    }
    // Returns the number of items in the deque.
    public void printDeque(){
        for(int i = 0; i < size; i++){
            int t = start + i;
            System.out.print(items[t]);
            System.out.print(" ");
        }
        System.out.println();
    }
    // Prints the items in the deque from first to last, separated by a space. Once all the items have been printed, print out a new line.
    public T removeFirst(){
        if(size == 0){return null;}
        T item = items[start];
        items[start] = null;
        size -=1;
        start +=1;
        if (rear > 7 &&  size < (rear+1)/4){int t= size;
            resize(size*3);
        }
        return item;
    }
    // Removes and returns the item at the front of the deque. If no such item exists, returns null.
    public T removeLast(){
        if(size == 0){return null;}
        size -= 1;
        T item = items[start + size];
        items[start + size] = null;

        if (rear > 7 &&  size < (rear+1)/4){int t= size;
            resize(size*3);
        }
        return item;
    }
    // Removes and returns the item at the back of the deque. If no such item exists, returns null.
    public T get(int index){
        return items[start +index];
        /*if(index ==0) {return items[size+start-1];}
        return items[start +index -1];*/
    }
    //: Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth. If no such item exists, returns null. Must not alter the deque!
    //In addition, we also want our two Deques to implement these two special methods:

    //public Iterator<T> iterator(){}
    //The Deque objects we’ll make are iterable (i.e. Iterable<T>) so we must provide this method to return an iterator.

    //public boolean equals(Object o){}
    //Returns whether or not the parameter o is equal to the Deque. o is considered equal if it is a Deque and if it contains the same contents (as goverened by the generic T’s equals method) in the same order.
    // (ADDED 2/12: You’ll need to use the instance of keywords for this. Read here for more information)
}
