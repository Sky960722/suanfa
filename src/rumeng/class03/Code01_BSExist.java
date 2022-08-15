package rumeng.class03;

import java.util.Arrays;

public class Code01_BSExist {

    public static int mostLeftNoLessNumIndex(int[] arr, int num) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        int L = 0;
        int R = arr.length - 1;
        int ans = -1;
        while (L <= R) {
            int mid = (L + R) / 2;
            if (arr[mid] >= num) {
                ans = mid;
                R = mid - 1;
            } else {
                L = mid + 1;
            }
        }
        return ans;
    }

    //arr保证有序
    public static boolean find(int[] arr, int num) {
        if (arr == null || arr.length == 0) {
            return false;
        }
        int L = 0;
        int R = arr.length - 1;
        while (L <= R) {
            int mid = (L + R) / 2;
            if (arr[mid] == num) {
                return true;
            } else if (arr[mid] < num) {
                L = mid + 1;
            } else {
                R = mid - 1;
            }
        }
        return false;
    }

    public static int test(int[] arr, int value) {
        for(int i = 0;i< arr.length ;i++){
            if(arr[i] >= value){
                return i;
            }
        }
        return -1;
    }


//    public static boolean test(int[] sortedArr, int num) {
//        for (int cur : sortedArr) {
//            if (cur == num) {
//                return true;
//            }
//        }
//        return false;
//    }

    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random() - (int) (maxValue * Math.random()));

        }
        return arr;
    }

    public static void main(String[] args) {
        int testTime = 500000;
        int maxSize = 10;
        int maxVaule = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxVaule);
            Arrays.sort(arr);
            int value = (int) ((maxVaule + 1) * Math.random()) - (int) (maxVaule * Math.random());
            if (test(arr, value) != mostLeftNoLessNumIndex(arr, value)) {
                System.out.println("出错了！");
                succeed = false;
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked");
    }


}
