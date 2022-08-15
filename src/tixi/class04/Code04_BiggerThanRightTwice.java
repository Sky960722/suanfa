package tixi.class04;

//翻转对
//给定一个数组 nums ，如果 i < j 且 nums[i] > 2*nums[j] 我们就将 (i, j) 称作一个重要翻转对。
//
//你需要返回给定数组中的重要翻转对的数量。
public class Code04_BiggerThanRightTwice {

    public static int reversePairs(int[] arrs) {
        if (arrs == null && arrs.length < 2) {
            return 0;
        }
        return process(arrs, 0, arrs.length - 1);
    }

    public static int process(int[] arrs, int l, int r) {
        if (l == r) {
            return 0;
        }
        int mid = l + ((r - l)) >> 1;
        return process(arrs, l, mid) + process(arrs, mid + 1, r) + merge(arrs, l, mid, r);
    }

    public static int merge(int[] arrs, int l, int m, int r) {
        int ans = 0;
        int windowR = m + 1;
        int p1 = l;
        int p2 = m + 1;
        while (p1 <= m) {
            while (windowR <= r && (long) arrs[p1] > (long) arrs[windowR] * 2) {
                windowR++;
            }
            ans += windowR - m - 1;
            p1++;
        }
        int[] helpArr = new int[r - l + 1];
        int i = 0;
        p1 = l;
        p2 = m + 1;
        while (p1 <= m && p2 <= r) {
            helpArr[i++] = arrs[p1] <= arrs[p2] ? arrs[p1++] : arrs[p2++];
        }
        while (p1 <= m){
            helpArr[i++] = arrs[p1++];
        }
        while (p2 <= r){
            helpArr[i++] = arrs[p2++];
        }
        for(i = 0;i < helpArr.length;i++){
            arrs[l + i] = helpArr[i];
        }
        return ans;
    }
}
