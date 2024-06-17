package deque;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;

public class ArrayDequeTest {
    @Test
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
        timeAListConstruction(11164000);
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
    }
