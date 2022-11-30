package tixi.class19;

public class Code01_Knapsack {

    //尝试模型
    //从左到右依次尝试


    //背包问题
    //最基本的背包问题就是01背包问题（01 knapsack problem）：一共有N件物品，第i（i从1开始）件物品的重量为w[i]，价值为v[i]。在总重量不超过背包承载上限W的情况下，能够装入背包的最大价值是多少？


    public static int maxValue(int[] w, int[] v, int bag) {
        if (bag == 0 || w.length == 0) {
            return 0;
        }
        return process1(w, v, 0, bag);
    }

    //1.从左到右依次判断，货物加或是不加的情况
    //2.方法返回值是当前货物的最大价值
    //3.需要考虑当rest不满足往前货物w的情况
    public static int process1(int[] w, int[] v, int index, int rest) {
        if (index == w.length) {
            return 0;
        }

        int yes = rest - w[index] >= 0 ? v[index] + process1(w, v, index + 1, rest - w[index]) : Integer.MIN_VALUE;
        int no = process1(w, v, index + 1, rest);
        return Math.max(yes, no);
    }

    //通过递归，可以看出index和rest控制整个流程
    //当index == w.length-1,rest > w[index]，就能确定
    public static int dp(int[] w, int[] v, int bag) {
        if (bag == 0 || w.length == 0) {
            return 0;
        }
        int[][] dp = new int[w.length][bag + 1];
        for(int i = 0;i <= bag;i++){
            dp[w.length-1][i] = i - w[w.length-1] > 0 ? v[w.length-1] : 0;
        }

        for(int i = w.length - 2;i >=0;i --){
            for(int j = 0 ; j <= bag;j++){
                int no = dp[i+1][j];
                int yes =0;
                if(j - w[i] >= 0){
                     yes = v[i]+dp[i+1][j-w[i]];
                }
                dp[i][j] = Math.max(no,yes);
            }
        }
        return dp[0][bag];
    }

    public static void main(String[] args) {
        int[] weights = {3, 2, 4, 7, 3, 1, 7};
        int[] values = {5, 6, 3, 19, 12, 4, 2};
        int bag = 15;
        System.out.println(maxValue(weights, values, bag));
        System.out.println(dp(weights, values, bag));
    }
}
