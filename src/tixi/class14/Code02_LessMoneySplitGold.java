package tixi.class14;

import java.util.PriorityQueue;

//分割金条
//一块金条切成两半，是需要花费和长度数值一样的铜板
//比如长度为20的金条，不管怎么切都要花费20个铜板，一群人想整分整块金条，怎么分最省铜板?
//例如，给定数组{10,20,30}，代表一共三个人，整块金条长度为60，金条要分成10，20，30三个部分。
//如果先把长度60的金条分成10和50，花费60；再把长度50的金条分成20和30，花费50；一共花费110铜板
//但如果先把长度60的金条分成30和30，花费60；再把长度30金条分成10和20，花费30；一共花费90铜板
//输入一个数组，返回分割的最小代价
public class Code02_LessMoneySplitGold {

    //贪心算法：
    //按最小的长度依次往上去和，可以弄一个priority,里面存放数组元素
    //将第一个和第二个弹出来的元素合在一起，同时计算max，然后放入队列中，接着弹出下两个元素，直到没有元素可以弹出
    public static int lessMoney1(int[] arr) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i : arr) {
            pq.add(i);
        }
        int ans = 0;
        int p1 = 0;
        int p2 = 0;
        while (pq.size() > 1) {
            p1 = pq.poll();
            p2 = pq.poll();
            ans += p1 + p2;
            pq.add(p1 + p2);
        }
        return ans;
    }


    //暴力递归
    //每个元素加一遍
    public static int lessMoney2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        return process(arr,0);
    }

    public static int process(int[] arr, int pre) {
        if (arr.length == 1) {
            return pre;
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                int[] newArr = copyAndMergeTwo(arr, i, j);
                ans = Math.min(ans, process(newArr, pre + arr[i] + arr[j]));
            }
        }
        return ans;
    }

    public static int[] copyAndMergeTwo(int[] arr, int i, int j) {
        int[] ans = new int[arr.length - 1];
        int ansi = 0;
        for (int arri = 0; arri < arr.length; arri++) {
            if (arri != i && arri != j) {
                ans[ansi++] = arr[arri];
            }
        }
        ans[ansi] = arr[i] + arr[j];
        return ans;
    }

    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    public static void main(String[] args) {
        int testTime = 100000;
        int maxSize = 6;
        int maxValue = 1000;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            if (lessMoney1(arr) != lessMoney2(arr)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
