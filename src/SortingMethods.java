public class SortingMethods {
    private int[] array, initialArray;
    private int comparisonsCounter, swapsCounter;


    public SortingMethods(int[] initialArray, int comparisonsCounter, int swapsCounter) {
        this.initialArray = initialArray;
        this.comparisonsCounter = comparisonsCounter;
        this.swapsCounter = swapsCounter;
    }

    public int[] insertSort(boolean printOutcome, boolean isDESC_SortOrder) {
        array = initialArray.clone();
        resetCounters();
        int n = array.length;

        for (int i = 1; i < n; ++i) {
            int key = array[i];
            int j = i - 1;

            if (isDESC_SortOrder) {
                //insert arr[i] into sorted arr[0..i-1]
                while (j >= 0 && array[j] < key) {
                    System.err.println(array[j] + "  <  " + key);
                    System.err.println(array[j + 1] + "  <-->  " + array[j]);
                    comparisonsCounter++;
                    swapsCounter++;
                    array[j + 1] = array[j];
                    j--;
                }
            } else {
                while (j >= 0 && array[j] > key) {
                    System.err.println(array[j] + "  >  " + key);
                    System.err.println(array[j + 1] + "  <-->  " + array[j]);
                    comparisonsCounter++;
                    swapsCounter++;
                    array[j + 1] = array[j];
                    j--;
                }
            }
            array[j + 1] = key;
        }
        if (printOutcome) {
            System.out.println("Insert Sort");
            printAfterSorting();
        }
        return array;
    }

    /********/

    public int[] mergeSort(boolean printOutcome, boolean isDESC_SortOrder) {
        array = initialArray.clone();
        resetCounters();
        int n = array.length;

        if (n > 1) {
            int p = 0;
            int r = n - 1;
            mergesorting(array, p, r, isDESC_SortOrder);
        }

        if (printOutcome) {
            System.out.println("Merge Sort");
            printAfterSorting();
        }
        return array;
    }


    void merge(int[] arr, int p, int q, int r, boolean isDESC_SortOrder) {

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
            comparisonsCounter++;

            if (isDESC_SortOrder) {
                if (L[i] >= R[j]) {     // DESC sort order
                    System.err.println(L[i] + "  >=  " + R[j]);
                    arr[k] = L[i];
                    i++;
                } else {
                    System.err.println(L[i] + "  < " + R[j]);
                    System.err.println(arr[k] + "  <-->  " + R[j]);
                    swapsCounter++;
                    arr[k] = R[j];
                    j++;
                }
            } else {
                if (L[i] <= R[j]) {     // ASC sort order
                    System.err.println(L[i] + "  <=  " + R[j]);
                    arr[k] = L[i];
                    i++;
                } else {
                    System.err.println(L[i] + "  >  " + R[j]);
                    System.err.println(arr[k] + "  <-->  " + R[j]);
                    swapsCounter++;
                    arr[k] = R[j];
                    j++;
                }
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

    void mergesorting(int[] arr, int p, int r, boolean isDESC_SortOrder) {
        if (p < r) {
            int m = (int) Math.floor((p + r) * 0.5);     // middle point

            //sort both subarrays
            mergesorting(arr, p, m, isDESC_SortOrder);
            mergesorting(arr, m + 1, r, isDESC_SortOrder);

            //merge sorted arrays
            merge(arr, p, m, r, isDESC_SortOrder);
        }
    }


    /***********/
    public int[] quickSort(boolean printOutcome, boolean isDESC_SortOrder) {
        array = initialArray.clone();
        resetCounters();
        int p = 0;    //start index
        int r = array.length - 1;  //ending index

        quicksorting(array, p, r, isDESC_SortOrder);

        if (printOutcome) {
            System.out.println("Quick Sort");
            printAfterSorting();
        }
        return array;
    }

    /***********/

    void quicksorting(int[] arr, int p, int r, boolean isDESC_SortOrder) {
//        comparisonsCounter++;
        if (p < r) {
            int q = partition(arr, p, r, isDESC_SortOrder);   //partition element

            quicksorting(arr, p, q - 1, isDESC_SortOrder);      //recursively sort two sub-arrays
            quicksorting(arr, q + 1, r, isDESC_SortOrder);
        }
    }


    int partition(int[] arr, int P, int r, boolean isDESC_SortOrder) {
        int pivot = arr[r]; //last element as pivot
        int i = (P - 1); // index of smaller element

        if (isDESC_SortOrder) {     //DESC
            for (int j = P; j < r; j++) {
                comparisonsCounter++;
                if (arr[j] >= pivot) {  //if current element <=pivot move it to left
                    System.err.println(arr[j] + "  >=  " + pivot);
                    i++;
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                    System.err.println(arr[i] + "  <-->  " + arr[j]);
                    swapsCounter++;
                } else {
                    System.err.println(arr[j] + "  <  " + pivot);
                }
            }
        } else {        //ASC
            for (int j = P; j < r; j++) {
                comparisonsCounter++;
                if (arr[j] <= pivot) {  //if current element <=pivot move it to left
                    System.err.println(arr[j] + "  <=  " + pivot);
                    i++;
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                    System.err.println(arr[i] + "  <-->  " + arr[j]);
                    swapsCounter++;
                } else {
                    System.err.println(arr[j] + "  >  " + pivot);
                }
            }
        }

        int temp = arr[i + 1];
        arr[i + 1] = arr[r];
        arr[r] = temp;
        swapsCounter++;
        System.err.println(arr[i + 1] + "  <-->  " + arr[r]);

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
        System.out.println("\nNumber of elements: " + this.array.length);
        for (int j = 0; j < this.array.length; j++) {
            System.out.print(this.array[j] + " ");
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
