package randomizedtest;

import org.junit.Test;
import timingtest.AList;
import edu.princeton.cs.algs4.StdRandom;


import static org.junit.Assert.assertEquals;

public class testThreeAddThreeRemove {

    @Test
    public void testThreeAddThreeRemove1() {
        AListNoResizing<Integer> A = new AListNoResizing<>();
        BuggyAList<Integer> B = new BuggyAList<>();

        A.addLast(4);
        B.addLast(4);
        A.addLast(5);
        B.addLast(5);
        A.addLast(6);
        B.addLast(6);

        assertEquals(A.size(),B.size());

        int T;
        int S;
        T =A.removeLast();
        S = B.removeLast();
        assertEquals(T,S);

        assertEquals(A.removeLast(),B.removeLast());
        assertEquals(A.removeLast(),B.removeLast());
    }
    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> B = new BuggyAList<>();

        int N = 10000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 5);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                B.addLast(randVal);
            } if (operationNumber == 1) {
                assertEquals(L.size(),B.size());
            } if (operationNumber == 2) {
                // getLast
                if (L.size() != 0) {
                assertEquals(L.getLast(),B.getLast());}
            }if (operationNumber == 3) {
                // removeLast

                if (L.size() != 0) {
                    int b = B.removeLast();
                    int t = L.removeLast();
                    assertEquals(t,b);}
            }if (operationNumber == 4) {
                //get
                if (L.size() != 0) {
                int number = StdRandom.uniform(0, L.size());
                assertEquals(L.get(number),B.get(number));}
            }
        }
        }
    }

