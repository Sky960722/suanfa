package rumeng.class08;

import java.util.Stack;

public class Code03_PartitionAndQuickSort {

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static int[] partition(int[] arr, int L, int R) {
        int lessR = L - 1;
        int moreL = R;
        int index = L;
        while (index < moreL) {
            if (arr[index] < arr[R]) {
                swap(arr, lessR+1, index);
                lessR++;
                index++;
            } else if (arr[index] > arr[R]) {
                moreL--;
                swap(arr, moreL, index);
            } else {
                index++;
            }
        }
        swap(arr, moreL, R);
        return new int[]{lessR + 1, moreL};
    }

    public static void quickSort1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        process(arr, 0, arr.length - 1);
    }

    public static void process(int[] arr, int L, int R) {
        if (L >= R) {
            return;
        }
        int[] equalE = partition(arr, L, R);
        process(arr, L, equalE[0] - 1);
        process(arr, equalE[1] + 1, R);

    }

    public static class Job {
        public int L;
        public int R;

        public Job(int left, int right) {
            this.L = left;
            this.R = right;
        }
    }

    public static void quickSort2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        Stack<Job> stack = new Stack<>();
        stack.push(new Job(0, arr.length - 1));
        while (!stack.isEmpty()) {
            Job cur = stack.pop();
            int[] equals = partition(arr, cur.L, cur.R);
            if(equals[0] > cur.L){
                stack.push(new Job(cur.L,equals[0] -1));
            }
            if(equals[1] < cur.R){
                stack.push(new Job(equals[1] + 1, cur.R));
            }
        }
    }

}
