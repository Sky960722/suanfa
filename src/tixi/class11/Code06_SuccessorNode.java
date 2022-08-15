package tixi.class11;

//后继节点:在中序遍历中节点的后一个节点叫该节点的后继节点
//设计一个算法，找出二叉搜索树中指定节点的“下一个”节点（也即中序后继）。
//如果指定节点没有对应的“下一个”节点，则返回null。
public class Code06_SuccessorNode {

    public static class Node{
        public int value;
        public Node left;
        public Node right;
        public Node parent;

        public Node(int data){
            this.value = data;
        }
    }

    //中序遍历：左中右
    //1.如果当前节点有右树，则后继节点一定是右子树的最左节点
    //2.如果当前节点没有右树
    //2.1则他的后继节点一定是他的根节点
    //2.2如果他是父节点是右节点，则说明他是后继节点的左子树的最右节点，一直网上找，直到找到左树的父节点，
    //也就是子父关系是左子父的关系
    //2.3如果他是父节点的左节点，则直接返回父节点
    //2.4找不到子父关系是左子父的关系，说明是当前整棵树的最右节点，返回null
    public static Node getSuccessorNode(Node node){
        if(node == null){
            return null;
        }
        if(node.right != null){
            return getMaxLeftNode(node.right);
        } else {
            return getMinLeftParent(node);
        }
    }

    public static Node getMaxLeftNode(Node root){
        while (root.left != null){
            root = root.left;
        }
        return root;
    }

    public static Node getMinLeftParent(Node node){
        Node cur = node;
        Node par = node.parent;
        /*
        while (cur.parent != null){
            par = cur.parent;
            if(par.left == cur){
                return par;
            }
            cur = cur.parent;
        }
        return null;
        */
        while (par != null && par.right == cur){
            cur = par;
            par = par.parent;
        }
        return par;
    }

    public static Node getSuccessorNode1(Node node){
        if(node == null){
            return null;
        }
        if(node.right != null){
            return getMaxLeftNode(node.right);
        } else {
            Node par = node.parent;
            while (par != null && par.right!=node){
                node = par;
                par = node.parent;
            }
            return par;
        }
    }

    public static void main(String[] args) {
        Node head = new Node(6);
        head.parent = null;
        head.left = new Node(3);
        head.left.parent = head;
        head.left.left = new Node(1);
        head.left.left.parent = head.left;
        head.left.left.right = new Node(2);
        head.left.left.right.parent = head.left.left;
        head.left.right = new Node(4);
        head.left.right.parent = head.left;
        head.left.right.right = new Node(5);
        head.left.right.right.parent = head.left.right;
        head.right = new Node(9);
        head.right.parent = head;
        head.right.left = new Node(8);
        head.right.left.parent = head.right;
        head.right.left.left = new Node(7);
        head.right.left.left.parent = head.right.left;
        head.right.right = new Node(10);
        head.right.right.parent = head.right;

        Node test = head.left.left;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.left.left.right;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.left;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.left.right;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.left.right.right;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.right.left.left;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.right.left;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.right;
        System.out.println(test.value + " next: " + getSuccessorNode(test).value);
        test = head.right.right; // 10's next is null
        System.out.println(test.value + " next: " + getSuccessorNode(test));
    }

}
