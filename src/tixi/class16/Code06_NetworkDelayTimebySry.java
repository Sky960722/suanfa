package tixi.class16;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

//743. 网络延迟时间
//        有 n 个网络节点，标记为 1 到 n。
//
//        给你一个列表 times，表示信号经过 有向 边的传递时间。 times[i] = (ui, vi, wi)，其中 ui 是源节点，vi 是目标节点， wi 是一个信号从源节点传递到目标节点的时间。
//
//        现在，从某个节点 K 发出一个信号。需要多久才能使所有节点都收到信号？如果不能使所有节点收到信号，返回 -1 。
//
//
//
//        示例 1：
//
//
//
//        输入：times = [[2,1,1],[2,3,1],[3,4,1]], n = 4, k = 2
//        输出：2
//        示例 2：
//
//        输入：times = [[1,2,1]], n = 2, k = 1
//        输出：1
//        示例 3：
//
//        输入：times = [[1,2,1]], n = 2, k = 2
//        输出：-1
//https://leetcode.cn/problems/network-delay-time/
public class Code06_NetworkDelayTimebySry {


    public static int networkDelayTime1(int[][] times, int n, int k) {
        Graph graph = create(times);
        Node from = graph.nodes.get(k);
        HashMap<Node, Integer> nodeDis = new HashMap<>();
        boolean[] visited = new boolean[n];
        nodeDis.put(from, 0);
        visited[k - 1] = true;
        Set<Node> set = new HashSet<>();
        Node minNode = selectMinNode(nodeDis, set);
        while (minNode != null) {
            int dis = nodeDis.get(minNode);
            for (Edge edge : minNode.edges) {
                if (!nodeDis.containsKey(edge.to)) {
                    nodeDis.put(edge.to, edge.weight + dis);
                }
                if (nodeDis.get(edge.to) > edge.weight + dis) {
                    nodeDis.put(edge.to, edge.weight + dis);
                }
            }
            set.add(minNode);
            visited[minNode.value - 1] = true;
            minNode = selectMinNode(nodeDis, set);
        }
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                return -1;
            }
        }
        int ans = Integer.MIN_VALUE;
        for (Integer num : nodeDis.values()) {
            ans = Integer.max(ans, num);
        }
        return ans;
    }


    private static Node selectMinNode(HashMap<Node, Integer> map, Set<Node> set) {
        int minDis = Integer.MAX_VALUE;
        Node minNode = null;
        for (Node node : map.keySet()) {
            if (!set.contains(node) && minDis > map.get(node)) {
                minNode = node;
                minDis = map.get(node);
            }
        }
        return minNode;
    }

    public static Graph create(int[][] times) {
        Graph graph = new Graph();
        for (int i = 0; i < times.length; i++) {

            int fi = times[i][0];
            int ti = times[i][1];
            int wi = times[i][2];
            if (!graph.nodes.containsKey(fi)) {
                Node from = new Node(fi);
                graph.nodes.put(fi, from);
            }
            if (!graph.nodes.containsKey(ti)) {
                Node to = new Node(ti);
                graph.nodes.put(ti, to);
            }
            Node from = graph.nodes.get(fi);
            Node to = graph.nodes.get(ti);
            Edge edge = new Edge(from, to, wi);
            from.nodes.add(to);
            from.edges.add(edge);
            graph.edges.add(edge);

        }
        return graph;
    }

    public static class Graph {
        public HashMap<Integer, Node> nodes;
        public HashSet<Edge> edges;

        public Graph() {
            nodes = new HashMap<>();
            edges = new HashSet<>();
        }
    }

    public static class Node {
        private int value;
        private ArrayList<Node> nodes;
        private ArrayList<Edge> edges;

        public Node(int v) {
            this.value = v;
            this.nodes = new ArrayList<>();
            this.edges = new ArrayList<>();
        }
    }

    public static class Edge {
        public Node from;
        public Node to;
        public int weight;

        public Edge(Node n1, Node n2, int w) {
            from = n1;
            to = n2;
            weight = w;
        }
    }


    public static void main(String[] args) {
        int[][] times = new int[3][3];
        times[0][0] = 2;
        times[0][1] = 1;
        times[0][2] = 1;
        times[1][0] = 2;
        times[1][1] = 3;
        times[1][2] = 1;
        times[2][0] = 3;
        times[2][1] = 4;
        times[2][2] = 1;
        int ans = networkDelayTime1(times, 4, 2);
        System.out.println(ans);
    }
}
