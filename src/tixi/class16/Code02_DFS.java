package tixi.class16;

import tixi.class16.Graph.Node;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class Code02_DFS {

    //从node出发，深度遍历
    public static void DFS(Node node){
        Stack<Node> stack = new Stack<>();
        Set<Node> set = new HashSet<>();
        stack.add(node);
        set.add(node);
        System.out.println(node.value);
        while (!stack.isEmpty()){
            Node cur = stack.pop();
            for (Node next:cur.nexts){
                if(!set.contains(next)){
                    stack.add(cur);
                    stack.add(next);
                    set.add(next);
                    System.out.println(next.value);
                    break;
                }
            }
        }
    }


}
