package tixi.class07;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class HeapGreater<T> {
    private ArrayList<T> heap;
    private HashMap<T, Integer> indexMap;
    private int heapSize;
    private Comparator<? super T> comp;

    public HeapGreater(Comparator<? super T> c) {
        heap = new ArrayList<>();
        indexMap = new HashMap<>();
        heapSize = 0;
        comp = c;
    }

    private void swap(int i, int j) {
        T o1 = heap.get(i);
        T o2 = heap.get(j);
        heap.set(i, o2);
        heap.set(j, o1);
        indexMap.put(o1, j);
        indexMap.put(o2, i);
    }

    private void heapInsert(int index) {
        while (comp.compare(heap.get(index), heap.get((index - 1) / 2)) < 0) {
            swap(index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    public void push(T obj) {
        heap.add(obj);
        indexMap.put(obj, heapSize);
        heapInsert(heapSize++);
    }

    private void heapify(int index) {
        int left = 2 * index + 1;
        while (left < this.heapSize) {
            int target = left + 1 < this.heapSize
                    && comp.compare(heap.get(left + 1), heap.get(left)) < 0 ? left + 1 : left;
            target = comp.compare(heap.get(target), heap.get(index)) < 0 ? target : index;
            if (index == target) {
                break;
            }
            swap(index, target);
            index = target;
            left = 2 * index + 1;
        }
    }

    public T pop(){
        T ans = heap.get(0);
        swap(0,heapSize-1);
        indexMap.remove(ans);
        heap.remove(--heapSize);
        heapify(0);
        return ans;
    }

    public void resign(T obj){
        heapInsert(indexMap.get(obj));
        heapify(indexMap.get(obj));
    }

    public void remove(T obj){

        /*
        int index = indexMap.get(obj);
        T rep = heap.get(heapSize-1);
        swap(index,heapSize-1);
        indexMap.remove(obj);
        heap.remove(--heapSize);
        resign(rep);
        */


        T replace = heap.get(heapSize - 1);
        int index = indexMap.get(obj);
        indexMap.remove(obj);
        heap.remove(--heapSize);
        if(obj!=replace){
            heap.set(index,replace);
            indexMap.put(replace,index);
            resign(replace);
        }


    }

    public boolean isEmpty() {
        return heapSize == 0;
    }

    public int size() {
        return heapSize;
    }



    public boolean contains(T obj) {
        return indexMap.containsKey(obj);
    }

    public T peek() {
        return heap.get(0);
    }
}
