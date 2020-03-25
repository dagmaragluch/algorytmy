public class SortingMethods {
    private int[] array, initialArray;
    private int comparisonsCounter, swapsCounter;


    /**
     * ZMIENIĆ LICZNIKI PORÓWNAŃ  - NIE TYLKO PRZY TRUE JE ZMIENIAĆ
     */


    public SortingMethods(int[] initialArray, int comparisonsCounter, int swapsCounter) {
        this.initialArray = initialArray;
        this.comparisonsCounter = comparisonsCounter;
        this.swapsCounter = swapsCounter;
    }

    public int[] selectSort(boolean printOutcome) {
        array = initialArray.clone();
        resetCounters();
        int n = array.length;

        //one by one move boundary of unsorted subarray
        for (int i = 0; i < n - 1; i++) {
            //looking for minimum element
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
                comparisonsCounter++;
            }

            //swap the found minimum element with the first element
            int temp = array[minIndex];
            array[minIndex] = array[i];
            array[i] = temp;
            swapsCounter++;
        }
        if (printOutcome) {
            System.out.println("Select Sort");
            printAfterSorting();
        }
        return array;
    }


    public int[] insertSort(boolean printOutcome) {
        array = initialArray.clone();
        resetCounters();
        int n = array.length;

        for (int i = 1; i < n; ++i) {
            int key = array[i];
            int j = i - 1;
            //insert arr[i] into sorted arr[0..i-1]
            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j = j - 1;
                swapsCounter++;
            }
            comparisonsCounter++;
            array[j + 1] = key;
        }
        if (printOutcome) {
            System.out.println("Insert Sort");
            printAfterSorting();
        }
        return array;
    }

    /********/

    public int[] mergeSort(boolean printOutcome) {
        array = initialArray.clone();
        resetCounters();
        int n = array.length;

        if (n > 1) {
            int p = 0;
//            int q = p;
            int r = (int) Math.floor(n * 0.5);
            mergesorting(array, p, r);
        }


        if (printOutcome) {
            System.out.println("Merge Sort");
            printAfterSorting();
        }
        return array;
    }


    void merge(int[] arr, int p, int q, int r) {

        int n1 = q - p + 1;  //find sizes of subarrays
        int n2 = r - q;

        int[] L = new int[n1];  //create temp arrays
        int[] R = new int[n2];

        //fill temp arrays - copy data
        for (int i = 0; i < n1; ++i)
            L[i] = arr[p + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[q + 1 + j];

        int i = 0;      //indexes of both subarrays
        int j = 0;

        // initial index of merged subarry array
        int k = p;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }
        // copy remaining elements of L[] if any
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }
        //copy remaining elements of R[] if any
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    void mergesorting(int[] arr, int p, int r) {
        if (p < r) {
            // find middle point
            int m = (p + r) / 2;

            //sort both subarrays
            mergesorting(arr, p, m);
            mergesorting(arr, m + 1, r);

            //merge sorted arrays
            merge(arr, p, m, r);
        }
    }

    /***********/
    public int[] quickSort(boolean printOutcome) {
        array = initialArray.clone();
        resetCounters();
        int p = 0;    //start index
        int r = array.length - 1;  //ending index

        quicksorting(array, p, r);

        if (printOutcome) {
            System.out.println("Quick Sort");
            printAfterSorting();
        }
        return array;
    }

    /***********/

    void quicksorting(int[] arr, int p, int r) {
        comparisonsCounter++;
        if (p < r) {
            int q = partition(arr, p, r);   //partition element

            quicksorting(arr, p, q - 1);      //recursively sort two sub-arrays
            quicksorting(arr, q + 1, r);
        }
    }


    int partition(int[] arr, int P, int r) {
        int pivot = arr[r]; //last element as pivot
        int i = (P - 1); // index of smaller element

        for (int j = P; j < r; j++) {

            if (arr[j] <= pivot) {  //if current element <=pivot move it to left
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                swapsCounter++;
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[r];
        arr[r] = temp;
        swapsCounter++;

        return i + 1;
    }

    /********************************/

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


    public void printAfterSorting() {
        for (int j = 0; j < this.array.length; j++) {
            System.out.print(this.array[j] + " ");
        }
        System.out.println("\ncomparisonsCounter: " + getComparisonsCounter());
        System.out.println("swapsCounter: " + getSwapsCounter());
    }

    public void resetCounters() {
        setComparisonsCounter(0);
        setSwapsCounter(0);
    }


}
