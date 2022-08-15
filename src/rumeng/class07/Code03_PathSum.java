package rumeng.class07;

public class Code03_PathSum {

    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    public static boolean isSum = false;

    public static boolean hasPathSum(TreeNode root, int sum) {
        if (root == null) {
            return false;
        }
        isSum = false;
        process(root, 0, sum);
        return isSum;
    }

    public static void process(TreeNode x, int preSum, int sum) {
        if(x.left == null && x.right == null){
            if(preSum + x.val == sum){
                isSum = true;
            }
            return;
        }
        preSum += sum;
        if(x.left != null){
            process(x.left,preSum,sum);
        }
        if(x.right != null){
            process(x.right,preSum,sum);
        }

    }


}
