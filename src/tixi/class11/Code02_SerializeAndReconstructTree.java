package tixi.class11;

import tixi.class07.HeapGreater;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Code02_SerializeAndReconstructTree {
    //前序遍历是中左右，后续遍历是左右中，这两个都可以序列化和反序列化
    //将二叉树中每个结点的空指针引出一个虚l
    //唯独中序遍历不行，左中右的顺序，有可能有两个值
    //例如{ null, 1, null, 2, null}
    //如下两棵树
    //     *         __2
    //     *        /
    //     *       1
    //     *       和
    //     *       1__
    //     *          \
    //     *           2
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    public static Queue<String> preSerial(Node head) {
        Queue<String> ans = new LinkedList<>();
        pres(head, ans);
        return ans;
    }

    public static void pres(Node head, Queue<String> ans) {
        if (head == null) {
            ans.add(null);
            return;
        }
        ans.add(String.valueOf(head.value));
        pres(head.left, ans);
        pres(head.right, ans);
    }

    public static Queue<String> inSerial(Node head) {
        Queue<String> ans = new LinkedList<>();
        ins(head, ans);
        return ans;
    }

    public static void ins(Node head, Queue<String> ans) {
        if (head == null) {
            ans.add(null);
        } else {
            ins(head.left, ans);
            ans.add(String.valueOf(head.value));
            ins(head.right, ans);
        }
    }

    public static Queue<String> posSerial(Node head) {
        Queue<String> ans = new LinkedList<>();
        poss(head, ans);
        return ans;
    }

    public static void poss(Node head, Queue<String> ans) {
        if (head == null) {
            ans.add(null);
        } else {
            poss(head.left, ans);
            poss(head.right, ans);
            ans.add(String.valueOf(head.value));
        }
    }

    public static Node buildByPreQueue(Queue<String> prelist) {
        if (prelist == null || prelist.size() == 0) {
            return null;
        }
        return preb(prelist);
    }

    public static Node preb(Queue<String> prelist) {
        String value = prelist.poll();
        if (value == null) {
            return null;
        }
        Node node = new Node(Integer.valueOf(value));
        node.left = preb(prelist);
        node.right = preb(prelist);
        return node;
    }

    public static Node buildByPosQueue(Queue<String> poslist) {
        if (poslist == null || poslist.size() == 0) {
            return null;
        }
        //后序遍历是左右中，相当于先将左树放入队列中，再将右树放入队列中，最后头节点放入队列中
        //声明一个栈，将队列中中弹出的结点顺序放入
        //则栈中先弹出头结点，再弹出右树，最后弹出左树，顺序是头右左
        //再根据preb的规律写
        Stack<String> stack = new Stack<>();
        while (!poslist.isEmpty()) {
            stack.push(poslist.poll());
        }
        Node head = posb(stack);
        return head;
    }

    private static Node posb(Stack<String> posstack) {
        String value = posstack.pop();
        if (value == null) {
            return null;
        }
        Node head = new Node(Integer.valueOf(value));
        head.right = posb(posstack);
        head.left = posb(posstack);
        return head;
    }
}
