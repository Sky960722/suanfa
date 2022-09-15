package tixi.class16;


import tixi.class16.Graph.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

//127 · 拓扑排序
//        给定一个有向图，图节点的拓扑排序定义如下:
//
//        对于图中的每一条有向边 A -> B , 在拓扑排序中A一定在B之前.
//        拓扑排序中的第一个节点可以是图中的任何一个没有其他节点指向它的节点.
//        针对给定的有向图找到任意一种拓扑排序的顺序.
//        你可以假设图中至少存在一种拓扑排序
//
//        图结点的个数 <= 5000
//        样例
//        样例 1：
//
//        输入：
//        graph = {{0,1,2,3},{1,4},{2,4,5},{3,4,5},4,5}
//        输出：
//        [0, 1, 2, 3, 4, 5]
//
//        拓扑排序可以为:
//        [0, 1, 2, 3, 4, 5]
//        [0, 2, 3, 1, 5, 4]
//        ...
//        您只需要返回给定图的任何一种拓扑顺序。
//https://www.lintcode.com/problem/127/
public class Code03_TopologicalOrderBFS {

    // 不要提交这个类
    public static class DirectedGraphNode {
        public int label;
        public ArrayList<DirectedGraphNode> neighbors;

        public DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<DirectedGraphNode>();
        }
    }

    //宽度优先遍历
    //1.弄一个HashMap记录对应的入度
    //2.第一次遍历graph，记录对应入度为0
    //3.第二次遍历graph，遍历每个节点的对应邻居，每个邻居的inDegree+1，表明当前节点依赖多少个节点
    public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        HashMap<DirectedGraphNode,Integer> inDegreeMap = new HashMap<>();
        for (DirectedGraphNode node : graph) {
            inDegreeMap.put(node,0);
        }
        for (DirectedGraphNode node : graph) {
            for (DirectedGraphNode neighbor : node.neighbors) {
                inDegreeMap.put(neighbor,inDegreeMap.get(neighbor)+1);
            }
        }
        Queue<DirectedGraphNode> queue = new LinkedList<>();
        for (DirectedGraphNode node : inDegreeMap.keySet()) {
            if(inDegreeMap.get(node) == 0){
                queue.add(node);
            }
        }
        ArrayList<DirectedGraphNode> list = new ArrayList<>();
        while (!queue.isEmpty()){
            DirectedGraphNode cur = queue.poll();
            list.add(cur);
            for (DirectedGraphNode next : cur.neighbors) {
                inDegreeMap.put(next,inDegreeMap.get(next)-1);
                if(inDegreeMap.get(next) == 0){
                    queue.add(next);
                }
            }
        }
        return list;

    }
}
