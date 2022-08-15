package tixi.class06;

import java.util.PriorityQueue;

public class Code04_SortArrayDistanceLessK {

    public static void sortedArrDistanceLessK(int[] arr, int k) {
        if (k == 0) {
            return;
        }
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        int index = 0;
        for (; index <= Math.min(arr.length - 1, k - 1); index++) {
            queue.add(arr[index]);
        }
        int i = 0;
        for (; index < arr.length; i++, index++) {
            queue.add(arr[index]);
            arr[i] = queue.poll();
        }
        while (!queue.isEmpty()) {
            arr[i++] = queue.poll();
        }
    }
}
