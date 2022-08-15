package rumeng.class06;

public class TraversalBinaryTree {

    public static class Node {
        public int value;
        public Node left;
        public Node right;
        public Node(int v){
            this.value = v;
        }
    }

    public static void f(Node head){
        if(head == null){
            return;
        }
        /*
        前序遍历
        System.out.println(head.value);
         */
        f(head.left);
        /*
        中序遍历
        System.out.println(head.value);
         */
        f(head.right);
        /*
        后序遍历
        System.out.println(head.value);
         */
    }

    public static void pre(Node head){
        if(head == null){
            return;
        }
        System.out.println(head.value);
        pre(head.left);
        pre(head.right);
    }

    public static void in(Node head){
        if(head == null){
            return;
        }
        in(head.left);
        System.out.println(head.value);
        in(head.right);
    }

    public static void pos(Node head){
        if(head == null){
            return;
        }
        pos(head.left);
        pos(head.right);
        System.out.println(head.value);
    }


}
