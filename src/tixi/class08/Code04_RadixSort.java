package tixi.class08;

import java.util.Arrays;
import java.util.Random;

public class Code04_RadixSort {

    public static void radixSort(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        radixSort(arr, 0, arr.length - 1, maxbits(arr));
    }

    public static int maxbits(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(max, arr[i]);
        }
        int res = 0;
        while (max  != 0) {
            res++;
            max /= 10;
        }
        return res;
    }

    public static int getDigit(int x, int d) {
        return ((x / (int) Math.pow(10, d - 1))) % 10;
    }

    public static void radixSort(int[] arr, int L, int R, int digits) {
        final int radix = 10;
        int i = 0, j = 0;
        int[] help = new int[R - L + 1];
        for (int d = 1; d <= digits; d++) {
            int[] count = new int[radix];
            for (i = L; i <= R; i++) {
                j = getDigit(arr[i], d);
                count[j]++;
            }
            for (i = 1; i < radix; i++) {
                count[i] = count[i] + count[i - 1];
            }
            for (i = R; i >= L; i--) {
                j = getDigit(arr[i], d);
                help[count[j] - 1] = arr[i];
                count[j]--;
            }
            for (i = L, j = 0; i <= R; i++, j++) {
                arr[i] = help[j];
            }
        }
    }

    public static void comparator(int[] arr) {
        Arrays.sort(arr);
    }

    public static int[] generateRandomArr(int len, int max) {
        int[] arr = new int[(int) (Math.random() * (len + 1))];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((max + 1) * Math.random());
        }
        return arr;
    }

    public static int[] copyArr(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] ans = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            ans[i] = arr[i];
        }
        return ans;
    }

    public static boolean isEqual(int[] arr1, int[] arr2) {
        if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
            return false;
        }
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        //76 34 72 35 65 36 22 21
        int[] arr = new int[8];
        arr[0] = 76;
        arr[1] = 34;
        arr[2] = 72;
        arr[3] = 35;
        arr[4] = 65;
        arr[5] = 36;
        arr[6] = 22;
        arr[7] = 21;
        radixSort(arr);

        int testTimes = 5000;
        int len = 10;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTimes; i++) {
            int[] arr1 = generateRandomArr(len, maxValue);
            int[] arr2 = copyArr(arr1);
            radixSort(arr1);
            comparator(arr2);
            if (!isEqual(arr1, arr2)) {
                succeed = false;
                printArray(arr1);
                printArray(arr2);
                break;
            }
        }
        System.out.println(succeed ? "没有问题" : "有问题");
    }
}
