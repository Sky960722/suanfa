package tixi.class16;


import tixi.class16.Graph.Graph;
import tixi.class16.Graph.Node;

import java.util.*;
//拓扑排序
//1.在图中找出所有入度为0的点输出
//2.把所有入度为0的点在图中删掉，继续找入度为0的点输出，周而复始
//3。图的所有点都被删除后，依次输出的顺序就是拓扑排序

//要求：有向图且其中没有环
//应用：事件安排，编译顺序
public class Code03_TopologySort {

    //1.设置queue zeroInQueue ,把in = 0放入队列，设置List ans
    //2.当node进入inQueue,则他对应的邻居 inNode--，因此设定一个结构Map<Node,Integer>inMap，记录每个节点对应的入度
    //3.先找出入度=0的节点放入0入度队列
    //4。从0队列中弹出节点，当前节点必定是入度为0的节点，放入ans中。
    //5.通过inMap，可以消除当前这个节点对其它节点的影响，当前节点入度=0，放入ans中，则表明该节点已完成，则其他依赖此节点的节点的in--
    //6.对queue重复循环，完成上面的ans，返回
    public static List<Node> sortedTopology(Graph graph) {
        //只有剩余入度为0的点，才进入这个队列
        Queue<Node> zeroInQueue = new LinkedList<>();
        //key 某个节点 value 剩余的入度
        Map<Node, Integer> inMap = new HashMap<>();
        List<Node> ans = new ArrayList<>();
        for (Node node : graph.nodes.values()) {
            inMap.put(node, node.in);
            if (node.in == 0) {
                zeroInQueue.add(node);
            }
        }
        while (!zeroInQueue.isEmpty()) {
            Node cur = zeroInQueue.poll();
            ans.add(cur);
            for (Node next : cur.nexts) {
                inMap.put(next, inMap.get(next) - 1);
                if(inMap.get(next)==0){
                    zeroInQueue.add(next);
                }
            }
        }

        return ans;
    }
}
