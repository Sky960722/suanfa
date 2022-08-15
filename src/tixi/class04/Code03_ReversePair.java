package tixi.class04;

//逆序对的定义
//设有一个序列{a_1, a_2, a_3,...a_{n-1}, a_n},对于序列中任意两个元素ai,aj，若i<j,ai>aj，则说明ai和aj是一对逆序对。
public class Code03_ReversePair {

    public static int reversePairNumber(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        return process(arr, 0, arr.length - 1);
    }

    public static int process(int[] arr, int l, int r) {
        if (l == r) {
            return 0;
        }
        int mid = l + ((r - l) >> 1);
        return process(arr, l, mid) + process(arr, mid + 1, r) + merge(arr, l, mid, r);
    }

    public static int merge(int[] arr, int l, int m, int r) {
        int help[] = new int[r - l + 1];
        int p1 = m;
        int p2 = r;
        int i = help.length - 1;
        int res = 0;
        while (p1 >= l && p2 >= m + 1) {
            res += arr[p1] > arr[p2] ? (p2 - m) : 0;
            help[i--] = arr[p1] > arr[p2] ? arr[p1--] : arr[p2--];
        }
        while (p1 >= l){
            help[i--] = arr[p1--];
        }
        while (p2 >= m+1){
            help[i--] = arr[p2--];
        }
        for(i = 0;i<help.length;i++){
            arr[l + i] = help[i];
        }
        return res;
    }

}
