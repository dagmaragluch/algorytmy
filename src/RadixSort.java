import java.util.Arrays;

public class RadixSort extends SortingMethods {

    public RadixSort(int[] initialArray, int comparisonsCounter, int swapsCounter) {
        super(initialArray, comparisonsCounter, swapsCounter);
    }

    public int[] radixSort(boolean printOutcome, boolean isDESC_SortOrder) {
        array = initialArray.clone();
        resetCounters();

        int max = getMax(array);

        // sortowanie array wg pozycji cyfry
        for (int digitPlace = 1; max / digitPlace > 0; digitPlace *= 10) {
            array = countingSort(array, digitPlace, isDESC_SortOrder);
        }

        if (printOutcome) {
            System.out.println("Radix Sort");
            printAfterSorting();
        }
        return array;
    }


    private int getMax(int[] arr) {
        int max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            comparisonsCounter++;
            if (arr[i] > max)
                max = arr[i];
        }
        return max;
    }


    private int[] countingSort(int[] arr, int digitPlace, boolean isDESC_SortOrder) {

        int[] output_arr = new int[arr.length];
        int[] count_arr = new int[10];   //bo mamy 10 cyfr; odpowiednik "k" w Cormenie

        Arrays.fill(count_arr, 0);

        /* count_arr[i] będzie teraz zawierać liczbę elementów równych i;
         * "nomralnie" byłoby "count_arr[arr[j]]++"
         * ale my porównujemy nie względem całej liczby, a cyfry na konkretnej pozycji liczby
         */
        for (int j = 0; j < arr.length; j++) {
            swapsCounter++;
            count_arr[(arr[j]) / digitPlace % 10]++;
        }

        // count_arr[i] będzie teraz zawierać liczbę elementów <= i

        if(!isDESC_SortOrder){                  //ASC
            for (int i = 1; i < 10; i++) {
                swapsCounter += 0.5;
                count_arr[i] += count_arr[i - 1];
            }
        }else{                                  //DESC
            for (int i = 9; i > 0; i--) {
                swapsCounter += 0.5;
                count_arr[i-1] += count_arr[i];
            }
        }

        /*budowanie tablicy wynikowej dla elementów, które nie muszą być parami różne;
         * ponownie różnica z Cormenem ze względu na porównywanie opd cyfr a nie całej liczby
         */
        for (int i = arr.length - 1; i >= 0; i--) {
            swapsCounter++;
            output_arr[count_arr[(arr[i] / digitPlace) % 10] - 1] = arr[i];
            count_arr[(arr[i] / digitPlace % 10)]--;
        }
        return output_arr;
    }
}
