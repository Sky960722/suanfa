package tixi.class05;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Code03_QuickSortRecursiveAndUnrecursive {

    public static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[j] = arr[i];
        arr[i] = tmp;
    }

    public static int[] netherlandsFlag(int[] arr, int l, int r) {
        if (l > r) {
            return new int[]{-1, -1};
        }
        if (l == r) {
            return new int[]{l, r};
        }
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

    public static class Op {
        public int l;
        public int r;

        public Op(int l, int r) {
            this.l = l;
            this.r = r;
        }
    }

    public static void quickSortByStack(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int arrLength = arr.length;
        swap(arr, (int) (Math.random() * arrLength), arrLength - 1);
        int[] equalArea = netherlandsFlag(arr, 0, arr.length - 1);
        int el = equalArea[0];
        int er = equalArea[1];
        Stack<Op> stack = new Stack<>();
        stack.push(new Op(0, el - 1));
        stack.push(new Op(er + 1, arrLength - 1));
        while (!stack.isEmpty()) {
            Op op = stack.pop();
            if (op.l < op.r) {
                swap(arr, op.l + (int) (+Math.random() * (op.r - op.l + 1)), op.r);
                equalArea = netherlandsFlag(arr, op.l, op.r);
                el = equalArea[0];
                er = equalArea[1];
                stack.push(new Op(0, el - 1));
                stack.push(new Op(er + 1, op.r));
            }
        }
    }

    public static void quickSortByQueue(int[] arr) {
        if (arr == null || arr.length < 2) {
            return;
        }
        int arrLength = arr.length;
        swap(arr, (int) (Math.random() * arrLength), arrLength - 1);
        int[] equalArea = netherlandsFlag(arr, 0, arr.length - 1);
        int el = equalArea[0];
        int er = equalArea[1];
        Queue<Op> queue = new LinkedList<>();
        queue.offer(new Op(0, el - 1));
        queue.offer(new Op(er + 1, arrLength - 1));
        while (!queue.isEmpty()) {
            Op op = queue.poll();
            if (op.l < op.r) {
                swap(arr, op.l + (int) (+Math.random() * (op.r - op.l + 1)), op.r);
                equalArea = netherlandsFlag(arr, op.l, op.r);
                el = equalArea[0];
                er = equalArea[1];
                queue.offer(new Op(0, el - 1));
                queue.offer(new Op(er + 1, op.r));
            }
        }
    }
}
