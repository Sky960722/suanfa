package rumeng.class04;

public class Code01_ReverseList {

    public static class Node{
        public int value;
        public Node next;
        public Node(int data){
            value = data;
        }
    }

    public static class DoubleNode{
        public int value;
        public DoubleNode last;
        public DoubleNode next;

        public DoubleNode(int data){
            value = data;
        }
    }




    public static void main(String[] args) {

        Node n1 = new Node(1);
        Node temp =n1;
        n1.next = new Node(2);
        n1.next.next = new Node(3);
        while(temp != null){
            System.out.print(temp.value + " ");
            temp = temp.next;
        }
        System.out.println();
        System.out.println("==========================");
        n1 = reverseLinkedList(n1);
        while(n1 != null){
            System.out.print(n1.value + " ");
            n1 = n1.next;
        }
        System.out.println();
    }

    private static Node reverseLinkedList(Node head) {
        Node pre = null;
        Node next = null;
        while(head != null){
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

    public static DoubleNode reverseDoubleList(DoubleNode head){
        DoubleNode pre = null;
        DoubleNode next = null;
        while(head != null){
            next = head.next;
            head.next = pre;
            head.last = next;
            pre = head;
            head = next;
        }
        return pre;
    }

}
