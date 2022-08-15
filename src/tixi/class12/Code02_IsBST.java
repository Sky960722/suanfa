package tixi.class12;

import java.util.ArrayList;
import java.util.List;

//给定一个二叉树，判断它是否是二叉查询树。
//1、左节点及以下节点的值比它小；2、右节点及以下节点的值比它大。当然，前提是子节点都存在的情况。
public class Code02_IsBST {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    //中序遍历访问每一个节点的值，然后放在ArrayList中，，判断ArrayList是否升序排列的
    public static boolean isBST1(Node head) {
        if (head == null) {
            return true;
        }
        List<Integer> list = new ArrayList<>();
        inOrder(head, list);
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i) > list.get(i + 1)) {
                return false;
            }
        }
        return true;
    }

    public static void inOrder(Node head, List<Integer> list) {
        if (head == null) {
            return;
        }
        inOrder(head.left, list);
        list.add(head.value);
        inOrder(head.right, list);
    }

    //搜索二叉数中任一一个节点的左树都比他小，右树都比他大，
    //1.首先收集信息，左树的最大值max，右树的最大值min，左右两树是否是BST
    //2.左树是BST，右树是BST，左树最大值max<val,右树min>val，则当前树是BST
    public static boolean isBST2(Node head) {
        if (head == null) {
            return true;
        }
        return process(head).isBST;
    }

    public static class Info {
        public boolean isBST;
        public Integer max;
        public Integer min;

        public Info(boolean isBST, Integer max, Integer min) {
            this.isBST = isBST;
            this.max = max;
            this.min = min;
        }
    }

    public static Info process(Node head) {
        if (head == null) {
            return new Info(true, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        boolean isBST = true;
        int max = Math.max(Math.max(leftInfo.max, rightInfo.max), head.value);
        int min = Math.min(Math.min(leftInfo.min, rightInfo.min), head.value);
        if (!leftInfo.isBST || !rightInfo.isBST) {
            isBST = false;
        }
        if (leftInfo.max > head.value || rightInfo.min < head.value) {
            isBST = false;
        }
        return new Info(isBST, max, min);
    }


    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static void inOrder(Node head) {
        if (head == null) {
            System.out.print(null + " ");
            return;
        }
        System.out.print(head.value + " ");
        inOrder(head.left);
        inOrder(head.right);
    }

    public static void main(String[] args) {

        int testTimes = 1000;
        int maxLevel = 5;
        int maxValue = 100;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel,maxValue);
            if(isBST1(head) != isBST2(head)){
                inOrder(head);
                System.out.println("失败");
            }
        }
        System.out.println("成功");
    }

}
