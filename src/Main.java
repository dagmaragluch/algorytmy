import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        String mode = args[0]; //--type or --stat
        String typeOrFileName = args[1]; //type or nazwa_pliku
        String sortOrderOrK = args[2];
        int repeats;

        int swapsCounter = 0;
        int comparisonsCounter = 0;

        if (mode.equals("--type")) {
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
            System.out.println("\n*************");

            if (typeOrFileName.equals("insert")) {
                sortingMethods.insertSort(true);
            } else if (typeOrFileName.equals("merge")) {
                sortingMethods.mergeSort(true);
            } else if (typeOrFileName.equals("quick")) {
                sortingMethods.quickSort(true);
            }
        } else {
            Random random = new Random();
            repeats = Integer.parseInt(sortOrderOrK);
            try (FileWriter fileWriter = new FileWriter(typeOrFileName, false);
                 PrintWriter printWriter = new PrintWriter(new BufferedWriter(fileWriter))) {
                for (int i = 100; i <= 10000; i += 100) {
                    for (int kk = 0; kk < repeats; kk++) {
                        ArrayList list = new ArrayList<Integer>(i);
                        for (int h = 0; h < i; h++) {
                            list.add(random.nextInt());
                        }
                        SortingMethods sort = new SortingMethods(convertIntegers(list), 0, 0);

                        makeStatisticsFile(sort, printWriter);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error, cannot create the file.");
                System.exit(0);
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

    private static void makeStatisticsFile(SortingMethods sortingMethods, PrintWriter printWriter) {
        long startTime = System.nanoTime();
        int[] list = sortingMethods.selectSort(false);
        long stopTime = System.nanoTime();
        writeToFile("SelectSort", sortingMethods, printWriter, startTime, list, stopTime);

        startTime = System.nanoTime();
        list = sortingMethods.insertSort(false);
        stopTime = System.nanoTime();
        writeToFile("InsertSort", sortingMethods, printWriter, startTime, list, stopTime);

        startTime = System.nanoTime();
        list = sortingMethods.quickSort(false);
        stopTime = System.nanoTime();
        writeToFile("QuickSort", sortingMethods, printWriter, startTime, list, stopTime);

    }

    private static void writeToFile(String sortType, SortingMethods sortingMethods, PrintWriter printWriter, long startTime, int[] list, long stopTime) {
        printWriter.print(sortType);
        printWriter.print(',');
        printWriter.print(list.length);
        printWriter.print(',');
        printWriter.print(sortingMethods.getComparisonsCounter());
        printWriter.print(',');
        printWriter.print(sortingMethods.getSwapsCounter());
        printWriter.print(',');
        printWriter.print(TimeUnit.NANOSECONDS.toNanos(stopTime - startTime));
        printWriter.println();
    }
}
