package tixi.class16;

import tixi.class16.Graph.Node;

import java.util.*;

public class Code01_BFS {

    //从Node出发，进行宽度优先遍历
    public static void bfs(Node start){
        if(start == null){
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        HashSet<Node> set = new HashSet<>();
        queue.add(start);
        set.add(start);
        while (!queue.isEmpty()){
            Node cur = queue.poll();
            System.out.println(cur.value);
            for (Node next: cur.nexts) {
                if(!set.contains(next)){
                    queue.add(next);
                    set.add(next);
                }
            }
        }
    }
}
