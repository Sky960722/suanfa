package tixi.class14;


import java.util.HashSet;
import java.util.List;

//点灯问题
//给定一个字符串str，只由‘X’和‘.’两种字符构成。‘X’表示墙，不能放灯，也不需要点亮。‘.’表示居民点，可以放灯，需要点亮。如果灯放在i位置，可以让i-1，
// i和i+1三个位置被点亮。返回如果点亮str中所有需要点亮的位置，至少需要几盏灯。
public class Code01_Light {

    //贪心算法
    //1.当前i位置是x，则直接跳过当前位置
    //2.当前i位置是.
    //2.1.i+1位置是x，则i位置点灯
    //2.2.i+1位置是.则判断i+2位置
    //2.3.如果i+2位置是.,则i+1位置点灯，跳到i+3
    //2.3.如果i+2位置是x,则i+1位置点灯，跳到i+3
    //只要i+1位置是.,则直接在i+1位置点灯，然后跳到i+3
    public static int minLight1(String road) {
        int ans = 0;
        int i = 0;
        char[] roadArr = road.toCharArray();
        while (i < roadArr.length) {
            //1.当前i位置是x
            if (roadArr[i] == 'x') {
                i++;
            } else {
                ans++;
                //如果当前i到了尽头，则直接退出当前循环
                if (i + 1 == roadArr.length) {
                    break;
                } else {
                    //2.当前i位置是.
                    //1.当前i+1位置是x，则i位置点灯，同时跳到i+2位置
                    if (roadArr[i + 1] == 'x') {
                        i = i + 2;
                    } else {
                        i = i + 3;
                    }
                }
            }
        }
        return ans;
    }

    public static int minLight2(String road) {
        if (road == null || road.length() == 0) {
            return 0;
        }
        return process(road.toCharArray(), 0, new HashSet<>());
    }

    //暴力递归
    //求每个.放灯或不妨灯的情况，如果满足i-1，i和i+1三个位置被点亮。返回如果点亮str中所有需要点亮的位置,否则返回最大值
    //用hasetSet记录当前灯点亮的位置
    //index记录当前灯的位置
    public static int process(char[] str, int index, HashSet<Integer> lights) {
        //当前遍历的位置到了str的末尾
        if (index == str.length) {
            for (int i = 0; i < str.length; i++) {
                if (str[i] != 'x') {
                    //判断当前点灯情况是否满足全部居民亮的情况，不满足，返回最大值
                    if (!lights.contains(i - 1) && !lights.contains(i) && !lights.contains(i + 1)) {
                        return Integer.MAX_VALUE;
                    }
                }
            }
            return lights.size();
        } else {
            //no表示当前位置没有点灯
            int no = process(str, index + 1, lights);
            //yes有两种情况，一种情况是墙，则直接赋最大值
            int yes = Integer.MAX_VALUE;
            //第二种是.,则lights添加灯,然后传入process
            if (str[index] == '.') {
                lights.add(index);
                yes = process(str, index + 1, lights);
                lights.remove(index);
            }
            return Math.min(yes, no);
        }
    }

    //更简洁的解法
    //两个x之间，数一下.的数量，然后除以3，向上取整
    //把灯数累加
    public static int minLight3(String road) {
        char[] str = road.toCharArray();
        //cur 表示当前两个x之间的数量
        int cur = 0;
        int light = 0;
        for (char c : str) {
            //遇到第一个x结束cur计数
            //同时统计当前需要点灯的数量，cur+2是为了向上取整
            if (c == 'x') {
                light += (cur + 2) / 3;
                cur = 0;
            } else {
                cur++;
            }
        }
        //最后统计到末尾的情况
        light += (cur + 2) / 3;
        return light;
    }

    public static String generateRandomString(int len) {
        char[] charArr = new char[(int) (Math.random() * len)];
        for (int i = 0; i < charArr.length; i++) {
            charArr[i] = Math.random() < 0.5 ? 'x' : '.';
        }
        return String.valueOf(charArr);
    }

    public static void main(String[] args) {
        int testTimes = 1000;
        int len = 10;
        for (int i = 0; i < testTimes; i++) {
            String test = generateRandomString(len);
            int a1 = minLight1(test);
            int a2 = minLight2(test);
            int a3 = minLight3(test);
            if(a1!=a2 || a2!=a3){
                System.out.println("Oops");
                System.out.println(test);
            }
        }
        System.out.println("finish");

    }
}
