package tixi.class11;

import java.util.ArrayList;
import java.util.List;

//将N叉数转变为二叉树
public class Code03_EncodeNaryTreeToBinaryTree {
    public static class Node{
        int val;
        public List<Node> children;
        public Node(){};
        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    class codec{
        public TreeNode encode(Node head){
            if(head == null){
                return null;
            }
            TreeNode h1 = new TreeNode(head.val);
            h1.left = en(head.children);
            return h1;
        }

        public TreeNode en(List<Node> children){
            TreeNode head = null;
            TreeNode cur = null;
            for (Node child : children) {
                TreeNode tNode = new TreeNode(child.val);
                if(head == null){
                    head = tNode;
                }else {
                   cur.right = tNode;
                }
                cur = tNode;
                cur.left = en(child.children);
            }
            return head;
        }

        public Node decode(TreeNode root){
            if(root == null){
                return null;
            }
            Node head = new Node(root.val,de(root.left));
            return head;
        }

        public List<Node> de(TreeNode root){
            List<Node> nodeList = new ArrayList<>();
            TreeNode cur = root;
            while (cur != null){
                Node n1 = new Node(cur.val,de(cur.left));
                nodeList.add(n1);
                cur = cur.right;
            }
            return nodeList;
        }
    }
}
