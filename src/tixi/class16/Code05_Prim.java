package tixi.class16;


import tixi.class16.Graph.Edge;
import tixi.class16.Graph.Graph;
import tixi.class16.Graph.Node;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

//最小生成树算法之Prim
//1.可以从任意节点出发来寻找最小生成树
//2.某个点加入到被选取的点后中后，解锁这个点出发的所有的新的边
//3.在所有解锁的边中选最小的边，然后看看这个边会不会形成环
//4.如果会，不要当前边，继续考察剩下解锁的边中最小的边，重复3
//5.如果不会，要当前边，将该边的指向点加入到被选取的点中，重复2
//6.当所有点都被选取，最小生成树就得到了
public class Code05_Prim {

    //1，弄一个堆，存放edge，Set存放点，判断当前点是否都加入进去了
    //2.for循环（防止树的出现），解锁当前循环的第一个点，然后这个点解锁新的边，存放在edge中
    //3.从这个edge解锁新的点，如果这个点不在Set里，这个edge存放在ans，然后对下一个点继续上述操作

    public static Set<Edge> primMST(Graph graph) {
        Set<Edge> ans = new HashSet<>();
        PriorityQueue<Edge> queue = new PriorityQueue<>(new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return o1.weight <= o2.weight ? -1 : 1;
            }
        });
        Set<Node> set = new HashSet<>();
        for (Node node : graph.nodes.values()) {
            if (!set.contains(node)) {
                set.add(node);
                for (Edge edge : node.edges) {
                    queue.add(edge);
                }
                while (!queue.isEmpty()) {
                    Edge cur = queue.poll();
                    Node to = queue.poll().to;
                    if (!set.contains(to)) {
                        set.add(to);
                        ans.add(cur);
                        for (Edge edge : to.edges) {
                            queue.add(edge);
                        }
                    }
                }
            }
        }
        return ans;
    }


    // 请保证graph是连通图
    // graph[i][j]表示点i到点j的距离，如果是系统最大值代表无路
    // 返回值是最小连通图的路径之和

    //思路：
    //1.graph.length是当前的节点数，用len表示
    //2.distance[]记录当前到的节点的最小权重
    //3.visit[]记录当前节点的访问情况
    //4.for循环，每次找出当前最小权重的点，然后更新这个点更新后的新的权重的边
    public static int prim(int[][] graph) {
        int len = graph.length;
        int[] dis = new int[len];
        boolean[] visit = new boolean[len];
        visit[0] = true;
        for (int i = 0; i < len; i++) {
            dis[i] = graph[0][i];
        }
        int sum = 0;
        for (int i = 0; i < len; i++) {
            int minDis = Integer.MAX_VALUE;
            int minIndex = -1;
            for (int j = 0; j < len; j++) {
                if (!visit[j] && minDis > dis[j]) {
                    minDis = dis[j];
                    minIndex = j;
                }
            }
            if(minIndex == -1){
                return sum;
            }
            sum += minDis;
            visit[minIndex] = true;
            for (int j = 0; j < len; j++) {
                dis[j] =Math.min(dis[j],graph[minIndex][j]);
            }
        }
        return sum;
    }
}
