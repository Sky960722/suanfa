package tixi.class07;

import java.security.PublicKey;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

//最大线段重合问题
//        给定很多线段，每个线段有两个整数[begin,end]，表示一条线段的起始位置和结束位置（end大于begin且都非负）
//        规定：重合区域必须大于等于1：例如[1,4]和[4,5]就不算重合
//        输入一个二维数组，数组的每个元素是一个包含两个元素的数组，分别为每个线段begin和end
//        返回线段最多重合区域中线段的个数
//        例如 arr = [[1,3],[2,4],[4,8]]
//        返回2,因为在[2,3]这个范围内有2个连段重合
public class Code01_CoverMax {

    public static int maxCover1(int[][] lines) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < lines.length; i++) {
            min = Math.min(min, lines[i][0]);
            max = Math.max(max, lines[i][1]);
        }
        int cover = 0;
        for (double p = min + 0.5; p < max; p++) {
            int cur = 0;
            for (int i = 0; i < lines.length; i++) {
                if (lines[i][0] < p && lines[i][1] > p) {
                    cur++;
                }
            }
            cover = Math.max(cover, cur);
        }
        return cover;
    }

    public static class Line {
        public int start;
        public int end;

        public Line(int s, int e) {
            this.start = s;
            this.end = e;
        }
    }

    public static int maxCover2(int[][] lines) {
        Line[] lineArr = new Line[lines.length];
        for (int i = 0; i < lines.length; i++) {
            lineArr[i] = new Line(lines[i][0], lines[i][1]);
        }
        Arrays.sort(lineArr, new Comparator<Line>() {
            @Override
            public int compare(Line o1, Line o2) {
                return o1.start - o2.start;
            }
        });
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        int max = 0;

        for (int i = 0; i < lineArr.length; i++) {
            while (!heap.isEmpty() && heap.peek() <= lineArr[i].start) {
                heap.poll();
            }
            heap.add(lineArr[i].end);
            max = Math.max(max, heap.size());
        }
        return max;
    }

    public static int maxCover3(int[][] lines) {
//        Arrays.sort(lines, new Comparator<int[]>() {
//            @Override
//            public int compare(int[] o1, int[] o2) {
//                return o1[0] - o2[1];
//            }
//        });

        Arrays.sort(lines, (a, b) -> (a[0] - b[0]));
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        int max = 0;
        for (int i = 0; i < lines.length; i++) {
            while (!heap.isEmpty() && heap.peek() <= lines[i][0]) {
                heap.poll();
            }
            heap.add(lines[i][1]);
            max = Math.max(max, heap.size());
        }
        return max;
    }

    public static int[][] generateLines(int N, int L, int R) {
        int size = (int) (Math.random() * N) + 1;
        int[][] lines = new int[size][2];
        for (int i = 0; i < size; i++) {
            int a = L + (int) (Math.random() * (R - L + 1));
            int b = L + (int) (Math.random() * (R - L + 1));
            if (a == b) {
                if (a != R) {
                    b = a + 1;
                } else {
                    a = a - 1;
                }
            }
            lines[i][0] = Math.min(a, b);
            lines[i][1] = Math.max(a, b);
        }
        return lines;
    }

    public static void main(String[] args) {


        System.out.println("test begin");
        int N = 200;
        int L = 0;
        int R = 200;
        int testTimes = 20;
        for (int i = 0; i < testTimes; i++) {
            int[][] lineArr = generateLines(N, L, R);
            int ans1 = maxCover1(lineArr);
            int ans2 = maxCover2(lineArr);
            int ans3 = maxCover3(lineArr);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("Oops");
            }
        }
        System.out.println("test end");
    }


}
