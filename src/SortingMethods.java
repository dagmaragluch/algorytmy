public class SortingMethods {
    protected int[] array, initialArray;
    protected int comparisonsCounter, swapsCounter;


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
                    swap(array, j + 1, j);    //array[j + 1] <=> array[j]
                    j--;
                }
            } else {
                while (j >= 0 && array[j] > key) {
                    handleComparisons(array[j], ">", key);
                    swap(array, j + 1, j);    //array[j + 1] <=> array[j]
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
                    swap(arr, i, j);
                } else {
                    handleComparisons(arr[j], "<", pivot);
                }
            }
        } else {        //ASC
            for (int j = P; j < r; j++) {
                if (arr[j] <= pivot) {  //if current element <=pivot move it to left
                    handleComparisons(arr[j], "<=", pivot);
                    i++;
                    swap(arr, i, j);
                } else {
                    handleComparisons(arr[j], ">", pivot);
                }
            }
        }
        swap(arr, i + 1, r);

        return i + 1;
    }


    /********************************/

    public int[] dualPivotQuickSort(boolean printOutcome, boolean isDESC_SortOrder) {
        array = initialArray.clone();
        resetCounters();
        int left = 0;
        int right = array.length - 1;

        dualPivotQuickSorting(array, left, right, isDESC_SortOrder);

        if (printOutcome) {
            System.out.println("Dual-Pivot Quick Sort");
            printAfterSorting();
        }
        return array;
    }


    void dualPivotQuickSorting(int[] arr, int left, int right, boolean isDESC_SortOrder) {

        if (right - left >= 1) {
            int[] positionsOfPivots = partitionCount(arr, left, right);
            int posP = positionsOfPivots[0];
            int posQ = positionsOfPivots[1];
            if (posQ != 0) {
                dualPivotQuickSorting(arr, left, posP - 1, false);
                dualPivotQuickSorting(arr, posP + 1, posQ - 1, false);
                dualPivotQuickSorting(arr, posQ + 1, right, false);
            }
        }
    }

    public void rotate3(int[] arr, int a, int b, int c) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = arr[c];
        arr[c] = temp;
//        System.err.println(arr[a] + " <-->' " + arr[b] + " <-->' " + arr[c]);
        swapsCounter += 2;
    }

    public int[] partitionCount(int[] arr, int left, int right) {
        int p;  //left pivot
        int q;  //right pivot
        int i = left + 1;
        int k = right - 1;
        int j = i;
        int d = 0;  // holds the difference between small and large elements
        int[] positionsOfPivots = new int[2];   //tab[0] = posP, tab[1] = posQ

        if (right <= left) return null;

        if (arr[right] < arr[left]) {
            handleComparisons(arr[right], "<", arr[left]);
            p = arr[right];
            q = arr[left];
        } else {
            handleComparisons(arr[right], ">=", arr[left]);
            p = arr[left];
            q = arr[right];
        }

        while (j <= k) {
            if (d >= 0) {
                if (arr[j] < p) {
                    handleComparisons(arr[j], "<", p);
                    swap(arr, i, j);
                    i++;
                    j++;
                    d++;
                } else {
                    handleComparisons(arr[j], ">=", p);
                    if (arr[j] < q) {
                        handleComparisons(arr[j], "<", q);
                        j++;
                    } else {
                        handleComparisons(arr[j], ">=", p);
                        swap(arr, j, k);
                        k--;
                        d--;
                    }
                }
            } else {
                if (arr[k] > q) {
                    handleComparisons(arr[k], ">", q);
                    k--;
                    d--;
                } else {
                    handleComparisons(arr[k], "<=", q);
                    if (arr[k] < p) {
                        handleComparisons(arr[k], "<", p);
                        rotate3(arr, k, j, i);
                        i++;
                        d++;
                    } else {
                        handleComparisons(arr[k], ">=", p);
                        swap(arr, j, k);
                    }
                    j++;
                }
            }
        }

        arr[left] = arr[i - 1];     //something like 2 swaps
        arr[i - 1] = p;

//        System.err.println(arr[left] + " <--> " + arr[k + 1]);
//        System.err.println(arr[right] + " <--> " + arr[i - 1]);

        arr[right] = arr[k + 1];
        arr[k + 1] = q;
        swapsCounter += 2;

        int posP = i - 1;
        int posQ = k + 1;
        positionsOfPivots[0] = posP;
        positionsOfPivots[1] = posQ;

        return positionsOfPivots;
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
