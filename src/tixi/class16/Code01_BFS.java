package tixi.class16;

import tixi.class16.Graph.Node;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Code01_BFS {

    //从Node出发，进行宽度优先遍历
    public static void bfs(Node start){
        if(start == null){
            return;
        }
        Queue<Node> stack = new LinkedList<>();
        HashSet<Node> set = new HashSet<>();
        stack.add(start);
        set.add(start);
        while (!stack.isEmpty()){
            Node cur = stack.poll();
            System.out.println(cur.value);
            for (Node next: cur.nexts) {
                if(!set.contains(next)){
                    stack.add(next);
                    set.add(next);
                }
            }
        }
    }
}
