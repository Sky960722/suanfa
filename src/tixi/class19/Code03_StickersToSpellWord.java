package tixi.class19;

// 本题测试链接：https://leetcode.com/problems/stickers-to-spell-word

import java.util.HashMap;

//691. 贴纸拼词
//        我们有 n 种不同的贴纸。每个贴纸上都有一个小写的英文单词。
//
//        您想要拼写出给定的字符串 target ，方法是从收集的贴纸中切割单个字母并重新排列它们。如果你愿意，你可以多次使用每个贴纸，每个贴纸的数量是无限的。
//
//        返回你需要拼出 target 的最小贴纸数量。如果任务不可能，则返回 -1 。
//
//        注意：在所有的测试用例中，所有的单词都是从 1000 个最常见的美国英语单词中随机选择的，并且 target 被选择为两个随机单词的连接。
//
//
//
//        示例 1：
//
//        输入： stickers = ["with","example","science"], target = "thehat"
//        输出：3
//        解释：
//        我们可以使用 2 个 "with" 贴纸，和 1 个 "example" 贴纸。
//        把贴纸上的字母剪下来并重新排列后，就可以形成目标 “thehat“ 了。
//        此外，这是形成目标字符串所需的最小贴纸数量。
//        示例 2:
//
//        输入：stickers = ["notice","possible"], target = "basicbasic"
//        输出：-1
//        解释：我们不能通过剪切给定贴纸的字母来形成目标“basicbasic”。
public class Code03_StickersToSpellWord {

    public static int minStickers1(String[] stickers, String target) {
        if (stickers == null || stickers.length == 0 || target == null || target.length() == 0) {
            return 0;
        }

        int[][] stickersMap = new int[stickers.length][26];
        for (int i = 0; i < stickers.length; i++) {
            String sticker = stickers[i];
            char[] chars = sticker.toCharArray();
            for (int j = 0; j < chars.length; j++) {
                stickersMap[i][chars[j] - 'a']++;
            }
        }
        int ans = process1(stickersMap, target);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    //思路：
    //1.每种贴纸来一张，直到当前贴纸无法减去target目标，target == 0，表明没有贴纸需要用了，返回0
    //2.只减含有第一个字母的贴纸
    public static int process1(int[][] stickers, String target) {
        if (target.length() == 0) {
            return 0;
        }
        char[] chars = target.toCharArray();
        int[] targetMap = new int[26];
        for (char ch : chars) {
            targetMap[ch - 'a']++;
        }
        //min 设立成MAX_VALUE,如果当前字符串没有符合要求的贴纸，则说明当前任务不可能完成
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < stickers.length; i++) {
            //说明当前贴纸包含第一个字母
            if (stickers[i][chars[0] - 'a'] > 0) {
                StringBuilder builder = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    if (targetMap[j] > 0) {
                        int nums = targetMap[j] - stickers[i][j];
                        for (int k = 0; k < nums; k++) {
                            builder.append((char) (j + 'a'));
                        }
                    }
                }
                String rest = builder.toString();
                min = Math.min(min, process1(stickers, rest));
            }
        }
        //min 如果是MAX_VALUE，说明当前贴纸没有被减去，直接返回MAX_VALUE
        int ans = min + (min == Integer.MAX_VALUE ? 0 : 1);
        return ans;
    }

    //增加dp
    public static int minStickers2(String[] stickers, String target) {
        int[][] stickersMap = new int[stickers.length][26];
        for(int i = 0;i<stickers.length;i++){
            for (char ch : stickers[i].toCharArray()) {
                stickersMap[i][ch-'a']++;
            }
        }
        HashMap<String,Integer> dp = new HashMap<>();
        int ans = process2(stickersMap,target,dp);
        return  ans == Integer.MAX_VALUE ? -1:0;
    }

    public static int process2(int[][] stickers, String target, HashMap<String, Integer> dp) {
        if (target.length() == 0) {
            return 0;
        }
        if (dp.containsKey(target)) {
            return dp.get(target);
        }
        char[] chars = target.toCharArray();
        int[] tcount = new int[26];
        for (char ch : chars) {
            tcount[ch - 'a']++;
        }
        int N = stickers.length;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < N; i++) {
            int[] sticker = stickers[i];
            if (sticker[chars[0] - 'a'] > 0) {
                StringBuilder builder = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    int nums = tcount[j] - sticker[j];
                    for (int k = 0; k < nums; k++) {
                        builder.append((char) j + 'a');
                    }
                }
                String rest = builder.toString();
                min = Math.min(min, process2(stickers, rest, dp));
            }
        }
        int ans = min + (min == Integer.MAX_VALUE ? 0 : 1);
        dp.put(target,ans);
        return ans;
    }
}
