package tixi.class16;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class Code03_TopologicalOrderDFS1 {

    public static class DirectedGraphNode {
        public int label;
        public ArrayList<DirectedGraphNode> neighbors;

        public DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<DirectedGraphNode>();
        }
    }

    public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        HashMap<DirectedGraphNode, Record> map = new HashMap<>();
        for (DirectedGraphNode node : graph) {
            f(node, map);
        }
        ArrayList<Record> list = new ArrayList<>();
        for (Record cur : map.values()) {
            list.add(cur);
        }
        list.sort(new Comparator<Record>() {
            @Override
            public int compare(Record o1, Record o2) {
                return o1.deep == o2.deep ? 0 : o1.deep > o2.deep ? -1 : 1;
            }
        });
        ArrayList<DirectedGraphNode> ans = new ArrayList<>();
        for (Record record : list) {
            ans.add(record.node);
        }
        return ans;

    }

    public static class Record {
        public DirectedGraphNode node;
        public long deep;

        public Record(DirectedGraphNode n, long d) {
            node = n;
            deep = d;
        }
    }

    public static Record f(DirectedGraphNode node, HashMap<DirectedGraphNode, Record> order) {
        if (order.containsKey(node)) {
            return order.get(node);
        }

        long deep = 0;
        for (DirectedGraphNode neighbor : node.neighbors) {
            deep = Math.max(deep, f(neighbor, order).deep);
        }
        Record cur = new Record(node, deep + 1);
        order.put(node, cur);
        return cur;
    }

}
