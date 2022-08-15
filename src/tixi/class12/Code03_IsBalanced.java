package tixi.class12;

import sun.security.krb5.Asn1Exception;

//平衡二叉树
//它是一棵空树或它的左右两个子树的高度差的绝对值不超过1，并且左右两个子树都是一棵平衡二叉树。
public class Code03_IsBalanced {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    //返回左树的高度，返回右树的高度，如果左树的高度与右树的高度相差超过1，则不是平衡二叉树
    public static boolean isBalanced1(Node head) {
        if (head == null) {
            return true;
        }
        Boolean[] ans = new Boolean[1];
        ans[0]= true;
        process1(head, ans);
        return ans[0];
    }

    public static int process1(Node head,  Boolean[] ans) {
        if (!ans[0] || head == null) {
            return -1;
        }
        int leftHeight = process1(head.left, ans);
        int rightHeight = process1(head.right, ans);
        if (Math.abs((leftHeight - rightHeight)) > 1) {
            ans[0] = false;
        }
        return Math.max(leftHeight, rightHeight) + 1;
    }

    //1.左树是否是平衡二叉树，右树是否是平衡二叉树
    //2.左树的高度，右树的高度
    public static boolean isBalanced2(Node head) {
        if (head == null) {
            return true;
        }
        return process2(head).isBal;
    }

    public static class Info {
        public boolean isBal;
        public int height;

        public Info(boolean isBal, int height) {
            this.isBal = isBal;
            this.height = height;
        }
    }

    public static Info process2(Node head) {
        if (head == null) {
            return new Info(true, 0);
        }
        Info leftInfo = process2(head.left);
        Info rightInfo = process2(head.right);
        boolean isBal = true;
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        if (Math.abs(leftInfo.height - rightInfo.height) > 1) {
            isBal = false;
        }
        if (!leftInfo.isBal || !rightInfo.isBal) {
            isBal = false;
        }
        return new Info(isBal, height);
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

        // 15 60 73
        Node test = new Node(15);
        test.left=new Node(60);
        test.left.left = new Node(73);
        System.out.println(isBalanced1(test));
        System.out.println(isBalanced2(test));

        int testTimes = 1000;
        int maxLevel = 5;
        int maxValue = 100;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel,maxValue);
            if(isBalanced1(head) != isBalanced2(head)){
                preOrder(head);
                System.out.println("失败");
            }
        }
        System.out.println("成功");
    }

    public static void preOrder(Node head) {
        if (head == null) {
            System.out.print(null + " ");
            return;
        }
        System.out.print(head.value + " ");
        preOrder(head.left);
        preOrder(head.right);
    }
}
