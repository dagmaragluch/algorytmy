package lista3;

import java.util.*;

import static java.lang.StrictMath.abs;

public class Zad2 {

    public static void main(String[] args) {
        String mode = args[0]; //-r or -p
        Random random = new Random();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter array length");
        int n = scanner.nextInt();
        System.out.println("Enter k - number of positional statistic (1 <= k <= n):");
        int k = scanner.nextInt();
        if (k <= 0 || k > n)
            throw new IllegalArgumentException("k must be in range (1 <= k <= n)");

        switch (mode) {
            case "-r":
                ArrayList list = new ArrayList<Integer>();
                for (int i = 0; i < n; i++) {
                    list.add(abs(random.nextInt(1000)));
                }
                SelectionAlgorithms selectionAlgorithms = new SelectionAlgorithms(convertIntegers(list), k, 0, 0);
                selectionAlgorithms.executeRandomizedSelect();
                selectionAlgorithms.executeSelect();
                break;

            case "-p":
                int[] array = generateRandomPermutation(n);
                SelectionAlgorithms selectionAlgorithms2 = new SelectionAlgorithms(array, k, 0, 0);
                selectionAlgorithms2.executeRandomizedSelect();
                selectionAlgorithms2.executeSelect();
                break;
        }

    }


    public static int[] convertIntegers(List<Integer> integers) {
        int[] ret = new int[integers.size()];
        Iterator<Integer> iterator = integers.iterator();
        for (int i = 0; i < ret.length; i++) {
            ret[i] = iterator.next();
        }
        return ret;
    }

    static int getNum(ArrayList<Integer> v) {
        int n = v.size();
        int index = (int) (Math.random() * n);
        int num = v.get(index);

        v.set(index, v.get(n - 1));
        v.remove(n - 1);

        return num;
    }

    static int[] generateRandomPermutation(int n) {
        ArrayList<Integer> v = new ArrayList<>(n);
        int[] arr = new int[n];
        for (int i = 0; i < n; i++)
            v.add(i + 1);

        int i = 0;
        while (v.size() > 0) {
            arr[i] = getNum(v);
            i++;
        }
        return arr;
    }


}
