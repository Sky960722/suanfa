package tixi.class13;

import java.util.*;

//https://leetcode.cn/problems/lowest-common-ancestor-of-a-binary-tree/
//二叉树的最近公共祖先
//给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。
//百度百科中最近公共祖先的定义为：“对于有根树 T 的两个节点 p、q，最近公共祖先表示为一个节点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。”
public class Code03_lowestAncestor {

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            this.val = x;
        }
    }


    //1.左树有节点p，右树有节点q，左右树都有节点p，节点q，则直接返回当前节点
    public static TreeNode lowestCommonAncestor1(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }
        return process(root, p, q).ans;
    }

    public static class Info {
        public boolean hasP;
        public boolean hasQ;
        public TreeNode ans;

        public Info(boolean hasP, boolean hasQ, TreeNode ans) {
            this.hasP = hasP;
            this.hasQ = hasQ;
            this.ans = ans;
        }
    }

    public static Info process(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return new Info(false, false, null);
        }
        Info leftInfo = process(root.left, p, q);
        Info rightInfo = process(root.right, p, q);
        boolean hasP = root == p || leftInfo.hasP || rightInfo.hasP;
        boolean hasQ = root == q || leftInfo.hasQ || rightInfo.hasQ;
        TreeNode ans = null;
        if (leftInfo.ans != null) {
            ans = leftInfo.ans;
        } else if (rightInfo.ans != null) {
            ans = rightInfo.ans;
        } else {
            if (hasP && hasQ) {
                ans = root;
            }
        }
        return new Info(hasP, hasQ, ans);
    }

    //用hashMap记录对应的子父节点
    //p往上找的节点存储一个集合set，
    //q往上找，每次判断其父节点是否再集合set中，在就返回
    public static TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }
        Map<TreeNode, TreeNode> map = new HashMap<>();
        map.put(root, null);
        fillMap(root, map);

        return findAns(p, q, map);
    }

    public static TreeNode findAns(TreeNode p, TreeNode q, Map<TreeNode, TreeNode> map) {
        TreeNode c1 = p;
        TreeNode c2 = q;
        Set<TreeNode> set1 = new HashSet<>();
        while (c1 != null) {
            set1.add(c1);
            c1 = map.get(c1);
        }
        while (c2 != null) {
            if (set1.contains(c2)) {
                return c2;
            }
            c2 = map.get(c2);
        }
        return null;
    }

    public static void fillMap(TreeNode root, Map<TreeNode, TreeNode> map) {
        if (root == null) {
            return;
        }
        map.put(root.left, root);
        map.put(root.right, root);
        fillMap(root.left, map);
        fillMap(root.right, map);
    }

    public static TreeNode generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    public static TreeNode generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        TreeNode node = new TreeNode((int) (Math.random() * maxValue));
        node.left = generate(level + 1, maxLevel, maxValue);
        node.right = generate(level + 1, maxLevel, maxValue);
        return node;
    }

    public static TreeNode pickRandomOne(TreeNode root) {
        if(root == null){
            return null;
        }
        ArrayList<TreeNode> list = new ArrayList<>();
        fillList(root, list);
        int size = list.size();
        int random = (int) (Math.random() * size);
        return list.get(random);
    }

    public static void fillList(TreeNode root, ArrayList<TreeNode> list) {
        if (root == null) {
            return;
        }
        list.add(root);
        fillList(root.left, list);
        fillList(root.right, list);
    }

    public static void main(String[] args) {
        int testTimes = 10000;
        int maxLevel = 10;
        int maxValue = 100;
        for (int i = 0; i < testTimes; i++) {
            TreeNode test = generateRandomBST(10, 100);
            TreeNode p = pickRandomOne(test);
            TreeNode q = pickRandomOne(test);
            if (lowestCommonAncestor1(test, p, q) != lowestCommonAncestor2(test, p, q)) {
                System.out.println("失败");
            }
        }
        System.out.println("finish");
    }

}
