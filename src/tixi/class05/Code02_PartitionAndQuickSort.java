package tixi.class05;


public class Code02_PartitionAndQuickSort {
    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static int partition(int[] arr, int l, int r) {
        int lessEqual = l - 1;
        int index = l;
        while (index < r) {
            if (arr[index] <= arr[r]) {
                swap(arr, ++lessEqual, index);
            }
            index++;
        }
        swap(arr, ++lessEqual, r);
        return lessEqual;
    }

    public static void quickSort1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process1(arr, 0, arr.length - 1);
    }

    public static void process1(int[] arr, int l, int r) {
        if (l >= r) {
            return;
        }
        int m = partition(arr, l, r);
        process(arr, l, m - 1);
        process(arr, m + 1, r);
    }

    public static void quickSort3(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process(arr, 0, arr.length - 1);
    }

    public static void process(int[] arr, int l, int r) {
        if (l >= r) {
            return;
        }
        swap(arr, l + (int) (Math.random() * (r - l + 1)), r);
        int[] equalE = netherlandsFlag(arr, l, r);
        process(arr, l, equalE[0] - 1);
        process(arr, equalE[1] + 1, r);

    }

    public static int[] netherlandsFlag(int[] arr, int l, int r) {

        int lessR = l - 1;
        int moreR = r;
        int index = l;
        while (index < moreR) {
            if (arr[index] == arr[r]) {
                index++;
            } else if (arr[index] < arr[r]) {
                /*
                swap(arr,index,lessR+1);
                lessR++;
                index++;
                 */
                swap(arr, index++, ++lessR);
            } else {
                /*
                moreR--;
                swap(arr,index,moreR);
                 */
                swap(arr, index, --moreR);
            }
        }
        swap(arr, r, moreR);
        return new int[]{lessR + 1, moreR};
    }

}
