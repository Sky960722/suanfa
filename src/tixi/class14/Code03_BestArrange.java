package tixi.class14;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.PriorityQueue;

//会议室宣讲
//一些项目要占用一个会议室宣讲，会议室不能同时容纳两个项目的宣讲
//给你每一个项目开始的时间和结束的时间
//你来安排宣讲的日程，要求会议室进行的宣讲的场次最多
//返回最多的宣讲场次
public class Code03_BestArrange {

    public static class Program {
        public int start;
        public int end;

        public Program(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    //贪心算法
    //按会场结束时间排序，弹出下一个开始时间和前一个结束时间做对比，满足开始时间小于结束时间的，记录数＋1
    //声明一个priority队列，以endDate作为比较对象
    public static int bestArrange1(Program[] programs) {
        if (programs.length == 0 || programs == null) {
            return 0;
        }
        PriorityQueue<Program> queue = new PriorityQueue<>((o1, o2) -> o1.end - o2.end);
        for (Program program : programs) {
            queue.add(program);
        }
        int ans = 0;
        int timeLine = 0;
        Program cur = null;
        while (!queue.isEmpty()) {
            cur = queue.poll();
            if (timeLine <= cur.start) {
                ans++;
                timeLine = cur.end;
            }
        }
        return ans;
    }

    //直接对program做排序，arrays.sort,声明timeLine，timeLine小于当前数据对象的，+1
    public static int bestArrange2(Program[] programs) {
        Arrays.sort(programs, (o1, o2) -> o1.end - o2.end);
        int ans = 0;
        int timeLine = 0;
        for (int i = 0; i < programs.length; i++) {
            if (timeLine <= programs[i].start) {
                ans++;
                timeLine = programs[i].end;
            }
        }
        return ans;
    }

    //暴力递归
    //
    public static int bestArrange3(Program[] programs) {
        if(programs.length == 0 || programs == null){
            return 0;
        }
        return process(programs,0,0);
    }

    public static int process(Program[] programs, int done, int timeLine) {
        if (programs.length == 0) {
            return done;
        }

        int max = done;

        for (int i = 0; i < programs.length; i++) {
            if (programs[i].start >= timeLine) {
                Program[] next = copyButExcept(programs, i);
                max = Math.max(max, process(next, done + 1, programs[i].end));
            }
        }
        return max;
    }

    public static Program[] copyButExcept(Program[] programs, int i) {
        Program[] ans = new Program[programs.length - 1];
        int index = 0;
        for (int k = 0; k < programs.length; k++) {
            if (k != i) {
                ans[index++] = programs[k];
            }
        }
        return ans;
    }

    public static Program[] generatePrograms(int maxSize, int maxTime) {
        Program[] ans = new Program[(int) (Math.random() * maxSize)];
        for (int i = 0; i < ans.length; i++) {
            int start = (int) (Math.random() * maxTime);
            int end = (int) (Math.random() * maxTime);
            if (start == end) {
                ans[i] = new Program(start, end + 1);
            } else {
                ans[i] = new Program(Math.min(start, end), Math.max(start, end));
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int testTimes = 100000;
        int programSize = 12;
        int timeMax = 20;
        for (int i = 0; i < testTimes; i++) {
            Program[] programs = generatePrograms(programSize, timeMax);
            int a1 = bestArrange1(programs);
            int a2 = bestArrange2(programs);
            int a3 = bestArrange3(programs);
            if (a1  != a3 || a2!= a3) {
                System.out.println("Oops!");
            }

        }
        System.out.println("finish!");
    }
}


