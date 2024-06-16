package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeAList {

    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
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
    timeAListConstruction(64000);
    }

    public static void timeAListConstruction(int a) {
        // TODO: YOUR CODE HERE
        AList<Double> times = new AList<>();
        AList<Integer> Ns = new AList<>();
        AList<Integer> opCounts = new AList<>();


        for (int i = 1000; i <= a; i *=2) {
            AList<Integer> test = new AList<>();
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
