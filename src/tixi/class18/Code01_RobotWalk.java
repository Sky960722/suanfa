package tixi.class18;

public class Code01_RobotWalk {
    //机器人步行问题

    //    假设有排成一行的N个位置记为1~N，N一定大于或等于2
//    开始时机器人在其中的M位置上(M一定是1~N中的一个)
//
//    如果机器人来到1位置，那么下一步只能往右来到2位置；
//    如果机器人来到N位置，那么下一步只能往左来到N-1位置；
//    如果机器人来到中间位置，那么下一步可以往左走或者往右走；
//    规定机器人必须走K步，最终能来到P位置(P也是1~N中的一个)的方法有多少种
//    给定四个参数 N、M、K、P，返回方法数量
    public static int ways1(int N, int start, int aim, int K) {
        if (N < 2 || start < 1 || aim < 1 || aim > N || K < 1) {
            return 0;
        }
        return process1(N, start, aim, K);
    }

    //思路：
    //1.该机器人有两种走法，一种往左，一种往右，除非在两端，那么只要满足最后rest=0的时候，rest=aim的即是一种解法
    public static int process1(int N, int cur, int aim, int rest) {
        if (rest == 0) {
            return cur == aim ? 1 : 0;
        }
        if (cur == 1) {
            return process1(N, cur + 1, aim, rest - 1);
        }
        if (cur == N) {
            return process1(N, cur - 1, aim, rest - 1);
        }
        return process1(N, cur - 1, aim, rest - 1) + process1(N, cur + 1, aim, rest - 1);
    }


    //dp解法
    //根据递归方程，可以看出cur和rest是dp数组，其中cur=aim，rest = 0，是1，其余rest=0的则是0
    //cur==1，和cur==N可以确定，其余可以通过dp[cur-1],dp[cur+1]确定
    //最后求的是dp[start][rest]的值
    public static int ways2(int N, int start, int aim, int K) {
        if (N < 2 || start < 1 || aim < 1 || aim > N || K < 1) {
            return 0;
        }
        int[][] dp = new int[N + 1][K+1];
        for (int i = 1; i < N + 1; i++) {
            if(i == aim){
                dp[i][0] = 1;
            } else {
                dp[i][0] = 0;
            }
        }

        for (int i = 1; i < K + 1; i++) {
            dp[1][i] = dp[2][i-1];
            dp[N][i] = dp[N-1][i-1];
            for(int j = 2;j<N;j++){
                dp[j][i] = dp[j-1][i-1]+dp[j+1][i-1];
            }
        }

        return dp[start][K];
    }

    public static int ways3(int N, int start, int aim, int K) {
        if (N < 2 || start < 1 || start > N || aim < 1 || aim > N || K < 1) {
            return -1;
        }
        int[][] dp = new int[N + 1][K + 1];
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= K; j++) {
                dp[i][j] = -1;
            }
        }
        // dp就是缓存表
        // dp[cur][rest] == -1 -> process1(cur, rest)之前没算过！
        // dp[cur][rest] != -1 -> process1(cur, rest)之前算过！返回值，dp[cur][rest]
        // N+1 * K+1
        return process3(start, K, aim, N, dp);
    }

    // cur 范: 1 ~ N
    // rest 范：0 ~ K
    public static int process3(int cur, int rest, int aim, int N, int[][] dp) {
        if (dp[cur][rest] != -1) {
            return dp[cur][rest];
        }
        // 之前没算过！
        int ans = 0;
        if (rest == 0) {
            ans = cur == aim ? 1 : 0;
        } else if (cur == 1) {
            ans = process3(2, rest - 1, aim, N, dp);
        } else if (cur == N) {
            ans = process3(N - 1, rest - 1, aim, N, dp);
        } else {
            ans = process3(cur - 1, rest - 1, aim, N, dp) + process3(cur + 1, rest - 1, aim, N, dp);
        }
        dp[cur][rest] = ans;
        return ans;

    }


    public static void main(String[] args) {
        System.out.println(ways1(5, 2, 4, 6));
        System.out.println(ways2(5, 2, 4, 6));
        System.out.println(ways3(5, 2, 4, 6));
    }
}
