package tixi.class10;

import java.util.Currency;
import java.util.Stack;

public class Code03_UnRecursiveTraversalBT {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    //设立一个栈，先将head放入栈中，然后弹出
    //先将head.right放入栈中，再将head.left放入栈中，确保弹出的时候是左右的顺序
    //再将弹出的节点的值打印，并将它的右左节点放入栈中
    public static void pre(Node head) {
        System.out.print("pre-order: ");
        if (head != null) {
            Node cur = head;
            Stack<Node> stack = new Stack<>();
            stack.push(head);
            while (!stack.isEmpty()) {
                cur = stack.pop();
                System.out.print(cur.value + " ");
                if (cur.right != null) {
                    stack.push(cur.right);
                }
                if (cur.left != null) {
                    stack.push(cur.left);
                }
            }
        }
        System.out.println();
    }

    //中序遍历：左头右，stack先压入左节点，压无可压，依次弹出的过程中。
    // 对每一个节点压入右节点，然后继续重复上述过程，直到栈不为空或者当前节点不为null
    //设立一个栈，先将该节点放入栈中，然后将该节点左节点依次放入栈中
    //直到没有左节点，则打印该节点的值，并将该节点的右节点放入栈中
    //循环重复以上的过程,直到栈不为空或者节点不为空
    public static void in(Node head) {
        System.out.print("in-order:");
        if (head != null) {
            Node cur = head;
            Stack<Node> stack = new Stack<>();
            while (!stack.isEmpty() || cur != null) {
                if (cur != null) {
                    stack.push(cur);
                    cur = cur.left;
                } else {
                    cur = stack.pop();
                    System.out.print(cur.value + " ");
                    cur = cur.right;
                }
            }
        }
        System.out.println();
    }

    //后序遍历：左右头，先遍历左，再遍历右，最后遍历头。
    //先序遍历，头左右，反一下，头右左
    // 设立两个栈，第一个栈是为了头右左的出栈顺序，进入到第二个栈，然后出栈就是左右头
    public static void pos1(Node head) {
        System.out.print("pos1-order:");
        if (head != null) {
            Stack<Node> stack1 = new Stack<>();
            Stack<Node> stack2 = new Stack<>();
            Node cur = head;
            stack1.push(cur);
            while (!stack1.isEmpty()) {
                cur = stack1.pop();
                stack2.push(cur);
                if (cur.left != null) {
                    stack1.push(cur.left);
                }
                if (cur.right != null) {
                    stack1.push(cur.right);
                }
            }
            while (!stack2.isEmpty()) {
                cur = stack2.pop();
                System.out.print(cur.value + " ");
            }
        }
        System.out.println();
    }

    //后续遍历：左右中，只用一个栈搞定
    //声明两个变量，一个变量c指向当前弹出的节点，另一个变量h接受当前栈顶的值
    //当前栈不断压入当前树的左节点，直到左节点没有，打印当前栈顶，h保存弹出栈顶的值
    //然后c保存当前栈顶的节点，当前节点必然是h节点的父亲，为了确保不继续压入h节点,需要判断c.left != h
    //然后压入c节点的右树。假设c节点右树只有一个节点。
    //h接收当前栈顶弹出来右树节点，又得确保当前栈不压入右节点，因此需要条件c.right != h
    //重复上述过程，直到栈空
    public static void pos2(Node head) {
        System.out.print("pos1-order:");
        if (head != null) {
            Node h = head;
            Node c = head;
            Stack<Node> stack = new Stack<>();
            stack.push(h);
            while (!stack.isEmpty()) {
                c = stack.peek();
                if(c.left!=null && h!=c.left && h!=c.right){
                    stack.push(c.left);
                } else if(c.right !=null && h!=c.right){
                    stack.push(c.right);
                } else {
                    System.out.print(stack.pop().value+" ");
                    h = c;
                }
            }
        }
        System.out.println();
    }


    //    pre-order: 1 2 4 5 3 6 7
//            ========
//    in-order: 4 2 5 1 6 3 7
//            ========
//    pos-order: 4 5 2 6 7 3 1
//            ========
//    pos-order: 4 5 2 6 7 3 1
//            ========
    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.right.left = new Node(6);
        head.right.right = new Node(7);

        pre(head);
        System.out.println("========");
        in(head);
        System.out.println("========");
        pos1(head);
        System.out.println("========");
        pos2(head);
        System.out.println("========");
    }
}
