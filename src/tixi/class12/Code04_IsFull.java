package tixi.class12;

//是否是满二叉树
public class Code04_IsFull {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    //方法一：
    //1.收集整棵树的节点数n和高度x
    //满足n = 2^x - 1
    public static boolean isFull1(Node head) {
        if (head == null) {
            return true;
        }
        Info1 info = process1(head);
        return info.nodes == (int) (Math.pow(2, info.height) - 1);
    }

    public static class Info1 {
        public int height;
        public int nodes;

        public Info1(int height, int nodes) {
            this.height = height;
            this.nodes = nodes;
        }
    }

    public static Info1 process1(Node head) {
        if (head == null) {
            return new Info1(0, 0);
        }
        Info1 leftInfo = process1(head.left);
        Info1 rightInfo = process1(head.right);
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        int nodes = leftInfo.nodes + rightInfo.nodes + 1;
        return new Info1(height, nodes);
    }

    // 第二种方法
    // 收集子树是否是满二叉树
    // 收集子树的高度
    // 左树满 && 右树满 && 左右树高度一样 -> 整棵树是满的
    public static class Info2 {
        public boolean isFull;
        public int height;

        public Info2(boolean isFull, int height) {
            this.isFull = isFull;
            this.height = height;
        }
    }

    public static boolean isFull2(Node head) {
        if (head == null) {
            return true;
        }
        return process2(head).isFull;
    }

    public static Info2 process2(Node head) {
        if (head == null) {
            return new Info2(true, 0);
        }
        Info2 leftInfo = process2(head.left);
        Info2 rightInfo = process2(head.right);
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        boolean isFull = true;
        if (!leftInfo.isFull || !rightInfo.isFull || leftInfo.height != rightInfo.height) {
            isFull = false;
        }
        return new Info2(isFull, height);
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
            if (isFull1(test) != isFull2(test)) {
                System.out.println("失败");
            }
        }
        System.out.println("成功");
    }
}
