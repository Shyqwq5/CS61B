package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T>{
    private Comparator<T> comparator;

    public MaxArrayDeque(Comparator<T> c){
        this.comparator = c;

    }
    //creates a MaxArrayDeque with the given Comparator.
    public T max(){
        if (this.size() == 0){
            return null;
        }
        T start = this.get(0);
        for(int i=1;i<this.size();i++) {
            if (this.comparator.compare(start, this.get(i)) < 0) {
                start = this.get(i);
            }}
        return start;
        }


    //returns the maximum element in the deque as governed by the previously given Comparator.
    // If the MaxArrayDeque is empty, simply return null.
    public T max(Comparator<T> c){
            if (this.size() == 0){
                return null;
            }
            T start = this.get(0);
            for(int i=1;i<this.size();i++) {
                if (c.compare(start, this.get(i)) < 0) {
                    start = this.get(i);
                }
            }
        return start;
    }


    //returns the maximum element in the deque as governed by the parameter Comparator c.
    //If the MaxArrayDeque is empty, simply return null.
}
