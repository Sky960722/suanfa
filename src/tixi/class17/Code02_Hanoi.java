package tixi.class17;

import java.util.Stack;

//暴力递归就是尝试
//1.把问题转化为规模缩小了的同类问题的子问题
//2.有明确的不需要进行递归的条件
//3.有当得到子问题的结果之后的决策过程
//4.不记录每一个子问题的解
public class Code02_Hanoi {

    //汉诺塔问题
//    有三根相邻的柱子，标号为A,B,C，A柱子上从下到上按金字塔状叠放着n个不同大小的圆盘，要把所有盘子一个一个移动到柱子B上，并且每次移动同一根柱子上都不能出现大盘子在小盘子上方，请问至少需要多少次移动，设移动次数为H(n)。

    //思路
    //1.所有n根柱子从左往右移这个过程可以看作三步
    //  1.n-1根柱子从左往中间移动
    //  2.n从左往右移动
    //  3.n-1从右往中移动
    //n根柱子从左往中间移动可以看成三步
    // 1.n-1根柱子从左往右移动
    // 2.n从左往中移动
    // 3.n-1根柱子从右往中移动
    //这种情况一共右6中
    //1.从左往右
    //2.从左往中
    //3.从中往右
    //4.从中往左
    //5.从右往左
    //6.从右往中
    public static void hanoi1(int n) {
        leftToRight(n);
    }

    //  1.n-1根柱子从左往中间移动
    //  2.n从左往右移动
    //  3.n-1从右往中移动
    public static void leftToRight(int n){
        if(n==1){
            System.out.println("Move 1 from left to right");
            return;
        }
        leftToMid(n-1);
        System.out.println("Move "+ n + " from left to right");
        midToRight(n-1);
    }

    //1.n-1根柱子从左往右移动
    //2.n从左往中移动
    //3.n-1从右往中移动
    private static void leftToMid(int n) {
        if(n==1){
            System.out.println("Move 1 from left to mid");
        }
        leftToRight(n-1);
        System.out.println("Move "+ n + " from left to mid");
        rightToMid(n-1);
    }

    //1.n-1根柱子从右往左移动
    //2.n从右往中移动
    //3.n-1从左往中移动
    private static void rightToMid(int n) {
        if(n == 1){
            System.out.println("Move 1 from right to mid");
        }
        rightToLeft(n-1);
        System.out.println("Move "+ n + " from right to mid");
        leftToMid(n-1);
    }

    //1.n-1根柱子从中往左移动
    //2.n从中往右移动
    //3.n-1从左往右移动
    private static void midToRight(int n) {
        if(n == 1){
            System.out.println("Move 1 from mid to right");
        }
        midToLeft(n-1);
        System.out.println("Move "+ n + " from mid to right");
        leftToRight(n-1);
    }

    //1.n-1根柱子从中往右移动
    //2.n从中往左移动
    //3.n-1从右往左移动
    private static void midToLeft(int n) {
        if(n == 1){
            System.out.println("Move 1 from mid to left");
        }
        midToRight(n-1);
        System.out.println("Move "+ n + " from mid to left");
        rightToLeft(n-1);
    }

    //1.n-1根柱子从右往中移动
    //2.n从右往左移动
    //3.n-1从中往右移动
    private static void rightToLeft(int n) {
        if(n == 1){
            System.out.println("Move 1 from right to left");
        }
        rightToMid(n-1);
        System.out.println("Move "+ n + " from right to left");
        midToLeft(n-1);
    }

    //上面6中情况，可以用三个参数统一表示，分别是from,to,other
    public static void hanoi2(int n) {
        if (n > 0) {
            func(n, "left", "right", "mid");
        }
    }


    public static void func(int n,String from,String to,String other){
        if(n==1){
            System.out.println("Move 1 from " + from +" to "+to );
            return;
        }
        func(n-1,from,other,to);
        System.out.println("Move n from " + from + " to "+to);
        func(n-1,other,to,from);
    }

    //3.用栈模拟递归
    //1.通过上面递归可以推出来，当前塔不断的将from到other这个过程压入栈中，直到n=1，为止
    //2.设定一个classRecord，有int base，from，to，other，finish
    public static class Record{
        public boolean finish;
        public int base;
        public String from;
        public String to;
        public String other;

        public Record(boolean finish, int base, String from, String to, String other) {
            this.finish = finish;
            this.base = base;
            this.from = from;
            this.to = to;
            this.other = other;
        }
    }

    public static void hanoi3(int N){
        if(N < 1){
            return;
        }
        Stack<Record> stack = new Stack<>();
        stack.push(new Record(false,N,"left","right","mid"));
        while (!stack.isEmpty()){
            Record cur = stack.pop();
            if(cur.base == 1){
                System.out.println("Move 1 from " + cur.from + " to " + cur.to);
                if (!stack.isEmpty()) {
                    stack.peek().finish = true;
                }
            }else {
                if (!cur.finish) {
                    stack.push(cur);
                    stack.push(new Record(false, cur.base - 1, cur.from, cur.other, cur.to));
                } else {
                    System.out.println("Move " + cur.base + " from " + cur.from + " to " + cur.to);
                    stack.push(new Record(false, cur.base - 1, cur.other, cur.to, cur.from));
                }
            }
        }
    }

}
