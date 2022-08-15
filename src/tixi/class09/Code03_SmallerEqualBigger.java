package tixi.class09;

import sun.misc.UCDecoder;

import java.util.List;

//将单向链表按某值划分成左边小、中间相等、右边大的形式
//9 -> 0 -> 4 -> 5 -> 1 pivot = 3
//
//进阶：如果链表长度为N,时间复杂度请达到O(N),额外空间复杂度请达到O(1)
public class Code03_SmallerEqualBigger {

    public static class ListNode {
        public int val;
        public ListNode next;

        public ListNode(int val) {
            this.val = val;
        }
    }

    public static ListNode listPartition1(ListNode head, int pivot) {
        if (head == null || head.next == null) {
            return head;
        }
        int len = 0;
        ListNode cur = head;
        while (cur != null) {
            len++;
            cur = cur.next;
        }
        ListNode[] nodeArr = new ListNode[len];
        cur = head;
        for (int i = 0; i < len; i++) {
            nodeArr[i] = cur;
            cur = cur.next;
        }
        arrPartition(nodeArr, pivot);
        for (int i = 0; i < nodeArr.length - 1; i++) {
            nodeArr[i].next = nodeArr[i + 1];
        }
        nodeArr[nodeArr.length - 1].next = null;
        return nodeArr[0];
    }

    private static void swap(ListNode[] nodes, int i, int j) {
        ListNode temp = nodes[j];
        nodes[j] = nodes[i];
        nodes[i] = temp;
    }

    public static void arrPartition(ListNode[] nodeArr, int pivot) {
        int lessP = -1;
        int moreP = nodeArr.length;
        int index = 0;
        while (index < moreP) {
            if (nodeArr[index].val < pivot) {
                swap(nodeArr, ++lessP, index++);
            } else if (nodeArr[index].val == pivot) {
                index++;
            } else {
                swap(nodeArr, --moreP, index);
            }
        }
    }

    public static ListNode listPartition2(ListNode head, int pivot) {

        if (head == null || head.next == null) {
            return head;
        }

        ListNode lessPH = null;
        ListNode equalPH = null;
        ListNode morePH = null;
        ListNode lessPT = null;
        ListNode equalPT = null;
        ListNode morePT = null;
        ListNode cur = head;
        ListNode next =null;
        while (cur != null) {
            next = cur.next;
            cur.next = null;
            if (cur.val < pivot) {
                if (lessPH == null) {
                    lessPH = cur;
                    lessPT = cur;
                } else {
                    lessPT.next = cur;
                    lessPT = lessPT.next;
                }
            } else if (cur.val == pivot) {
                if (equalPH == null) {
                    equalPH = cur;
                    equalPT = cur;
                } else {
                    equalPT.next = cur;
                    equalPT = equalPT.next;
                }
            } else {
                if (morePH == null) {
                    morePH = cur;
                    morePT = cur;
                } else {
                    morePT.next = cur;
                    morePT = morePT.next;
                }
            }
            cur = next;
        }
        if (lessPT != null) {
            lessPT.next = equalPH;
            equalPT = equalPT == null ? lessPT : equalPT;
        }

        if (equalPT != null) {
            equalPT.next = morePH;
        }
        return lessPH != null ? lessPH : (equalPH != null ? equalPH : morePH);
    }

    public static void printLinkedList(ListNode head) {
        while (head.next != null) {
            System.out.print(head.val + " -> ");
            head = head.next;
        }
        System.out.println(head.val);
        System.out.println();
    }

    public static void main(String[] args) {
        ListNode head1 = new ListNode(7);
        head1.next = new ListNode(9);
        head1.next.next = new ListNode(1);
        head1.next.next.next = new ListNode(8);
        head1.next.next.next.next = new ListNode(5);
        head1.next.next.next.next.next = new ListNode(2);
        head1.next.next.next.next.next.next = new ListNode(5);

        printLinkedList(head1);

        //head1 = listPartition1(head1, 4);
        head1 = listPartition2(head1, 5);
        printLinkedList(head1);
    }
}
