package sharedData;

public class Main {
    public static void main(String[] args) {
        SynchronizedData sharedData = new SynchronizedData();

        GetNumbers getNumbersThread = new GetNumbers(sharedData);
        GetSum getSumThread = new GetSum(sharedData);

        getNumbersThread.start();
        getSumThread.start();
    }
}
