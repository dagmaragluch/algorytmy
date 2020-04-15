package lista2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.lang.StrictMath.abs;
import static java.lang.System.exit;

public class Main {

    public static void main(String[] args) {
        String mode = args[0]; //--type or --stat
        String typeOrFileName = args[1]; //type or nazwa_pliku
        String compOrK = args[2];
        int repeats;
        boolean isDESC_SortOrder;

        int swapsCounter = 0;
        int comparisonsCounter = 0;


        if (mode.equals("--type")) {
            String sortOrder = args[3];
            isDESC_SortOrder = setSortOrder(sortOrder);

            Scanner scanner = new Scanner(System.in);
            Scanner scanner2 = new Scanner(System.in);

            System.out.println("Enter array length");
            int n = scanner.nextInt();

            int[] numbers = new int[n];

            System.out.println("Input array elements:");

            String string = scanner2.nextLine();

            StringTokenizer st = new StringTokenizer(string, " ");

            for (int j = 0; j < n; j++) {
                numbers[j] = Integer.parseInt(st.nextToken());
                System.out.print(" " + numbers[j]);
            }
            SortingMethods sortingMethods = new SortingMethods(numbers, swapsCounter, comparisonsCounter);
            RadixSort radixSortClass = new RadixSort(numbers, swapsCounter, comparisonsCounter);
            System.out.println("\n*************");

            long startTime = System.nanoTime();

            switch (typeOrFileName) {
                case "insert":
                    sortingMethods.insertSort(true, isDESC_SortOrder);
                    break;
                case "merge":
                    sortingMethods.mergeSort(true, isDESC_SortOrder);
                    break;
                case "quick":
                    sortingMethods.quickSort(true, isDESC_SortOrder);
                    break;
                case "mquick":
                    sortingMethods.dualPivotQuickSort(true, isDESC_SortOrder);
                    break;
                case "radix":
                    radixSortClass.radixSort(true, isDESC_SortOrder);
                    break;
            }
            long stopTime = System.nanoTime();
            System.err.println("time: " + TimeUnit.NANOSECONDS.toNanos(stopTime - startTime));

        } else {
            Random random = new Random();
            repeats = Integer.parseInt(compOrK);
            try (FileWriter fileWriter = new FileWriter(typeOrFileName, false);
                 PrintWriter printWriter = new PrintWriter(new BufferedWriter(fileWriter))) {
                for (int i = 100; i <= 10000; i += 100) {
                    for (int kk = 0; kk < repeats; kk++) {
                        ArrayList list = new ArrayList<Integer>(i);
                        for (int h = 0; h < i; h++) {
                            list.add(abs(random.nextInt()));
//                            list.add(random.nextInt((9999 - 1000) + 1) + 1000);        //liczby 4-cyfrowe

                        }
                        SortingMethods sort = new SortingMethods(convertIntegers(list), 0, 0);
                        RadixSort radix = new RadixSort(sort.initialArray, 0, 0);
                        makeStatisticsFile(sort, radix, printWriter);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error, cannot create the file.");
                exit(0);
            }
        }
    }

    private static int[] convertIntegers(List<Integer> integers) {
        int[] ret = new int[integers.size()];
        Iterator<Integer> iterator = integers.iterator();
        for (int i = 0; i < ret.length; i++) {
            ret[i] = iterator.next();
        }
        return ret;
    }

    private static void makeStatisticsFile(SortingMethods sortingMethods, RadixSort radix, PrintWriter printWriter) {

        long startTime = System.nanoTime();
        int[] list = sortingMethods.insertSort(false, false);
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();   // run the garbage collector
        long memory = runtime.totalMemory() - runtime.freeMemory();
        long stopTime = System.nanoTime();
        writeToFile("InsertSort", sortingMethods, printWriter, startTime, list, stopTime, memory);

        startTime = System.nanoTime();
        list = sortingMethods.mergeSort(false, false);
        runtime = Runtime.getRuntime();
        runtime.gc();
        memory = runtime.totalMemory() - runtime.freeMemory();
        stopTime = System.nanoTime();
        writeToFile("MergeSort", sortingMethods, printWriter, startTime, list, stopTime, memory);

        startTime = System.nanoTime();
        list = sortingMethods.quickSort(false, false);
        runtime = Runtime.getRuntime();
        runtime.gc();
        memory = runtime.totalMemory() - runtime.freeMemory();
        stopTime = System.nanoTime();
        writeToFile("QuickSort", sortingMethods, printWriter, startTime, list, stopTime, memory);

        startTime = System.nanoTime();
        list = sortingMethods.dualPivotQuickSort(false, false);
        runtime = Runtime.getRuntime();
        runtime.gc();
        memory = runtime.totalMemory() - runtime.freeMemory();
        stopTime = System.nanoTime();
        writeToFile("DualPivot", sortingMethods, printWriter, startTime, list, stopTime, memory);

        startTime = System.nanoTime();
        list = radix.radixSort(false, false);
        runtime = Runtime.getRuntime();
        runtime.gc();
        memory = runtime.totalMemory() - runtime.freeMemory();
        stopTime = System.nanoTime();
        writeToFile("RadixSort", sortingMethods, printWriter, startTime, list, stopTime, memory);

    }

    private static void writeToFile(String sortType, SortingMethods sortingMethods, PrintWriter printWriter, long startTime, int[] list, long stopTime, long memory) {
        printWriter.print(sortType);
        printWriter.print(',');
        printWriter.print(list.length);
        printWriter.print(',');
        printWriter.print(sortingMethods.getComparisonsCounter());
        printWriter.print(',');
        printWriter.print(sortingMethods.getSwapsCounter());
        printWriter.print(',');
        printWriter.print(memory);
        printWriter.print(',');
        printWriter.print(TimeUnit.NANOSECONDS.toNanos(stopTime - startTime));
        printWriter.println();
    }

    private static boolean setSortOrder(String inputString) {
        if (inputString.equals("'>='")) {
            return true;
        } else if (inputString.equals("'<='")) {
            return false;
        } else {
            System.out.println("Incorrect sort order");
            exit(1);
            return false;
        }
    }
}
