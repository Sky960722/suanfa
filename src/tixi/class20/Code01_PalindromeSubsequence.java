package tixi.class20;

// 测试链接：https://leetcode.com/problems/longest-palindromic-subsequence/

//516. 最长回文子序列
//        给你一个字符串 s ，找出其中最长的回文子序列，并返回该序列的长度。
//
//        子序列定义为：不改变剩余字符顺序的情况下，删除某些字符或者不删除任何字符形成的一个序列。
//
//
//
//        示例 1：
//
//        输入：s = "bbbab"
//        输出：4
//        解释：一个可能的最长回文子序列为 "bbbb" 。
//        示例 2：
//
//        输入：s = "cbbd"
//        输出：2
//        解释：一个可能的最长回文子序列为 "bb" 。
//
//
//        提示：
//
//        1 <= s.length <= 1000
//        s 仅由小写英文字母组成
public class Code01_PalindromeSubsequence {

    //样本对应模型
    public static int longestPalindromeSubseq1(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] ch1 = s.toCharArray();
        char[] ch2 = reverse(s).toCharArray();
        int N = ch1.length;
        int[][] dp = new int[N][N];
        dp[0][0] = ch1[0] == ch2[0] ? 1 : 0;
        for (int i = 1; i < N; i++) {
            dp[0][i] = ch1[0] == ch2[i] ? 1 : dp[0][i - 1];
        }
        for (int i = 1; i < N; i++) {
            dp[i][0] = ch1[i] == ch2[0] ? 1 : dp[i - 1][0];
        }
        for (int i = 1; i < N; i++) {
            for (int j = 1; j < N; j++) {
                int p1 = dp[i][j - 1];
                int p2 = dp[i - 1][j];
                int p3 = ch1[i] == ch2[j] ? 1 + dp[i - 1][j - 1] : 0;
                dp[i][j] = Math.max(p1, Math.max(p2, p3));
            }
        }
        return dp[N - 1][N - 1];
    }

    public static String reverse(String s) {
        StringBuilder builder = new StringBuilder();
        char[] ch = s.toCharArray();
        int N = ch.length;
        for (int i = N - 1; i >= 0; i--) {
            builder.append(ch[i]);
        }
        return builder.toString();
    }


    //范围对应模型
    public static int longestPalindromeSubseq2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        return process2(s.toCharArray(), 0, s.length() - 1);
    }

    //L和R范围上的最长回文子序列
    //1.L == R ? 则必然是 1
    //2.L == R-1 ？ 则判断L 和 R是否相等，相等就是2，不然就是1(需要考虑这种情况，因为会存在L和R相等，然后相邻的情况)
    //3.L和R不相等，L和R各减1
    //4.L和R相等，同时减1
    public static int process2(char[] s, int L, int R) {
        if (L == R) {
            return 1;
        }
        if (L == R - 1) {
            return s[L] == s[R] ? 2 : 1;
        }
        int p1 = process2(s, L, R - 1);
        int p2 = process2(s, L + 1, R);
        int p3 = 0;
        if (s[L] == s[R]) {
            p3 = 2 + process2(s, L + 1, R - 1);
        }
        return Math.max(p1, Math.max(p2, p3));
    }

    public static int longestPalindromeSubseq3(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int N = s.length();
        int[][] dp = new int[N][N];
        return process3(s.toCharArray(), 0, s.length() - 1, dp);
    }

    public static int process3(char[] s, int L, int R, int[][] dp) {
        if (L == R) {
            return 1;
        }
        if (L == R - 1) {
            return s[L] == s[R] ? 2 : 1;
        }
        if (dp[L][R] != 0) {
            return dp[L][R];
        }
        int p1 = process3(s, L, R - 1, dp);
        int p2 = process3(s, L + 1, R, dp);
        int p3 = 0;
        if (s[L] == s[R]) {
            p3 = 2 + process3(s, L + 1, R - 1, dp);
        }
        dp[L][R] = Math.max(p1, Math.max(p2, p3));
        return dp[L][R];
    }

    //根据递归方程可以推的对角线都是1，L==R-1可以求出来
    //L，R依赖L+1，R-1，L，R-1，L+1，R三种情况
    public static int longestPalindromeSubseq4(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int N = s.length();
        int[][] dp = new int[N][N];
        char[] chArr = s.toCharArray();
        for (int i = 0; i < N; i++) {
            dp[i][i] = 1;
        }
        for (int i = 0; i < N - 1; i++) {
            dp[i][i + 1] = chArr[i] == chArr[i + 1] ? 2 : 1;
        }
        for (int i = N - 3; i >= 0; i--) {
            for (int j = i + 2; j < N; j++) {
                int p1 = dp[i][j - 1];
                int p2 = dp[i + 1][j];
                int p3 = 0;
                if (chArr[i] == chArr[j]) {
                    p3 = 2 + dp[i + 1][j - 1];
                }
                dp[i][j] = Math.max(p1, Math.max(p2, p3));
            }
        }
        return dp[0][N - 1];
    }

    public static int longestPalindromeSubseq5(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int N = s.length();
        int[][] dp = new int[N][N];
        char[] chArr = s.toCharArray();
        dp[N-1][N-1] = 1;
        for (int i = 0; i < N - 1; i++) {
            dp[i][i] = 1;
            dp[i][i + 1] = chArr[i] == chArr[i + 1] ? 2 : 1;
        }
        for (int i = N - 3; i >= 0; i--) {
            for (int j = i + 2; j < N; j++) {
                int p1 = dp[i][j - 1];
                int p2 = dp[i + 1][j];
                dp[i][j] = Math.max(p1,p2);
                if (chArr[i] == chArr[j]) {
                    int p3 = 2 + dp[i + 1][j - 1];
                    dp[i][j] = Math.max(dp[i][j],p3);
                }
            }
        }
        return dp[0][N - 1];
    }

    public static void main(String[] args) {
        String test = "aaa";
        System.out.println(longestPalindromeSubseq4(test));
    }
}
