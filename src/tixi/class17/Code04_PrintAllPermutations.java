package tixi.class17;


import com.sun.org.apache.bcel.internal.generic.SWAP;

import java.util.ArrayList;
import java.util.List;

public class Code04_PrintAllPermutations {

    //打印一个字符串的全部排列
    public static List<String> permutation1(String s) {
        List<String> ans = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return ans;
        }
        char[] str = s.toCharArray();
        ArrayList<Character> rest = new ArrayList<Character>();
        for (char cha : str) {
            rest.add(cha);
        }
        String path = "";
        f(rest, path, ans);
        return ans;
    }

    public static void f(ArrayList<Character> rest, String path, List<String> ans) {
        if (rest.isEmpty()) {
            ans.add(path);
        } else {
            int N = rest.size();
            for (int i = 0; i < N; i++) {
                char cur = rest.get(i);
                rest.remove(i);
                f(rest, path + cur, ans);
                rest.add(i, cur);
            }
        }
    }

    public static List<String> permutation2(String s) {
        List<String> ans = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return ans;
        }
        char[] str = s.toCharArray();
        g1(str, 0, ans);
        return ans;
    }

    //用数组下标做交换
    private static void g1(char[] str, int index, List<String> ans) {
        if(index == str.length){
            ans.add(String.valueOf(str));
            return;
        }
        for (int i = index; i < str.length; i++) {
            swap(str,index,i);
            g1(str,index+1,ans);
            swap(str,index,i);
        }
    }

    private static void swap(char[] str,int a,int b){
        char tmp = str[a];
        str[a] = str[b];
        str[b] =tmp;
    }

    //再进一步，用boolean[]标记访问过的字符，ascall码是0~255
    //打印一个字符串的全部排列，要求不能出现重复的排列
    public static List<String> permutation3(String s) {
        List<String> ans = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return ans;
        }
        char[] str = s.toCharArray();
        g2(str, 0, ans);
        return ans;
    }

    private static void g2(char[] str, int index, List<String> ans) {
        if(index == str.length){
            ans.add(String.valueOf(str));
            return;
        }
        boolean[] visited = new boolean[256];
        for (int i = index; i < str.length; i++) {
            if(!visited[str[i]]){
                swap(str,index,i);
                g2(str,index+1,ans);
                swap(str,index,i);
                visited[str[i]] = true;
            }

        }
    }
}
