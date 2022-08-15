package tixi.class05;

//区间和的个数
//给你一个整数数组 nums 以及两个整数 lower 和 upper 。求数组中，值位于范围 [lower, upper] （包含 lower 和 upper）之内的 区间和的个数 。
//
//区间和 S(i, j) 表示在 nums 中，位置从 i 到 j 的元素之和，包含 i 和 j (i ≤ j)
public class Code01_CountOfRangeSum {

    public static int countRangeSum(int[] nums, int lower, int upper) {
        if (nums == null && nums.length == 0) {
            return 0;
        }
        long[] preSum = new long[nums.length];
        preSum[0] = nums[0];
        for (int i = 0; i < preSum.length; i++) {
            preSum[i] = preSum[i - 1] + nums[i];
        }

        return process(preSum,0,preSum.length-1,lower,upper);
    }

    public static int process(long[] preSum, int l, int r, int lower, int upper) {
        if (l == r) {
            return preSum[l] >= lower && preSum[l] <= upper ? 1 : 0;
        }
        int mid = l + ((r - l) >> 1);
        return process(preSum, l, mid, lower, upper) + process(preSum, mid + 1, r, lower, upper) + merger(preSum, l,
                mid, r, lower, upper);

    }

    public static int merger(long[] preSum, int l, int m, int r, int lower, int upper) {
        int windowL = l;
        int windowR = l;
        int ans = 0;
        for (int i = m + 1; i <= r; i++) {
            long min = preSum[i] - upper;
            long max = preSum[i] - lower;
            while (windowR <= m && windowR <= max) {
                windowR++;
            }
            while (windowL <= m && windowL < min) {
                windowL++;
            }
            ans += windowR - windowL;
        }
        long[] helpArr = new long[r - l + 1];
        int p1 = l;
        int p2 = m+1;
        int i =0;
        while (p1 <= m && p2 <=r){
            helpArr[i++] = preSum[p1] <= preSum[p2]? preSum[p1++] : preSum[p2++];
        }
        while (p1 <= m){
            helpArr[i++] = preSum[p1++];
        }
        while (p2<= r){
            helpArr[i++] = preSum[p2++];
        }
        for(i = 0;i <helpArr.length;i++){
            preSum[l+i] = helpArr[i];
        }
        return ans;
    }
}
