package tixi.class09;

import java.util.HashMap;

//复制带随机链表的指针
//https://leetcode.cn/problems/copy-list-with-random-pointer/
public class Code04_CopyListWithRandom {
    public static class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    public static Node copyRandomList1(Node head) {
        if (head == null) {
            return null;
        }

        HashMap<Node, Node> nodeMap = new HashMap<>();
        Node cur = head;
        Node tmp = null;
        while (cur != null) {
            tmp = deepCopyNodeWithOutPointer(cur);
            nodeMap.put(cur, tmp);
            cur = cur.next;
        }

        cur = head;
        while (cur != null) {
            tmp = nodeMap.get(cur);
            tmp.next = nodeMap.getOrDefault(cur.next, null);
            tmp.random = nodeMap.getOrDefault(cur.random, null);
            cur = cur.next;
        }
        return nodeMap.getOrDefault(head, null);

    }

    private static Node deepCopyNodeWithOutPointer(Node node) {
        Node ans = new Node(node.val);
        return ans;
    }

    public static Node copyRandomList2(Node head) {

        if (head == null) {
            return head;
        }

        Node cur = head;
        Node next = null;
        Node tmp = null;
        while (cur != null) {
            next = cur.next;
            tmp = deepCopyNodeWithOutPointer(cur);
            cur.next = tmp;
            tmp.next = next;
            cur = next;
        }

        Node n1 = head;
        Node n2 = head.next;
        while (n1 != null) {
            tmp = n1.random;
            if (tmp != null) {
                n2.random = tmp.next;
            }
            n1 = n1.next.next;
            n2 = n2.next != null ? n2.next.next : null;
        }
        Node ans = head.next;
        n1 = head;
        n2 = head.next;
        while (n1 != null) {
            next = n1.next.next;
            n1.next = next;
            n2.next = next != null ? next.next:null;
            n1 = next;
            n2 = n1 != null ? n1.next : null;
        }
        return ans;
    }

    public static void printLinkedList(Node head) {
        while (head != null) {
            System.out.print(head.val + " -> ");
            head = head.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {

    }
}
