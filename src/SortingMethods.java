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
                    handleComparisons(array[j], "<", key);
                    swap(array, array, j + 1, j);    //array[j + 1] <=> array[j]
                    j--;
                }
            } else {
                while (j >= 0 && array[j] > key) {
                    handleComparisons(array[j], ">", key);
                    swap(array, array, j + 1, j);    //array[j + 1] <=> array[j]
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
            mergeSorting(array, p, r, isDESC_SortOrder);
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
            if (isDESC_SortOrder) {
                if (L[i] >= R[j]) {     // DESC sort order
                    handleComparisons(L[i], ">=", R[j]);
                    arr[k] = L[i];
                    i++;
                } else {
                    handleComparisons(L[i], "<", R[j]);
                    swap(arr, R, k, j);    //arr[k] <=> R[j];
                    j++;
                }
            } else {
                if (L[i] <= R[j]) {     // ASC sort order
                    handleComparisons(L[i], "<=", R[j]);
                    arr[k] = L[i];
                    i++;
                } else {
                    handleComparisons(L[i], ">", R[j]);
                    swap(arr, R, k, j);    //arr[k] <=> R[j];
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

    void mergeSorting(int[] arr, int p, int r, boolean isDESC_SortOrder) {
        if (p < r) {
            int m = (int) Math.floor((p + r) * 0.5);     // middle point

            //sort both subarrays
            mergeSorting(arr, p, m, isDESC_SortOrder);
            mergeSorting(arr, m + 1, r, isDESC_SortOrder);

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

        quickSorting(array, p, r, isDESC_SortOrder);

        if (printOutcome) {
            System.out.println("Quick Sort");
            printAfterSorting();
        }
        return array;
    }


    void quickSorting(int[] arr, int p, int r, boolean isDESC_SortOrder) {
        if (p < r) {
            int q = partition(arr, p, r, isDESC_SortOrder);   //partition element

            quickSorting(arr, p, q - 1, isDESC_SortOrder);      //recursively sort two sub-arrays
            quickSorting(arr, q + 1, r, isDESC_SortOrder);
        }
    }


    int partition(int[] arr, int P, int r, boolean isDESC_SortOrder) {
        int pivot = arr[r]; //last element as pivot
        int i = (P - 1); // index of smaller element

        if (isDESC_SortOrder) {     //DESC
            for (int j = P; j < r; j++) {
                if (arr[j] >= pivot) {  //if current element <=pivot move it to left
                    handleComparisons(arr[j], ">=", pivot);
                    i++;
                    swap(arr, arr, i, j);
                } else {
                    handleComparisons(arr[j], "<", pivot);
                }
            }
        } else {        //ASC
            for (int j = P; j < r; j++) {
                if (arr[j] <= pivot) {  //if current element <=pivot move it to left
                    handleComparisons(arr[j], "<=", pivot);
                    i++;
                    swap(arr, arr, i, j);
                } else {
                    handleComparisons(arr[j], ">", pivot);
                }
            }
        }
        swap(arr, arr, i + 1, r);

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

    public void swap(int[] arr1, int[] arr2, int index1, int index2) {
        System.err.println(arr1[index1] + " <--> " + arr2[index2]);
        int temp = arr1[index1];
        arr1[index1] = arr2[index2];
        arr2[index2] = temp;
        swapsCounter++;
    }

    public void handleComparisons(int a, String comparator, int b) {
        System.err.println(a + " " + comparator + " " + b);
        comparisonsCounter++;
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


    /********************************/

    public int[] dualPivotQuickSort(boolean printOutcome, boolean isDESC_SortOrder) {
        array = initialArray.clone();
        resetCounters();
        int n = array.length;

        ///

        if (printOutcome) {
            System.out.println("Insert Sort");
            printAfterSorting();
        }
        return array;
    }

    void dualPivotQuicksorting(int[] arr, int left, int right, boolean isDESC_SortOrder) {
        if (arr[right] > arr[left]) {
//            swap(arr[left], arr[right]);
        }

        int p = arr[left];
        int q = arr[right];

//        partitionDualPivot(arr, p, q, posP, posQ);

    }

    public void rotate3(int a, int b, int c) {
        int temp = a;
        a = b;
        b = c;
        c = temp;
//        System.err.println(a + "  <-->'  " + b);
//        swapsCounter++;
    }
}
