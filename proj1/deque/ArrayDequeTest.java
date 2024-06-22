package deque;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ArrayDequeTest {
    @Test
    public void addLast() {
        Deque<Integer> L = new ArrayDeque<>();
        LinkedListDeque<Integer> B = new LinkedListDeque<>();
        int N = 500;
        for (int i = 0; i < N; i += 1) {
            int randVal = StdRandom.uniform(0, 100);
            L.addLast(randVal);
            B.addLast(randVal);}
        assertEquals(B.get(200),L.get(200));
        }
    @Test
    public void addFirst() {
        ArrayDeque<Integer> L = new ArrayDeque<>();
        LinkedListDeque<Integer> B = new LinkedListDeque<>();
        int N = 500;
        for (int i = 0; i < N; i += 1) {
            int randVal = StdRandom.uniform(0, 100);
            L.addFirst(randVal);
            B.addFirst(randVal);}
        assertEquals(B.get(200),B.getRecursive(200));
    }

    @Test
    public void removeFirstandLast() {
        ArrayDeque<Integer> L = new ArrayDeque<>();
        LinkedListDeque<Integer> B = new LinkedListDeque<>();
        int N = 50;
        for (int i = 0; i < N; i += 1) {
            int randVal = StdRandom.uniform(0, 100);
            L.addFirst(randVal);
            B.addFirst(randVal);}
        for (int i = 0; i < N; i += 1) {
            int randVal = StdRandom.uniform(0, 100);
            L.addLast(randVal);
            B.addLast(randVal);}
        assertEquals(B.get(20),L.get(20));
        for (int i = N/2; i < N; i += 1) {
            L.removeFirst();
            B.removeFirst();}
        assertEquals(B.get(25),L.get(25));
        for (int i = N/2; i < N; i += 1) {
            L.removeLast();
            B.removeLast();}
        int a = B.get(0);
        int b = L.get(0);
        assertEquals(a,b);

    }
    @Test
    public void print() {
        ArrayDeque<Integer> L = new ArrayDeque<>();
        LinkedListDeque<Integer> B = new LinkedListDeque<>();
        int N = 50;
        for (int i = 0; i < N; i += 1) {
            int randVal = StdRandom.uniform(0, 100);
            L.addFirst(randVal);
            B.addFirst(randVal);}
        for (int i = 0; i < N; i += 1) {
            int randVal = StdRandom.uniform(0, 100);
            L.addLast(randVal);
            B.addLast(randVal);}
        L.printDeque();
        B.printDeque();

    }
    @Test
    public void get0() {
        ArrayDeque<Integer> L = new ArrayDeque<>();
        LinkedListDeque<Integer> B = new LinkedListDeque<>();
        int N = 10;
        for (int i = 0; i < N; i += 1) {
            int randVal = StdRandom.uniform(0, 100);
            L.addFirst(randVal);
            B.addFirst(randVal);
            assertEquals(B.get(0),L.get(0));}
        for (int i = 0; i < N; i += 1) {
            L.removeFirst();
            B.removeFirst();
            assertEquals(B.get(0),L.get(0));}
        for (int i = 0; i < N; i += 1) {
            int randVal = StdRandom.uniform(0, 100);
            L.addLast(randVal);
            B.addLast(randVal);
            assertEquals(B.get(0),L.get(0));}
        for (int i = 0; i < N; i += 1) {
            L.removeLast();
            B.removeLast();
            assertEquals(B.get(0),L.get(0));
        assertEquals(B.getRecursive(0),L.get(0));}
        L.printDeque();
        B.printDeque();

    }
}


   /*@Test
    public void addIsEmptySizeTest() {}

    private static void printTimingTable(ArrayDeque<Integer> Ns, ArrayDeque<Double> times, ArrayDeque<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeAListConstruction(1614000);
    }

    public static void timeAListConstruction(int a) {
        // TODO: YOUR CODE HERE
        ArrayDeque<Double> times = new ArrayDeque<>();
        ArrayDeque<Integer> Ns = new ArrayDeque<>();
        ArrayDeque<Integer> opCounts = new ArrayDeque<>();


        for (int i = 1000; i <= a; i *=2) {
            ArrayDeque<Integer> test = new ArrayDeque<>();
            Stopwatch sw = new Stopwatch();
            int ii =0;
            while (ii<=i) { test.addLast(1);ii+=1;}
            double timeInSeconds = sw.elapsedTime();
            times.addLast(timeInSeconds);
            Ns.addLast(i);
            opCounts.addLast(i);}

        printTimingTable(Ns,times,opCounts);
    }
    @Test
    public void testThreeAddThreeRemove1() {
        ArrayDeque<Integer> A = new ArrayDeque<>();
        LinkedListDeque<Integer> B = new LinkedListDeque<>();

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
    public void randomizedTest1() {
        ArrayDeque<Integer> L = new ArrayDeque<>();
        LinkedListDeque<Integer> B = new LinkedListDeque<>();

        int N = 500;
        for (int i = 1; i < N; i += 1) {

            // addLast
            int randVal = StdRandom.uniform(0, 100);
            L.addLast(randVal);
            B.addLast(randVal);
            int randVal2 = StdRandom.uniform(0, 100);
            L.addFirst(randVal2);
            B.addFirst(randVal2);

            if (L.size() != 0) {
            int number = StdRandom.uniform(0, L.size());
            int a= L.get(number);
            int b= B.get(number);
            assertEquals(a, b);}

        }

    }
    @Test
    public void randomizedTest() {
        ArrayDeque<Integer> L = new ArrayDeque<>();
        LinkedListDeque<Integer> B = new LinkedListDeque<>();

        int N = 10000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 5);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                B.addLast(randVal);
            }
            if (operationNumber == 1) {
                assertEquals(L.size(), B.size());
            }
            if (operationNumber == 2) {
                // getLast
                if (L.size() != 0) {
                    assertEquals(L.get(1), B.get(1));
                }
            }
            if (operationNumber == 3) {
                // removeLast

                if (L.size() != 0) {

                    assertEquals(L.removeLast(), B.removeLast());
                }
            }
            if (operationNumber == 4) {
                //get
                if (L.size() != 0) {
                    int number = StdRandom.uniform(0, L.size());
                    int a= L.get(number);
                    int b= B.get(number);
                    assertEquals(a, b);
                }
            }
        }
    }}*/


