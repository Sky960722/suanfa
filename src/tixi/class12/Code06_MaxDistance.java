package tixi.class12;

import java.util.*;

//https://leetcode.cn/problems/diameter-of-binary-tree/
//二叉树的直径
//        给定一棵二叉树，你需要计算它的直径长度。一棵二叉树的直径长度是任意两个结点路径长度中的最大值。这条路径可能穿过也可能不穿过根结点。
public class Code06_MaxDistance {
    //思路1
    //1.返回这个节点左树的maxDIstance和高度，右树的maxDistance和高度
    //2.比较左树的maxDistance和右树的maxDistance，
    //3.拿maxDistance和左heigh+右h+1,比较，返回该节点的maxDistance

    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int v) {
            val = v;
        }
    }

    public static int diameterOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return process(root).maxDistance;
    }

    public static class Info {
        public int maxDistance;
        public int height;

        public Info(int maxDistance, int height) {
            this.maxDistance = maxDistance;
            this.height = height;
        }
    }

    public static Info process(TreeNode root) {
        if (root == null) {
            return new Info(0, 0);
        }
        Info leftInfo = process(root.left);
        Info rightInfo = process(root.right);
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        int maxDistance = Math.max(
                Math.max(leftInfo.maxDistance, rightInfo.maxDistance),
                leftInfo.height + rightInfo.height + 1);
        return new Info(maxDistance, height);
    }

    //思路二
    //暴力求解每两个节点之间的距离
    //用hashMap记录子父节点之间的关系
    //用ArrayList记录前序遍历的节点顺序，然后做双层循环，求每个节点之间的disTance
    public static int maxDistance1(TreeNode head) {
        if (head == null) {
            return 0;
        }
        ArrayList<TreeNode> arr = getPrelist(head);
        HashMap<TreeNode, TreeNode> parentMap = getParentMap(head);
        int max = 0;
        for (int i = 0; i < arr.size(); i++) {
            for (int j = i; j < arr.size(); j++) {
                max = Math.max(max, distance(parentMap, arr.get(i), arr.get(j)));
            }
        }
        return max;
    }

    public static ArrayList<TreeNode> getPrelist(TreeNode head) {
        ArrayList<TreeNode> arr = new ArrayList<>();
        fillPrelist(head, arr);
        return arr;
    }

    public static void fillPrelist(TreeNode head, ArrayList<TreeNode> arr) {
        if (head == null) {
            return;
        }
        arr.add(head);
        fillPrelist(head.left, arr);
        fillPrelist(head.right, arr);
    }

    public static HashMap<TreeNode, TreeNode> getParentMap(TreeNode head) {
        HashMap<TreeNode, TreeNode> map = new HashMap<>();
        map.put(head, null);
        fillParentMap(head, map);
        return map;
    }

    public static void fillParentMap(TreeNode head, HashMap<TreeNode, TreeNode> parentMap) {
        if (head.left != null) {
            parentMap.put(head.left, head);
            fillParentMap(head.left, parentMap);
        }
        if (head.right != null) {
            parentMap.put(head.right, head);
            fillParentMap(head.right, parentMap);
        }
    }


    //路径就是他们往上找相遇的第一个父亲节点
    //声明一个map，记录n1向上直到根节点，经过几个节点，自己算0
    //n2首先再map的k中，说明n2是n1的根节点，直接返回
    //n2不在map的k中，n2向上找寻每个根节点再map中的情况
    public static int distance(Map<TreeNode, TreeNode> parentMap, TreeNode o1, TreeNode o2) {
        HashSet<TreeNode> o1Set = new HashSet<>();
        TreeNode cur = o1;
        o1Set.add(cur);
        while (parentMap.get(cur) != null) {
            cur = parentMap.get(cur);
            o1Set.add(cur);
        }
        cur = o2;
        while (!o1Set.contains(cur)) {
            cur = parentMap.get(cur);
        }
        TreeNode lowestAncestor = cur;
        cur = o1;
        int distance1 = 1;
        while (cur != lowestAncestor) {
            cur = parentMap.get(cur);
            distance1++;
        }
        cur = o2;
        int distance2 = 1;
        while (cur != lowestAncestor) {
            cur = parentMap.get(cur);
            distance2++;
        }
        return distance1 + distance2 - 1;
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

        TreeNode test = new TreeNode(1);
        test.left = new TreeNode(2);
        test.right = new TreeNode(3);
        test.left.left = new TreeNode(4);
        test.left.right = new TreeNode(5);
        int cou = maxDistance1(test);
        int cou1 = diameterOfBinaryTree(test);
        System.out.println(cou);
        System.out.println(cou1);
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            TreeNode head = generateRandomBST(maxLevel, maxValue);
            if (diameterOfBinaryTree(head) != maxDistance1(head)) {
                System.out.println("出错了！");
            }
        }
        System.out.println("测试结束");
    }
}
