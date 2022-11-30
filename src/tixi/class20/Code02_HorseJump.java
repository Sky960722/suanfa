package tixi.class20;

import com.sun.org.apache.bcel.internal.generic.RET;

//题目二
//请同学们自行搜做或者想象一个象棋的棋盘，然后把整个棋盘放入第一象限，棋盘的最左下角是(0,0)位置,那么整个棋盘就是横坐标上9条线，纵坐标上10条线的区域，给你三个参数x,y,k,返回"马"从(0,0)
// 位置出发，必须走k步，最后落在(x,y)上的方法数有多少种？
public class Code02_HorseJump {

    public static int jump(int a, int b, int k) {
        if (a < 0 || a > 9 || b < 0 || b > 8 || k < 0) {
            return 0;
        }
        return process(a, b, 0, 0, k);
    }

    //1.rest为0,a=x，b=y，满足等于
    //2.剩下rest不为0的情况,则是这个马往8个方向跳的格子
    private static int process(int a, int b, int x, int y, int rest) {
        if (x < 0 || x > 9 || y < 0 || y > 8) {
            return 0;
        }
        if (rest == 0) {
            return a == x && b == y ? 1 : 0;
        }
        int p1 = process(a, b, x + 2, y + 1, rest - 1);
        int p2 = process(a, b, x + 1, y + 2, rest - 1);
        int p3 = process(a, b, x + 2, y - 1, rest - 1);
        int p4 = process(a, b, x + 1, y - 2, rest - 1);
        int p5 = process(a, b, x - 2, y + 1, rest - 1);
        int p6 = process(a, b, x - 1, y + 2, rest - 1);
        int p7 = process(a, b, x - 2, y - 1, rest - 1);
        int p8 = process(a, b, x - 1, y - 2, rest - 1);
        int ways = p1 + p2 + p3 + p4 + p5 + p6 + p7 + p8;
        return ways;
    }

    //dp根据递归方程可以推出三个参数，x，y，rest
    //1.根据x,y，rest可以推出来，abrest=1，其余为0
    //2.根据动态可以推出来8种情况
    public static int jump1(int a, int b, int k) {
        if (a < 0 || a > 9 || b < 0 || b > 8 || k < 0) {
            return 0;
        }
        int[][][] dp = new int[10][9][k + 1];
        dp[a][b][0] = 1;
        for (int rest = 1; rest < k + 1; rest++) {
            for (int x = 0; x < 10; x++) {
                for (int y = 0; y < 9; y++) {
                    int ways = pick(dp, x + 2, y + 1, rest - 1);
                    ways += pick(dp, x + 1, y + 2, rest - 1);
                    ways += pick(dp, x - 1, y + 2, rest - 1);
                    ways += pick(dp, x - 2, y + 1, rest - 1);
                    ways += pick(dp, x - 2, y - 1, rest - 1);
                    ways += pick(dp, x - 1, y - 2, rest - 1);
                    ways += pick(dp, x + 1, y - 2, rest - 1);
                    ways += pick(dp, x + 2, y - 1, rest - 1);
                    dp[x][y][rest] = ways;
                }
            }
        }
        return dp[0][0][k];
    }

    public static int pick(int[][][] dp, int x, int y, int rest) {
        if (x < 0 || x > 9 || y < 0 || y > 8) {
            return 0;
        }
        return dp[x][y][rest];
    }
}
