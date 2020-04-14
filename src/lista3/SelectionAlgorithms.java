package lista3;

import java.util.Random;

public class SelectionAlgorithms {

    private int[] array;
    private final int[] initialArray;
    private int positionalStatistic;
    private int comparisonsCounter, swapsCounter;
    private final Random random = new Random();


    public SelectionAlgorithms(int[] initialArray, int positionalStatistic, int comparisonsCounter, int swapsCounter) {
        this.initialArray = initialArray;
        this.positionalStatistic = positionalStatistic;
        this.comparisonsCounter = comparisonsCounter;
        this.swapsCounter = swapsCounter;
    }

    public int[] executeRandomizedSelect() {
        array = initialArray.clone();
        resetCounters();
        int n = array.length;
        System.out.println("n = " + positionalStatistic);
        int pos = randomizedSelect(array, 0, n - 1, positionalStatistic);   //change!!!

        System.out.println("Randomized Select");
        printAfterSorting(pos, array);

        return array;
    }


    public int randomizedSelect(int[] arr, int lowerBound, int upperBound, int position) {

        for (int i = lowerBound; i < upperBound; i++) {
            System.err.print(arr[i] + " ");
        }
        System.err.println();
        System.err.println("position: " + position);

        if (upperBound == lowerBound) {
            return arr[lowerBound];
        }
        int pivot = randomizedPartition(arr, lowerBound, upperBound);
        System.err.println("pivot: " + pivot);
        int size = pivot - lowerBound + 1;
        if (position == size) {
            return arr[pivot];
        } else if (position < size) {
            return randomizedSelect(arr, lowerBound, pivot - 1, position);
        } else {
            return randomizedSelect(arr, pivot + 1, upperBound, position - size);
        }

    }


    public int randomizedPartition(int[] arr, int lowerBound, int upperBound) {

        int i = getRandomNumberInRange(lowerBound, upperBound);
        swap(arr, upperBound, i);

        return partition(arr, lowerBound, upperBound);
    }

    private int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        return random.nextInt((max - min) + 1) + min;
    }

    /********************************/

    public int partition(int[] arr, int P, int r) {
        int pivot = arr[r]; //last element as pivot
        int i = (P - 1); // index of smaller element

        for (int j = P; j < r; j++) {
            if (arr[j] <= pivot) {  //if current element <=pivot move it to left
                handleComparisons(arr[j], "<=", pivot);
                i++;
                swap(arr, i, j);
            } else {
                handleComparisons(arr[j], ">", pivot);
            }
        }
        swap(arr, i + 1, r);

        return i + 1;
    }

    public int getComparisonsCounter() {
        return comparisonsCounter;
    }

    public int getSwapsCounter() {
        return swapsCounter;
    }

    public void setComparisonsCounter(int comparisonsCounter) {
        this.comparisonsCounter = comparisonsCounter;
    }

    public void setSwapsCounter(int swapsCounter) {
        this.swapsCounter = swapsCounter;
    }

    public void swap(int[] arr1, int[] arr2, int index1, int index2) {
//        System.err.println(arr1[index1] + " <--> " + arr2[index2]);
        int temp = arr1[index1];
        arr1[index1] = arr2[index2];
        arr2[index2] = temp;
        swapsCounter++;
    }

    public void swap(int[] arr, int index1, int index2) {
//        System.err.println(arr[index1] + " <--> " + arr[index2]);
        int temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
        swapsCounter++;
    }

    public void handleComparisons(int a, String comparator, int b) {
//        System.err.println(a + " " + comparator + " " + b);
        comparisonsCounter++;
    }

//    public void printAfterSorting(int position) {
////        System.out.println("\nNumber of elements: " + this.array.length);
//        for (int j = 0; j < this.array.length; j++) {
//            if (j == position) {
//                System.out.print(" [" + this.array[j] + "] ");
//            } else
//                System.out.print(this.array[j] + " ");
//        }
//        System.out.println();
//        System.err.println("\ncomparisonsCounter: " + getComparisonsCounter());
//        System.err.println("swapsCounter: " + getSwapsCounter());
//    }

    public void printAfterSorting(int position, int[] array) {
        for (int j = 0; j < array.length; j++) {
            if (j == position)
                System.out.print("[" + array[j] + "] ");
            else
                System.out.print(array[j] + " ");
        }
        System.out.println();
//        System.err.println("\ncomparisonsCounter: " + getComparisonsCounter());
//        System.err.println("swapsCounter: " + getSwapsCounter());
    }

    public void resetCounters() {
        setComparisonsCounter(0);
        setSwapsCounter(0);
    }

}
