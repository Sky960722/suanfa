package tixi.class13;

import javax.swing.text.SimpleAttributeSet;
import java.util.*;

//字典序拼接字符串
//题目描述:字符串数组，将各字符串拼接，使得最低词典顺序。如：“ab”,“cd”,“ef”,拼接成“abcdef”最小，其他方式都比它大。
public class Code05_LowestLexicography {

    //1.按照首字母大小排序，首字母相同，比第二个，直到完全比完x1-x2 < 0,x1-x2
    //b，ba不行，bba > bab
    //2.贪心算法，直接比较每两个字母互相拼接后的长度
    //假设b，ba
    //比较b+ba和ba+b之间的长度
    public static String lowestString1(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        Arrays.sort(strs, ((o1, o2) -> (o1 + o2).compareTo(o2 + o1)));
        StringBuffer str = new StringBuffer();
        for (String s : strs) {
            str.append(s);
        }
        return str.toString();
    }

    //暴力解法
    //声明一个priority队列，将所有结果放进去，然后弹出第一个
    public static String lowestString2(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        TreeSet<String> ans = process(strs);
        return ans.pollFirst();
    }

    public static TreeSet<String> process(String[] strs) {
        TreeSet<String> ans = new TreeSet<>();
        if (strs.length == 0) {
            ans.add("");
            return ans;
        }
        for (int i = 0; i < strs.length; i++) {
            String first = strs[i];
            String[] nexts = removeIndexString(strs, i);
            TreeSet<String> next = process(nexts);
            for (String cur : next) {
                ans.add(first + cur);
            }
        }
        return ans;
    }

    public static String[] removeIndexString(String[] arr, int index) {
        int N = arr.length;
        String[] ans = new String[N - 1];
        int ansIndex = 0;
        for (int i = 0; i < N; i++) {
            if (i != index) {
                ans[ansIndex++] = arr[i];
            }
        }
        return ans;
    }


    public static void main(String[] args) {
        int testTimes = 10000;
        int strLen = 10;
        int arrLen = 10;
        for (int i = 0; i < testTimes; i++) {
            String[] test1 = generateRandomStringArr(arrLen, strLen);
            String[] test2 = copyStrignArr(test1);
            String ans1 = lowestString1(test1);
            String ans2 = lowestString2(test2);
            if(!ans1.equals(ans2)){
                System.out.println("Oops");
            }
        }
        System.out.println("finish");
    }

    public static String[] generateRandomStringArr(int arrLen, int strLen) {
        String[] strArr = new String[(int) Math.random() * arrLen];
        for (int i = 0; i < strArr.length; i++) {
            strArr[i] = generateRandomString(strLen);
        }
        return strArr;
    }

    public static String generateRandomString(int strLen) {
        char[] ans = new char[(int) (Math.random() * strLen) + 1];
        for (int i = 0; i < ans.length; i++) {
            int value = (int) (Math.random() * 10);
            ans[i] = Math.random() <= 0.5 ? (char) ('a' + value) : (char) ('A' + value);
        }
        return String.valueOf(ans);
    }

    public static String[] copyStrignArr(String[] arr) {
        String[] ans = new String[arr.length];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = String.valueOf(arr[i]);
        }
        return ans;
    }

}
