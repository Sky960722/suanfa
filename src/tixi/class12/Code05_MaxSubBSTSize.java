package tixi.class12;

import java.util.ArrayList;
import java.util.List;

// 在线测试链接 : https://leetcode.com/problems/largest-bst-subtree
//给定一个二叉树，找到其中最大的二叉搜索树（BST）子树，其中最大指的是子树节点数最多的。注意:子树必须包含其所有后代。
public class Code05_MaxSubBSTSize {

    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int value) {
            val = value;
        }
    }


    //1.判断左树是否是二叉树，右树是否是二叉树
    //  如果左树是二叉数，右树是二叉树，
    //  //且满足左树的最大值小于val，右树的最小值大于val
    //  //则左树节点树+右树节点树+1，并将它记录到当前节点的最大子树个数
    //  //如果当前节点不是二叉树
    //  则将当前节点的左最大子树个数和右最大子树作比较，然后记录最大的
    //
    public static int largestBSTSubtree(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return process(root).subNodes;
    }

    public static class Info {
        public boolean isBST;
        public int subNodes;
        public int nodes;
        public int max;
        public int min;

        public Info(boolean isBST, int subNodes, int nodes, int max, int min) {
            this.isBST = isBST;
            this.subNodes = subNodes;
            this.nodes = nodes;
            this.max = max;
            this.min = min;
        }
    }

    public static Info process(TreeNode head) {
        if (head == null) {
            return new Info(true, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        int nodes = leftInfo.nodes + rightInfo.nodes + 1;
        int max = Math.max(Math.max(leftInfo.max, rightInfo.max), head.val);
        int min = Math.min(Math.min(leftInfo.min, rightInfo.min), head.val);
        int subNode = Math.max(leftInfo.subNodes, rightInfo.subNodes);
        boolean isBST = false;
        if (leftInfo.isBST && rightInfo.isBST) {
            if (leftInfo.max < head.val && rightInfo.min > head.val) {
                isBST = true;
                subNode = leftInfo.nodes + rightInfo.nodes + 1;
            }
        }
        return new Info(isBST, subNode, nodes, max, min);
    }

    //核对方法
    //1.对每个节点统计是否二叉树，如果是，返回个数，不是，则返回0
    //搜索二叉数中序遍历是单调上升的
    public static int right(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int allNodes = getBSTSize(root);
        if (allNodes != 0) {
            return allNodes;
        }
        return Math.max(right(root.left), right(root.right));

    }

    public static int getBSTSize(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        inOrder(root, list);
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i) >= list.get(i + 1)) {
                return 0;
            }
        }
        return list.size();
    }

    public static void inOrder(TreeNode root, List<Integer> list) {
        if (root == null) {
            return;
        }
        inOrder(root.left, list);
        list.add(root.val);
        inOrder(root.right, list);
    }

    public static TreeNode generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // 为了验证
    // 对数器方法
    public static TreeNode generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        TreeNode head = new TreeNode((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    // 为了验证
    // 对数器方法
    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            TreeNode head = generateRandomBST(maxLevel, maxValue);
            if (largestBSTSubtree(head) != right(head)) {
                System.out.println("出错了！");
            }
        }
        System.out.println("测试结束");
    }

}
