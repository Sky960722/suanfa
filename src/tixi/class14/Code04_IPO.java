package tixi.class14;


import java.util.Comparator;
import java.util.PriorityQueue;

//项目收益
//        输入：
//        正数数组costs正数数组profits正数k
//        正数k含义：能串行的最多做k个项目
//        costs[i] 表示 i 号项目的花费
//        profits[i] 表示 i 号项目在扣除花费之后还能挣到的钱(利润)
//        w表示你初始的资金
//        说明：
//        你每做完一个项目，马上获得的收益，可以支持你去做下一个项目。
//        输出：
//        你最后获得的最大钱数。
public class Code04_IPO {

    public static class Program {
        public int p;
        public int c;

        public Program(int p, int c) {
            this.p = p;
            this.c = c;
        }
    }

    //贪心算法：
    //1.根据profits[i] 表示 i 号项目在扣除花费之后还能挣到的钱(利润) ，可以知道每次拿到当前能拿到最大利润的项目，就能拿到最大钱数
    //设立两个队列，一个队列存放最小的花费的项目minCost，一个队列存放最大的利润
    //对k做循环，每次都从micCost取出当前能取出的最大利润项目
    public static int findMaximizedCapital(int K, int W, int[] Profits, int[] Capital) {
        PriorityQueue<Program> minCost = new PriorityQueue<>(((o1, o2) -> o1.c - o2.c));
        PriorityQueue<Program> maxPro = new PriorityQueue<>((o1, o2) -> o2.p - o1.p);
        //1.minCost塞满当前项目,根据花费排序
        for (int i = 0; i < Profits.length; i++) {
            minCost.add(new Program(Profits[i], Capital[i]));
        }
        //对k次项目做循环
        for (int i = 0; i < K; i++) {
            //往mmaxPro里放满足条件能够花费的项目
            while (!minCost.isEmpty() && minCost.peek().c <= W) {
                maxPro.add(minCost.poll());
            }
            //表示当前项目没有能够消费的
            if (maxPro.isEmpty()) {
                break;
            }
            W += maxPro.poll().p;
        }
        return W;
    }

}
