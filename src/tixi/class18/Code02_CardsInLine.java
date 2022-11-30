package tixi.class18;

public class Code02_CardsInLine {

    //给定一个整形数组arr，代表数值不同的纸牌排成一条线
    //玩家A和玩家B依次拿走每张纸牌
    //规定玩家A先拿，玩家B后拿
    //但是每个玩家每次只能拿走最左或最右的纸牌
    //玩家A和玩家B都绝顶聪明
    //请返回最后获胜者的分数

    // 根据规则，返回获胜者的分数

    //思路：
    //零和博弈游戏，赢得一方拿的牌越大，则失败的一方相差的越多
    //1.先手拿牌，最终拿的一定是最大的结果
    //2.后手拿牌，只能根据先手拿牌后最大的结果，挑最小的情况去拿牌
    public static int win1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int first = f(arr, 0, arr.length - 1);
        int second = g(arr, 0, arr.length - 1);
        return Math.max(first, second);
    }


    public static int f(int[] arr, int L, int R) {
        if (L == R) {
            return arr[L];
        }
        int p1 = arr[L] + g(arr, L + 1, R);
        int p2 = arr[R] + g(arr, L, R - 1);
        return Math.max(p1, p2);
    }

    //后手的情况下，相当于先手拿了L+1，R或者L，R-1范围上的牌
    //先手挑选了最好的情况，因此，后手只能获得最小值
    public static int g(int[] arr, int L, int R) {
        if (L == R) {
            return 0;
        }
        int p1 = f(arr, L + 1, R);
        int p2 = f(arr, L, R - 1);
        return Math.min(p1, p2);
    }


    //记忆化搜索
    public static int win2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        int[][] fmap = new int[N][N];
        int[][] gmap = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                fmap[i][j] = -1;
                gmap[i][j] = -1;
            }
        }
        int first = f2(arr, 0, arr.length - 1, fmap, gmap);
        int second = g2(arr, 0, arr.length - 1, fmap, gmap);
        return Math.max(first, second);
    }

    public static int f2(int[] arr, int L, int R, int[][] fmap, int[][] gmap) {
        if (fmap[L][R] != -1) {
            return fmap[L][R];
        }
        int ans = 0;
        if (L == R) {
            ans = arr[L];
        } else {
            int p1 = arr[L] + g2(arr, L + 1, R, fmap, gmap);
            int p2 = arr[R] + g2(arr, L, R - 1, fmap, gmap);
            ans = Math.max(p1, p2);
        }
        return ans;
    }

    //后手的情况下，相当于先手拿了L+1，R或者L，R-1范围上的牌
    //先手挑选了最好的情况，因此，后手只能获得最小值
    public static int g2(int[] arr, int L, int R, int[][] fmap, int[][] gmap) {
        if (gmap[L][R] != -1) {
            return gmap[L][R];
        }
        int ans = 0;
        if (L != R) {
            int p1 = f2(arr, L + 1, R, fmap, gmap); // 对手拿走了L位置的数
            int p2 = f2(arr, L, R - 1, fmap, gmap); // 对手拿走了R位置的数
            ans = Math.min(p1, p2);
        }
        gmap[L][R] = ans;
        return ans;
    }

    //dp方程
    //设定两个dp[][]，fmap和gmap
    //fmap[i][i]和gmap[i][i]可以确定
    public static int win3(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        int[][] fmap = new int[N][N];
        int[][] gmap = new int[N][N];
        //填充fmap的对角线
        for (int i = 0; i < N; i++) {
            fmap[i][i] = arr[i];
        }
        //按照对角线依次填充
        //首先对列循环
        //然后对L和R分别++，始终呈对角线趋势，分别计算fmap和gmap
        for (int startCol = 1; startCol < N; startCol++) {
            int L = 0;
            int R = startCol;
            //根据R开始循环，R比L大，因此只需要对R做判断既可
            while (R < N) {
                fmap[L][R] = Math.max(arr[L] + gmap[L + 1][R], arr[R] + gmap[L][R - 1]);
                gmap[L][R] = Math.min(fmap[L + 1][R], fmap[L][R - 1]);
                L++;
                R++;
            }
        }
        return Math.max(fmap[0][N - 1], gmap[0][N - 1]);
    }

}
