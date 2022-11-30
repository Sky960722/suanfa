package tixi.class17;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Code03_PrintAllSubsquences {
    //打印一个字符串的全部子序列
    //s -> "abc" ->
    //思路：
    //1.对字符串中每个字符单个扫描，选择加或不加,这些都能成为一条路径
    //2.index == s.length,则将经过的path路径添加到ans中
    public static List<String> subs(String s) {
        char[] str = s.toCharArray();
        String path = "";
        List<String> ans = new ArrayList<>();
        process1(str, 0, ans, path);
        return ans;
    }

    public static void process1(char[] s,int index,List<String > ans,String path){
        if(index == s.length){
            ans.add(path);
            return;
        }
        String no = path;
        process1(s,index+1,ans,no);
        String yes = path+String.valueOf(s[index]);
        process1(s,index+1,ans,yes);
    }

    //打印一个字符串的全部子序列，要求不能出现重复字面值的子序列
    public static List<String> subsNoRepeat(String s) {
        char[] str = s.toCharArray();
        String path = "";
        HashSet<String> set = new HashSet<>();
        process2(str, 0, set, path);
        List<String> ans = new ArrayList<>();
        for (String cur : set) {
            ans.add(cur);
        }
        return ans;
    }

    //思路：
    //声明一个set，存储所有的路径
    public static void process2(char[] s, int index, Set<String> ans, String path){
        if(index == s.length){
            ans.add(path);
            return;
        }
        String no = path;
        process2(s,index+1,ans,no);
        String yes = path+String.valueOf(s[index]);
        process2(s,index+1,ans,yes);
    }

}
