package rumeng.class06;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Code01_MergeKSortedLists {

    public static class ListNode {
        public int val;
        public ListNode next;
    }

    public static ListNode mergeLists(ListNode[] lists) {
        if (lists == null) {
            return null;
        }
        PriorityQueue<ListNode> heap = new PriorityQueue<>(new Comparator<ListNode>() {
            @Override
            public int compare(ListNode o1, ListNode o2) {
                return o1.val - o2.val;
            }
        });
        for (int i = 0; i < lists.length; i++) {
            if (lists[i] != null) {
                heap.add(lists[i]);
            }
        }

        if (heap.isEmpty()) {
            return null;
        }

        ListNode head = heap.poll();
        ListNode pre = head;
        while (!heap.isEmpty()) {
            ListNode cur = heap.poll();
            pre.next = cur;
            pre=cur;
            if(cur.next != null){
                heap.add(cur.next);
            }
        }
        return head;
    }
}
