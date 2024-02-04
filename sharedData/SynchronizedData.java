package sharedData;
import java.util.Scanner;

class SynchronizedData {
    //shared array
    private int[] numbers = new int[5];
    private boolean filled = false;

    //synchronized method to get numbers from the keyboard and fill the array
    public synchronized void getNumbers() {
        while (filled) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter " + numbers.length + " numbers(separated by space or press enter after each number):");
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = scanner.nextInt();
        }
        
        //notifying GetSum thread that the array is filled
        filled = true;
        notify();
    }

    //synchronized method to get the sum, print it, and fill the array with zeros
    public synchronized void getSum() {
        while (!filled) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //calculating and printing the sum
        int sum = 0;
        for (int num : numbers) {
            sum += num;
        }
        System.out.println("Sum: " + sum);

        //filling the array with zeros
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = 0;
        }

        //setting filled to false and notifying GetNumbers thread
        filled = false;
        notify();

        //artificial delay
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class GetNumbers extends Thread {
    private SynchronizedData sharedData;

    public GetNumbers(SynchronizedData sharedData) {
        this.sharedData = sharedData;
    }

    public void run() {
        while (true) {
            sharedData.getNumbers();
        }
    }
}

class GetSum extends Thread {
    private SynchronizedData sharedData;

    public GetSum(SynchronizedData sharedData) {
        this.sharedData = sharedData;
    }

    public void run() {
        while (true) {
            sharedData.getSum();
        }
    }
}


