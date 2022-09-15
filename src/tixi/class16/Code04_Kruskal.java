package tixi.class16;


import tixi.class16.Graph.Edge;
import tixi.class16.Graph.Graph;
import tixi.class16.Graph.Node;

import java.util.*;

//最小生成树算法之Kruskal
//1.总是从权值最小的边开始考虑，依次考察权值依次变大的边
//2.当前的边要么进入最小生成树的集合，要么丢弃
//3.如果当前的边进入最小生成树的集合中不会形成环，就要当前边
//4.如果当前的边进入最小生成树的集合中会形成环，就不要当前边
//5.考察完所有边之后，最小生成树的集合也得到了
public class Code04_Kruskal {

    public static class UnionFind {
        private HashMap<Node, Node> parents;
        private HashMap<Node, Integer> sizeMap;

        public UnionFind(HashMap<Integer, Node> nodes) {
            parents = new HashMap<>();
            sizeMap = new HashMap<>();
            for (Node node : nodes.values()) {
                parents.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        private Node find(Node n) {
            Stack<Node> stack = new Stack<>();
            while (n != parents.get(n)) {
                stack.push(n);
                n = parents.get(n);
            }
            while (!stack.isEmpty()) {
                parents.put(stack.pop(), n);
            }
            return n;
        }

        public void union(Node n1, Node n2) {
            Node f1 = find(n1);
            Node f2 = find(n2);
            if (f1 != f2) {
                Node big = sizeMap.get(f1) >= sizeMap.get(f2) ? f1 : f2;
                Node small = big == f1 ? f2 : f1;
                parents.put(small, big);
                sizeMap.put(big, sizeMap.get(big) + sizeMap.get(small));
                sizeMap.remove(small);
            }
        }

        public boolean isSameSet(Node n1, Node n2) {
            return find(n1) == find(n2);
        }

        public int sets() {
            return sizeMap.size();
        }
    }

    //贪心，对所有edge做个小顶堆（权重）
    //对边的from和to做个判断，是否已经成环，可以用UnionFind，并查集实现
    //成环的，舍弃这条边，继续下一条边，直到当前点都在一个集合中
    public static Set<Edge> kruskalMST(Graph graph) {
        UnionFind uf = new UnionFind(graph.nodes);
        PriorityQueue<Edge> queue = new PriorityQueue<>(new Comparator<Edge>() {
            @Override
            public int compare(Edge o1, Edge o2) {
                return o1.weight <= o2.weight ? -1 : 1;
            }
        });
        for (Edge edge : graph.edges) {
            queue.add(edge);
        }
        Set<Edge> ans = new HashSet<>();
        while (uf.sets() > 1){
            Edge cur = queue.poll();
            Node from = cur.from;
            Node to = cur.to;
            if(!uf.isSameSet(from,to)){
                ans.add(cur);
                uf.union(from,to);
            }
        }
        return ans;
    }
}
