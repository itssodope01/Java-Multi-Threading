package partialsum;
class PartialSum implements Runnable {
    private int[] array;
    private int startIndex;
    private int endIndex;
    private long partialSum;

    public PartialSum(int[] array, int startIndex, int endIndex) {
        this.array = array;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.partialSum = 0;
    }

    public long getPartialSum() {
        return partialSum;
    }

    @Override
    public void run() {
        for (int i = startIndex; i <= endIndex; i++) {
            partialSum += array[i];
        }
    }
}
