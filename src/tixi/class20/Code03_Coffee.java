package tixi.class20;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

// 题目
// 数组arr代表每一个咖啡机冲一杯咖啡的时间，每个咖啡机只能串行的制造咖啡。
// 现在有n个人需要喝咖啡，只能用咖啡机来制造咖啡。
// 认为每个人喝咖啡的时间非常短，冲好的时间即是喝完的时间。
// 每个人喝完之后咖啡杯可以选择洗或者自然挥发干净，只有一台洗咖啡杯的机器，只能串行的洗咖啡杯。
// 洗杯子的机器洗完一个杯子时间为a，任何一个杯子自然挥发干净的时间为b。
// 四个参数：arr, n, a, b
// 假设时间点从0开始，返回所有人喝完咖啡并洗完咖啡杯的全部过程结束后，至少来到什么时间点。
public class Code03_Coffee {

    // 验证的方法
    // 彻底的暴力
    // 很慢但是绝对正确
    public static int right(int[] arr, int n, int a, int b) {
        int[] times = new int[arr.length];
        int[] drink = new int[n];
        return forceMake(arr, times, 0, drink, n, a, b);
    }

    //暴力
    //首先每个人尝试给自己做一杯咖啡，求n个人每人喝完咖啡的时间数组
    //1.先用暴力求出这个时间数组，设定6个参数，
    // arr表示咖啡机使用时间
    // times表示咖啡机能使用的时间，初始都是0
    // index表示第几个人喝咖啡
    // drinks表示每个人喝完咖啡的时间
    // n表示人数，用于for循环
    // a表示咖啡机洗咖啡时间
    // b表示所有咖啡挥发时间
    //2.先用for循环和index尝试每个顾客使用不同咖啡机的情况，没收集到一种情况，就把这个情况传给forcewash
    //3.forcewash根据drinks暴力求解最短时间，最后比较最小时间求得对应情况
    public static int forceMake(int[] arr, int[] times, int index, int[] drinks, int n, int a, int b) {
        if (index == n) {
            int[] drinkSorted = Arrays.copyOf(drinks, n);
            Arrays.sort(drinkSorted);
            //这个方法是将所有人喝完咖啡后，洗咖啡的最佳时间，通过暴力递归求得
            return forceWash(drinkSorted, a, b, 0, 0, 0);
        }
        int time = Integer.MAX_VALUE;
        //对arr.length做for循环，每位顾客尝试喝咖啡的情况，用for循环代替
        for (int i = 0; i < arr.length; i++) {
            int pre = times[i];
            int work = arr[i];
            times[i] = pre + work;
            //当前顾客喝咖啡的时间
            drinks[index] = pre + work;
            time = Math.min(time, forceMake(arr, times, index + 1, drinks, n, a, b));
            times[i] = pre;
            drinks[index] = 0;
        }
        return time;
    }


    //暴力递归
    //1.洗咖啡的机子只有一台，通过washLine记录当前咖啡机的工作时间，time是当前咖啡洗干净的时间
    //2.一种是洗杯子，washLine和drinks谁大取谁，+a
    //3.一种是自然挥发，drinks直接+b，
    public static int forceWash(int[] drinks, int a, int b, int index, int washLine, int time) {
        if (index == drinks.length) {
            return time;
        }
        // 选择一：当前index号咖啡杯，选择用洗咖啡机刷干净
        int wash = Math.max(drinks[index], washLine) + a;
        int ans1 = forceWash(drinks, a, b, index + 1, washLine, Math.max(wash, time));

        // 选择二：当前index号咖啡杯，选择自然挥发
        int dry = drinks[index] + b;
        int ans2 = forceWash(drinks, a, b, index + 1, washLine, Math.max(dry, time));
        return Math.min(ans1, ans2);

    }



    // for test
    public static int[] randomArray(int len, int max) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * max) + 1;
        }
        return arr;
    }



    // for test
    public static void printArray(int[] arr) {
        System.out.print("arr : ");
        for (int j = 0; j < arr.length; j++) {
            System.out.print(arr[j] + ", ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int len = 10;
        int max = 10;
        int testTime = 10;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(len, max);
            int n = (int) (Math.random() * 7) + 1;
            int a = (int) (Math.random() * 7) + 1;
            int b = (int) (Math.random() * 10) + 1;
            int ans1 = right(arr, n, a, b);
        }
        System.out.println("测试结束");
    }
}
