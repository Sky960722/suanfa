package tixi.class16;

import java.util.*;

public class Code03_TopologicalOrderDFS2 {

    // 不要提交这个类
    public static class DirectedGraphNode {
        public int label;
        public ArrayList<DirectedGraphNode> neighbors;

        public DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<DirectedGraphNode>();
        }
    }

    //深度优先遍历
    //点次：这个节点往后经过的节点总数(包括自己)
    //拓扑排序：按点次进行排序，高的在前，低的在后面
    //求出所有点的点次，用map存储，然后排序
    public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        HashMap<DirectedGraphNode,Record> order = new HashMap<>();
        for (DirectedGraphNode cur : graph) {
            f(cur,order);
        }
        ArrayList<Record> recordArr = new ArrayList<>();
        for (Record r : order.values()) {
            recordArr.add(r);
        }
//        recordArr.sort(new Comparator<Record>() {
//            @Override
//            public int compare(Record o1, Record o2) {
//                return o1.count == o2.count?0:(o1.count > o2.count? -1:1);
//            }
//        });
        recordArr.sort((o1,o2) -> {return o1.count == o2.count?0:(o1.count > o2.count? -1:1);});
        ArrayList<DirectedGraphNode> ans = new ArrayList<>();
        for (Record record : recordArr) {
            ans.add(record.node);
        }
        return ans;
    }

    //点次，每个节点对应的点次，
    public static class Record {
        public DirectedGraphNode node;
        public long count;

        public Record(DirectedGraphNode n, long o) {
            node = n;
            count = o;
        }
    }

    //记录当前每个节点的点次
    //order 缓存当前每个节点的record
    public static Record f(DirectedGraphNode node, HashMap<DirectedGraphNode, Record> order) {
        if (order.containsKey(node)) {
            return order.get(node);
        }

        long count = 0;
        for (DirectedGraphNode neighbor : node.neighbors) {
            count += f(node, order).count;
        }
        Record curRec = new Record(node, count + 1);
        order.put(node, curRec);
        return curRec;
    }
}
