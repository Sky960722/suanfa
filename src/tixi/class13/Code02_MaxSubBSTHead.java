package tixi.class13;

import jdk.nashorn.internal.ir.CallNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Code02_MaxSubBSTHead {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }


    //1.首先判断当前节点是否是搜索二叉数，如果是，返回当前节点，不是，咋比较左右两节点
    public static Node maxSubBSTHead1(Node head) {
        if (head == null) {
            return null;
        }
        if (getBSTSize(head) != 0) {
            return head;
        }
        Node leftNode = maxSubBSTHead1(head.left);
        Node rightNode = maxSubBSTHead1(head.right);
        return getBSTSize(leftNode) >= getBSTSize(rightNode) ? leftNode : rightNode;
    }

    //返回当前搜索二叉数的总个数，不是则返回，用中序判断
    public static int getBSTSize(Node head) {
        ArrayList<Node> list = new ArrayList<>();
        inOrder(head, list);
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).value >= list.get(i + 1).value) {
                return 0;
            }
        }
        return list.size();
    }

    public static void inOrder(Node head, List<Node> list) {
        if (head == null) {
            return;
        }
        inOrder(head.left, list);
        list.add(head);
        inOrder(head.right, list);
    }


    //1.左树是否是搜索二叉树，右树是否是搜索二叉数
    //设置四个参数，分别是树的总个数，是否是搜索二叉树，当前树的最大搜索子树的头节点，当前树的最大搜索子树的个数,树的最大值和最小值
    //2.都是，左树的个数+右树的个数+1，头节点更新为当前，子树个数更新为当前总个数
    //3.有一个不是，则直接比较当前树的最大搜索子树的个数，然后更新为最大的那个，并且头节点也更新为最大的那个
    public static Node maxSubBSTHead2(Node head) {
        if (head == null) {
            return null;
        }
        return process(head).maxSubBSTNode;
    }

    public static Info process(Node head) {
        if (head == null) {
            return new Info(true, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, null);
        }
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        boolean isBST = false;
        int allNodes = leftInfo.allNodes + rightInfo.allNodes + 1;
        int subBSTNodes = 0;
        int max = Math.max(Math.max(leftInfo.max, rightInfo.max), head.value);
        int min = Math.min(Math.min(leftInfo.min, rightInfo.min), head.value);
        Node maxSubBSTNode = null;
        if (leftInfo.isBST && rightInfo.isBST && leftInfo.max < head.value && rightInfo.min > head.value) {
            subBSTNodes = allNodes;
            maxSubBSTNode = head;
            isBST = true;
        } else {
            subBSTNodes = leftInfo.subBSTNodes >= rightInfo.subBSTNodes ? leftInfo.subBSTNodes : rightInfo.subBSTNodes;
            maxSubBSTNode = leftInfo.subBSTNodes >= rightInfo.subBSTNodes ?
                    leftInfo.maxSubBSTNode : rightInfo.maxSubBSTNode;
            isBST = false;
        }
        return new Info(isBST,allNodes,subBSTNodes,max,min,maxSubBSTNode);

    }

    public static class Info {
        public boolean isBST;
        public int allNodes;
        public int subBSTNodes;
        public int max;
        public int min;
        public Node maxSubBSTNode;


        public Info(boolean isBST, int allNodes, int subBSTNodes, int max, int min, Node maxSubBSTNode) {
            this.isBST = isBST;
            this.allNodes = allNodes;
            this.subBSTNodes = subBSTNodes;
            this.max = max;
            this.min = min;
            this.maxSubBSTNode = maxSubBSTNode;
        }
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

    public static void main(String[] args) {
        int testTime1 = 100000;
        int maxV = 100;
        int maxH = 10;
        for (int i = 0; i < testTime1; i++) {
            Node test = generateRandomBST(maxH, maxV);
            if (maxSubBSTHead1(test) != maxSubBSTHead2(test)) {
                System.out.println("失败");
            }
        }
        System.out.println("成功");
    }

}
