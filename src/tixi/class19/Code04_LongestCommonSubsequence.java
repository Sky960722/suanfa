package tixi.class19;

//1143. 最长公共子序列
//        给定两个字符串 text1 和 text2，返回这两个字符串的最长 公共子序列 的长度。如果不存在 公共子序列 ，返回 0 。
//
//        一个字符串的 子序列 是指这样一个新的字符串：它是由原字符串在不改变字符的相对顺序的情况下删除某些字符（也可以不删除任何字符）后组成的新字符串。
//
//        例如，"ace" 是 "abcde" 的子序列，但 "aec" 不是 "abcde" 的子序列。
//        两个字符串的 公共子序列 是这两个字符串所共同拥有的子序列。
//
//
//
//        示例 1：
//
//        输入：text1 = "abcde", text2 = "ace"
//        输出：3
//        解释：最长公共子序列是 "ace" ，它的长度为 3 。
//        示例 2：
//
//        输入：text1 = "abc", text2 = "abc"
//        输出：3
//        解释：最长公共子序列是 "abc" ，它的长度为 3 。
//        示例 3：
//
//        输入：text1 = "abc", text2 = "def"
//        输出：0
//        解释：两个字符串没有公共子序列，返回 0 。
//
//
//        提示：
//
//        1 <= text1.length, text2.length <= 1000
//        text1 和 text2 仅由小写英文字符组成。

// 链接：https://leetcode.com/problems/longest-common-subsequence/
public class Code04_LongestCommonSubsequence {

    //样本对应模型

    public static int longestCommonSubsequence1(String text1, String text2) {
        if (text1 == null || text2 == null || text1.length() == 0 || text2.length() == 0) {
            return 0;
        }
        char[] ch1 = text1.toCharArray();
        char[] ch2 = text2.toCharArray();
        return process1(ch1, ch2, text1.length() - 1, text2.length() - 1);
    }

    //思路:
    //1.思考str1，0~i上与str2，0~j上最长公共子序列多长？
    //1.i为0，j为0，则直接返回他们的s1[0]和s2[0]的比较的情况
    //2.i为0，j不为0，则依次比较i与j的情况，直到两个字符相等，或者i == j == 0
    //3.i不为0，j为0，则类似i为0
    //4.i不为0，j不为0
    //
    public static int process1(char[] s1, char[] s2, int i, int j) {
        if (i == 0 && j == 0) {
            return s1[i] == s2[j] ? 1 : 0;
        } else if (i == 0) {
            if (s2[j] == s1[i]) {
                return 1;
            } else {
                return process1(s1, s2, i, j - 1);
            }
        } else if (j == 0) {
            if (s1[i] == s2[j]) {
                return 1;
            } else {
                return process1(s1, s2, i - 1, j);
            }
        } else {
            int p1 = process1(s1, s2, i - 1, j);
            int p2 = process1(s1, s2, i, j - 1);

            int p3 = s1[i] == s2[j] ? (1 + process1(s1, s2, i - 1, j - 1)) : 0;
            int ans = Math.max(p1, Math.max(p2, p3));
            return ans;
        }
    }

    public static int longestCommonSubsequence2(String text1, String text2) {
        if (text1 == null || text2 == null || text1.length() == 0 || text2.length() == 0) {
            return 0;
        }
        char[] ch1 = text1.toCharArray();
        char[] ch2 = text2.toCharArray();
        int[][] dp = new int[ch1.length][ch2.length];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                dp[i][j] = -1;
            }
        }
        return process2(ch1, ch2, text1.length() - 1, text2.length() - 1, dp);
    }

    public static int process2(char[] s1, char[] s2, int i, int j, int[][] dp) {
        if (dp[i][j] != -1) {
            return dp[i][j];
        }
        int ans;
        if (i == 0 && j == 0) {
            ans = s1[i] == s2[j] ? 1 : 0;
            dp[i][j] = ans;
            return ans;
        } else if (i == 0) {
            if (s2[j] == s1[i]) {
                dp[i][j] = 1;
                return 1;
            } else {
                ans = process2(s1, s2, i, j - 1, dp);
                dp[i][j] = ans;
                return ans;
            }
        } else if (j == 0) {
            if (s1[i] == s2[j]) {
                dp[i][j] = 1;
                return 1;
            } else {
                ans = process2(s1, s2, i - 1, j, dp);
                dp[i][j] = ans;
                return ans;
            }
        } else {
            int p1 = process2(s1, s2, i - 1, j, dp);
            int p2 = process2(s1, s2, i, j - 1, dp);
            int p3 = s1[i] == s2[j] ? (1 + process2(s1, s2, i - 1, j - 1, dp)) : 0;
            ans = Math.max(p1, Math.max(p2, p3));
            dp[i][j] = ans;
            return ans;
        }
    }

    //根据递归公式来看
    //可以确定第一列和第一行的值
    //i和j的值根据i，j-1，i-1,j,i-1,j-1三个位置的值做判断
    public static int longestCommonSubsequence3(String text1, String text2) {
        if (text1 == null || text2 == null || text1.length() == 0 || text2.length() == 0) {
            return 0;
        }
        char[] ch1 = text1.toCharArray();
        char[] ch2 = text2.toCharArray();
        int N = text1.length();
        int M = text2.length();
        int[][] dp = new int[N][M];
        dp[0][0] = ch1[0] == ch2[0] ? 1 : 0;
        for (int i = 1; i < M; i++) {
            dp[0][i] = ch1[0] == ch2[i] ? 1 : dp[0][i - 1];
        }
        for (int i = 1; i < N; i++) {
            dp[i][0] = ch1[i] == ch2[0] ? 1 : dp[i - 1][0];
        }
        for (int i = 1; i < N; i++) {
            for (int j = 1; j < M; j++) {
                int p1 = dp[i - 1][j];
                int p2 = dp[i][j - 1];
                int p3 = ch1[i] == ch2[j] ? 1 + dp[i - 1][j - 1] : 0;
                dp[i][j]  = Math.max(p1, Math.max(p2, p3));
            }
        }
        return dp[N - 1][M - 1];
    }

}
