package rumeng.class04;

public class Code04_ReverseNodesInKGroup {

    public static class ListNode {
        public int value;
        public ListNode next;
    }

    public static ListNode getKGroupEnd(ListNode start, int k) {
        while ( k != 1 && start != null) {
            start = start.next;
            k--;
        }
        return start;
    }

    public static void reverse(ListNode start, ListNode end) {
        end = end.next;
        ListNode pre = null;
        ListNode cur = start;
        ListNode next = null;
        while (cur != end) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        start.next = end;
    }

    public static ListNode reverseKGroup(ListNode head, int k) {
        ListNode start = head;
        ListNode end = getKGroupEnd(start, k);
        if (end == null) {
            return head;
        }
        //凑齐第一组
        head = end; //将头节点指向第一个要反转的end节点
        reverse(start,end);
        //上一组的结尾节点
        ListNode lastEnd = start;
        while (lastEnd.next != null) {
            start = lastEnd.next;
            end = getKGroupEnd(start, k);
            if (end == null) {
                return head;
            }
            reverse(start, end);
            lastEnd.next = end;
            lastEnd = start;
        }
        return head;
    }
}
