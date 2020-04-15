package lista3;

import java.util.ArrayList;
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

        int pos = randomizedSelect(array, 0, n - 1, positionalStatistic - 1);

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

        if (min > max) {
            throw new IllegalArgumentException("max must be > min");
        }

        return random.nextInt((max - min) + 1) + min;
    }

    /********************************/

    public int[] executeSelect() {
        array = initialArray.clone();
        resetCounters();

        int pos = select(array, positionalStatistic + 1);

        System.out.println("Select");
        printAfterSorting2(pos, array);

        return array;
    }


    public int select(int[] array, int position) {
        int n = array.length;
        int pivot;

        if (n < 10) {
            System.err.println("Sorting Table");
            array = insertSort(array);
            return array[position - 1];
        }

        ArrayList<int[]> arraysOfFives = createArrays(array); //grupowanie piątkami (+ew. 1 tablicą z mniejszą il. elem)
        int[] arrayOfMedians = getMedians(arraysOfFives);     //tworzenie tablicy median
        int mediansLength = arrayOfMedians.length;
        arrayOfMedians = insertSort(arrayOfMedians);        //sortowanie median

        if (mediansLength <= 5) {
            pivot = arrayOfMedians[mediansLength / 2];
        } else {
            pivot = select(arrayOfMedians, arrayOfMedians[mediansLength / 2]);
        }

        int[] low = getLow(array, pivot);
        int[] high = getHigh(array, pivot);
        int k = low.length;

        if (position < k) {
            return select(low, position);
        } else if (position > k) {
            return select(high, position - k - 1);
        } else {
            return pivot;
        }

    }


    /********************************/

    private int[] getLow(int[] array, int pivot) {
        ArrayList<Integer> lowArrayList = new ArrayList<>();
        for (int i = 0; i < array.length; i++)
            if (i < pivot)
                lowArrayList.add(i);
        return Zad2.convertIntegers(lowArrayList);
    }


    private int[] getHigh(int[] array, int pivot) {
        ArrayList<Integer> highArrayList = new ArrayList<>();
        for (int i = 0; i < array.length; i++)
            if (i > pivot)
                highArrayList.add(i);
        return Zad2.convertIntegers(highArrayList);
    }


    private int[] getMedians(ArrayList<int[]> arraysOfFives) {
        int n = arraysOfFives.size();
        int[] arrayOfMedians = new int[n];

        for (int i = 0; i < n; i++) {
            arrayOfMedians[i] = getMedian(arraysOfFives.get(i));
        }
        return arrayOfMedians;
    }


    private int getMedian(int[] arr) {
        int n = arr.length;
        arr = insertSort(arr);
        int median = arr[(int) Math.floor(n / 2)];

        return median;
    }


    private ArrayList<int[]> createArrays(int[] array) {
        ArrayList<int[]> arraysOfFives = new ArrayList<>();
        int n = array.length;
        int k = 0;

        for (int j = 0; j < n / 5; j++) {
            int[] arr = new int[5];
            for (int i = 0; i < 5; i++) {
                arr[i] = array[k];
                k++;
            }
            arraysOfFives.add(arr);
        }
        if (n % 5 != 0) {
            int[] arr2 = new int[n % 5];
            for (int i = 0; i < n % 5; i++) {
                arr2[i] = array[k];
                k++;
            }
            arraysOfFives.add(arr2);
        }
        return arraysOfFives;
    }


    public int[] insertSort(int[] array) {
        int n = array.length;

        for (int i = 1; i < n; ++i) {
            int key = array[i];
            int j = i - 1;

            while (j >= 0 && array[j] > key) {
                handleComparisons(array[j], ">", key);
                swap(array, j + 1, j);    //array[j + 1] <-> array[j]
                j--;
            }
            array[j + 1] = key;
        }
        return array;
    }


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

    public void swap(int[] arr, int index1, int index2) {
        System.err.println(arr[index1] + " <--> " + arr[index2]);
        int temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
        swapsCounter++;
    }

    public void handleComparisons(int a, String comparator, int b) {
        System.err.println(a + " " + comparator + " " + b);
        comparisonsCounter++;
    }

    public void printAfterSorting(int position, int[] array) {
        for (int j = 0; j < array.length; j++) {
            if (j == position)
                System.out.print("[" + array[j] + "] ");
            else
                System.out.print(array[j] + " ");
        }
        System.out.println();
        System.err.println("\ncomparisonsCounter: " + getComparisonsCounter());
        System.err.println("swapsCounter: " + getSwapsCounter());
    }


    public void printAfterSorting2(int pivot, int[] array) {
        for (int j = 0; j < array.length; j++) {
            if (array[j] == pivot)
                System.out.print("[" + array[j] + "] ");
            else
                System.out.print(array[j] + " ");
        }
        System.out.println();
        System.err.println("\ncomparisonsCounter: " + getComparisonsCounter());
        System.err.println("swapsCounter: " + getSwapsCounter());
    }

    public void resetCounters() {
        setComparisonsCounter(0);
        setSwapsCounter(0);
    }

}
