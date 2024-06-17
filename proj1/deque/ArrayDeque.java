package deque;

public class ArrayDeque<T> {
    private T[] items;
    private int front;
    private int rear;
    private int size;

    public ArrayDeque(){
        items = (T[]) new Object[8];
        front = 0;
        rear = 0;
        size = 4;
    }
    private void resize(int newSize){
        int t = newSize/2;
        T[] newItems = (T[]) new Object[newSize];
        System.arraycopy(items, size-front, newItems, t-front, front+rear);
        size = t;
        items = newItems;
    }

    public void addFirst(T item){
        if(front == size){
            resize(size*4);
        }
        front +=1;
        items[size - front] = item;
    }
    // Adds an item of type T to the front of the deque. You can assume that item is never null.
    public void addLast(T item){
        if(rear == size){
            resize(size*4);
        }
        items[size + rear] = item;
        rear +=1;
    }
    //Adds an item of type T to the back of the deque. You can assume that item is never null.
    public boolean isEmpty(){
        return front == 0 || rear == 0;
    }
    // Returns true if deque is empty, false otherwise.
    public int size(){
        return front + rear;
    }
    // Returns the number of items in the deque.
    public void printDeque(){
        for(int i = 0; i < front+rear; i++){
            int t = size-front + i;
            System.out.print(items[t]);
            System.out.print(" ");
        }
        System.out.println();
    }
    // : Prints the items in the deque from first to last, separated by a space. Once all the items have been printed, print out a new line.
    public T removeFirst(){
        if(front == 0){return null;}
        T item = items[size-front];
        items[size-front] = null;
        front -=1;
        if (front+rear < items.length/4){int t= size;
            resize(t);
        }
        return item;
    }
    // Removes and returns the item at the front of the deque. If no such item exists, returns null.
    public T removeLast(){
        if(rear == size){return null;}
        rear -=1;
        T item = items[size+rear];
        items[size+rear] = null;
        if (rear+front < items.length/4){int t= size;
            resize(t);
        }
        return item;
    }
    // Removes and returns the item at the back of the deque. If no such item exists, returns null.
    public T get(int index){
        return items[size-front+index];
    }
    //: Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth. If no such item exists, returns null. Must not alter the deque!
    //In addition, we also want our two Deques to implement these two special methods:

    //public Iterator<T> iterator(){}
    //The Deque objects we’ll make are iterable (i.e. Iterable<T>) so we must provide this method to return an iterator.

    //public boolean equals(Object o){}
    //Returns whether or not the parameter o is equal to the Deque. o is considered equal if it is a Deque and if it contains the same contents (as goverened by the generic T’s equals method) in the same order.
    // (ADDED 2/12: You’ll need to use the instance of keywords for this. Read here for more information)
}
