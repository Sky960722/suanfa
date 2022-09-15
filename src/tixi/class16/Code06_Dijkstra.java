package tixi.class16;

import tixi.class16.Graph.Edge;
import tixi.class16.Graph.Node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Code06_Dijkstra {

    //单元到每个点的最小路径
    //选择一个点作为起始点，然后from这个点出发，
    //1.从from这个点中解锁所有的边，用hashMap记录当前从from到每个点的最小距离
    //2.选择除了from以外的最小距离的点，然后解锁新的边和距离，直到所有距离都更新完
    //3.用Set记录当前以选择的点
    public static HashMap<Node, Integer> dijkstra1(Node from) {
        HashMap<Node, Integer> nodeDis = new HashMap<>();
        nodeDis.put(from, 0);
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
            minNode = selectMinNode(nodeDis, set);
        }
        return nodeDis;
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

    private static class Record {
        private Node node;
        private Integer dis;

        public Record(Node node, Integer dis) {
            this.node = node;
            this.dis = dis;
        }
    }

    public static class NodeHeap {
        private Node[] heap;
        private HashMap<Node, Integer> indexMap;
        private HashMap<Node, Integer> distanceMap;
        private int size;

        public NodeHeap(int size) {
            heap = new Node[size];
            indexMap = new HashMap<>();
            distanceMap = new HashMap<>();
        }

        private void swap(int index1, int index2) {
            indexMap.put(heap[index1], index2);
            indexMap.put(heap[index2], index1);
            Node tmp = heap[index1];
            heap[index1] = heap[index2];
            heap[index2] = tmp;
        }

        private void heapInsert(int index) {
            while (distanceMap.get((index - 1) / 2) > distanceMap.get(index)) {
                swap((index - 1) / 2, index);
                index = (index - 1) / 2;
            }
        }

        private void heapify(int index, int size) {
            int left = index * 2 + 1;
            while (left < size) {
                int small = left + 1 < size && distanceMap.get(heap[left + 1]) < distanceMap.get(heap[left]) ?
                        left + 1 : left;
                small = distanceMap.get(heap[small]) < distanceMap.get(heap[index]) ? small : index;
                if (small == index) {
                    break;
                }
                swap(small, index);
                index = small;
                left = index * 2 + 1;
            }
        }

        private boolean isEntered(Node node) {
            return indexMap.containsKey(node);
        }

        private boolean inHeap(Node node) {
            return isEntered(node) && indexMap.get(node) != -1;
        }

        public void addOrUpdateOrIgnore(Node node, int distance) {
            if (inHeap(node)) {
                distanceMap.put(node, Math.min(distanceMap.get(node), distance));
                heapInsert(indexMap.get(node));
            }
            if (!isEntered(node)) {
                heap[size] = node;
                indexMap.put(node, size);
                distanceMap.put(node, distance);
                heapInsert(size++);
            }
        }

        public Record pop() {
            Record record = new Record(heap[0], distanceMap.get(heap[0]));
            swap(0, size - 1);
            indexMap.put(heap[size - 1], -1);
            distanceMap.remove(heap[size - 1]);
            heap[size - 1] = null;
            heapify(0, --size);
            return record;
        }

        public boolean isEmpty() {
            return size == 0;
        }
    }

    //改进后的dijkstra2算法
    //用加强堆实现从剩下未被确定的node里挑选距离最近的节点
    public static HashMap<Node, Integer> dijkstra2(Node head, int size) {
        NodeHeap heap = new NodeHeap(size);
        heap.addOrUpdateOrIgnore(head, 0);
        HashMap<Node, Integer> ans = new HashMap<>();
        while (!heap.isEmpty()){
            Record record = heap.pop();
            Node cur = record.node;
            int dis = record.dis;
            for (Edge edge : cur.edges) {
                heap.addOrUpdateOrIgnore(edge.to,edge.weight+dis);
            }
            ans.put(cur,dis);
        }
        return ans;
    }
}
