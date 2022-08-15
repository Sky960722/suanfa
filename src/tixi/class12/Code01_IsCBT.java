package tixi.class12;



import java.util.LinkedList;
import java.util.Queue;

//二叉树的完全性检验
//        给定一个二叉树的 root ，确定它是否是一个 完全二叉树 。
//
//        在一个 完全二叉树 中，除了最后一个关卡外，所有关卡都是完全被填满的，并且最后一个关卡中的所有节点都是尽可能靠左的。它可以包含 1 到 2h 节点之间的最后一级 h 。
public class Code01_IsCBT {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode() {
        }

        public TreeNode(int val) {
            this.val = val;
        }
    }

    //按层遍历
    //1.当前节点只有右树，没有左树，返回false
    //2.当前节点只有左节点或者没有节点，则后续弹出节点均没有叶子节点
    public static boolean isCompleteTree1(TreeNode root) {
        if(root == null){
            return true;
        }
        TreeNode cur = root;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        boolean noNode = false;
        while (!queue.isEmpty()) {
            cur = queue.poll();
            if (cur.left == null && cur.right != null) {
                return false;
            }

            if (cur.left != null) {
                queue.add(cur.left);
            }
            if (cur.right != null) {
                queue.add(cur.right);
            }
            if (noNode && (cur.left != null || cur.right != null)) {
                return false;
            }
            if (cur.left == null || cur.right == null) {
                noNode = true;
            }
        }
        return true;
    }

    //深度递归
    //1.当前树如果是完全二叉树
    //2.则左树肯定是满二叉树
    //3.右树肯定是高度相同的完全二叉树
    //4.所以需要左树和右树的高度，高度相等，则接着判断左树是否是满二叉树，如果是，则右树是否是完全二叉树
    public static boolean isCompleteTree2(TreeNode root) {
        if (root == null) {
            return true;
        }
        return process(root).isCBT;
    }

    public static class Info {
        public boolean isFull;
        public boolean isCBT;
        public int height;

        public Info(boolean full, boolean cbt, int h) {
            isFull = full;
            isCBT = cbt;
            height = h;
        }
    }

    public static Info process(TreeNode x) {
        if (x == null) {
            return new Info(true, true, 0);
        }
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;

        boolean isFull = leftInfo.isFull
                && rightInfo.isFull
                && leftInfo.height == rightInfo.height;
        boolean isCBT = false;
        if (isFull) {
            isCBT = true;
        } else {
            if (leftInfo.isCBT && rightInfo.isCBT) {
                //1.左树是完全二叉树，右树是满树，则左树比右树+1
                if (leftInfo.isCBT
                        && rightInfo.isFull
                        && leftInfo.height == rightInfo.height + 1) {
                    isCBT = true;
                }
                //2.左树是满树，右树是满树，则左树高度比右树+1
                if (leftInfo.isFull
                        && rightInfo.isFull
                        && leftInfo.height == rightInfo.height + 1) {
                    isCBT = true;
                }
                //3.左树是满树，右树是完全二叉树，则左树高度和右树高度相等
                if (leftInfo.isFull
                        && rightInfo.isCBT
                        && leftInfo.height == rightInfo.height) {
                    isCBT = true;
                }
            }
        }
        return new Info(isFull, isCBT, height);
    }

    public static TreeNode generateRandomBST(int maxHeight, int maxValue) {

        return generate(1, maxHeight, maxValue);

    }

    public static TreeNode generate(int height, int maxHeight, int maxValue) {
        if (height > maxHeight || Math.random() < 0.5) {
            return null;
        }
        TreeNode head = new TreeNode((int) (Math.random() * maxValue));
        head.left = generate(height+1,maxHeight,maxValue);
        head.right = generate(height +1,maxHeight,maxValue);
        return head;
    }

//    // for test
//    public static TreeNode generateRandomBST1(int maxLevel, int maxValue) {
//        return generate1(1, maxLevel, maxValue);
//    }
//
//    // for test
//    public static TreeNode generate1(int level, int maxLevel, int maxValue) {
//        if (level > maxLevel || Math.random() < 0.5) {
//            return null;
//        }
//        TreeNode head = new TreeNode((int) (Math.random() * maxValue));
//        head.left = generate1(level + 1, maxLevel, maxValue);
//        head.right = generate1(level + 1, maxLevel, maxValue);
//        return head;
//    }

    public static void main(String[] args) {
        int testTimes = 10000;
        int maxHeight = 5;
        int maxValue = 100;
        for (int i = 0; i < testTimes; i++) {
            TreeNode testNode = generateRandomBST(maxHeight,maxValue);
            if(isCompleteTree1(testNode)!=isCompleteTree2(testNode)){
                System.out.println("失败");
            }
        }
        System.out.println("成功");
    }
}
