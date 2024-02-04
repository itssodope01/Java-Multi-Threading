package partialsum;

import java.util.Random;

public class MultiThreadedSum {
    public static void main(String[] args) {
        int N = 10000;
        int K = 8; //K number of threads
        int[] array = new int[N];

        //filling array with pseudo-random numbers
        Random random = new Random();
        for (int i = 0; i < N; i++) {
            array[i] = random.nextInt(100); //bound
        }

        //calculating partial sums using Runnable interface
        PartialSum[] partialSumsRunnable = new PartialSum[K];
        int m = N / K;
        Thread[] threadsRunnable = new Thread[K];

        for (int i = 0; i < K; i++) {
            partialSumsRunnable[i] = new PartialSum(array, i * m, (i + 1) * m - 1);
            threadsRunnable[i] = new Thread(partialSumsRunnable[i]);
            threadsRunnable[i].start();
        }

        try {
            for (Thread thread : threadsRunnable) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //calculating total sum from partial sums (using Runnable)
        long totalSumRunnable = 0;
        for (PartialSum partialSum : partialSumsRunnable) {
            totalSumRunnable += partialSum.getPartialSum();
        }

        //calculating total sum in conventional way
        long totalSumConventional = 0;
        for (int num : array) {
            totalSumConventional += num;
        }

        //compare results
        System.out.println("Total Sum (Runnable): " + totalSumRunnable);
        System.out.println("Total Sum (Conventional): " + totalSumConventional);
    }
}