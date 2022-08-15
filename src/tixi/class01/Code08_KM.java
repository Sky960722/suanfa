package tixi.class01;

import java.util.HashMap;

public class Code08_KM {

    //请保证arr中，只有一种数出现了K次，其他数都出现了M次
    public static int onlyKTimes(int[] arr, int k, int m) {
        int[] t = new int[32];

        //t[0] 0位置的1出现了几个
        //t[i] i位置的1出现了几个
        for (int num : arr) {
            for (int i = 0; i <= 31; i++) {
//                if (((num >> i) & 1) != 0) {
//                    t[i]++;
//                }
                t[i] += (num >> i) & 1;
            }
        }
        int ans = 0;
        for (int i = 0; i < 32; i++) {
            if (t[i] % m != 0) { //在第i位上，有1
                ans |= (1 << i);
            }
        }
        return ans;
    }

    public static int test(int[] arr, int k, int m) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : arr) {
            if (map.containsKey(num)) {
                map.put(num, map.get(num) + 1);
            } else {
                map.put(num, 1);
            }
        }

        for(int num:map.keySet()){
            if(map.get(num) == k){
                return num;
            }
        }
        return -1;
    }


}
