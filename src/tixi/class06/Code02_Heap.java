package tixi.class06;

import com.sun.org.apache.bcel.internal.generic.SWAP;

import java.util.PriorityQueue;

public class Code02_Heap {
    public static class MyMaxHeap {
        private int[] heap;
        private final int limit;
        private int heapSize;

        public MyMaxHeap(int limit) {
            heap = new int[limit];
            this.limit = limit;
            heapSize = 0;
        }

        public boolean isEmpty() {
            return heapSize == 0;
        }

        public boolean isFull() {
            return heapSize == limit;
        }

        private void swap(int[] arr, int i, int j) {
            int tmp = arr[i];
            arr[i] = arr[j];
            arr[j] = tmp;
        }

        private void heapInsert(int[] arr, int index) {
            while (arr[index] > arr[(index - 1) / 2]) {
                swap(arr, index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        public void push(int value) {
            if (heapSize == limit) {
                throw new RuntimeException("heap is full");
            }
            heap[heapSize] = value;
            heapInsert(heap, heapSize++);
        }

        private void heapify(int[] arr, int index, int heapSize) {
            int left = index * 2 + 1;
            while (left < heapSize) {
                int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
                largest = arr[largest]>arr[index] ?largest:index;
                if(largest == index){
                    break;
                }
                swap(arr,largest,index);
                index=largest;
                left=index*2+1;
            }
        }

        public int pop(){
            if(this.heapSize == 0){
                throw new RuntimeException("No element");
            }
            int ans = heap[0];
            swap(heap,0,--heapSize);
            heapify(heap,0,heapSize);
            return ans;
        }


    }

    public static void main(String[] args) {
        MyMaxHeap heap = new MyMaxHeap(10);
        heap.push(1);
        heap.push(2);
        heap.push(4);
        heap.push(5);
        int ans =heap.pop();
        System.out.println(ans);
    }


}
