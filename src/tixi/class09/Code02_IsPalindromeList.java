package tixi.class09;

//请判断一个链表是否为回文链表。
//
//        示例 1:
//
//        输入: 1->2
//        输出: false
//        示例 2:
//
//        输入: 1->2->2->1
//        输出: true
//        进阶：
//        你能否用 O(n) 时间复杂度和 O(1) 空间复杂度解决此题？
//
//        来源：力扣（LeetCode）
//        链接：https://leetcode-cn.com/problems/palindrome-linked-list
//        著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
//        ————————————————
//        版权声明：本文为CSDN博主「有理想的番茄」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
//        原文链接：https://blog.csdn.net/weixin_43777983/article/details/93597011

import java.util.List;
import java.util.Stack;

public class Code02_IsPalindromeList {

    public static class ListNode {
        public int val;
        public ListNode next;

        public ListNode(int val) {
            this.val = val;
        }
    }

    public static boolean isPalindrome1(ListNode head) {

        if (head == null || head.next == null) {
            return true;
        }

        Stack<ListNode> stack = new Stack<>();
        ListNode cur = head;
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }
        cur = head;
        while (cur != null) {
            if (cur.val != stack.pop().val) {
                return false;
            }
            cur = cur.next;
        }
        return true;
    }

    public static boolean isPalindromeByR(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }

        Stack<ListNode> stack = new Stack<>();
        ListNode slow = head.next;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        while (slow != null) {
            stack.push(slow);
            slow = slow.next;
        }
        while (!stack.isEmpty()) {
            if (head.val != stack.pop().val) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    public static boolean isPalindromeByL(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }

        Stack<ListNode> stack = new Stack<>();
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            stack.push(slow);
            slow = slow.next;
            fast = fast.next.next;
        }
        if (fast.next != null) {
            stack.push(slow);
        }
        slow = slow.next;
        while (!stack.isEmpty()) {
            if (slow.val != stack.pop().val) {
                return false;
            }
            slow = slow.next;
        }
        return true;
    }

    public static boolean isPalindromeBy3(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        fast = reverseLinkedList(slow);
        //printLinkedList(head);
        //printLinkedList(fast);
        while (head != null) {
            if (head.val != fast.val) {
                return false;
            }
            fast = fast.next;
            head = head.next;
        }
        return true;
    }

    public static ListNode reverseLinkedList(ListNode head) {
        ListNode cur = head;
        ListNode pre = null;
        ListNode next = null;
        while (cur != null) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    public static boolean isPalindrombyR12(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        Stack<ListNode> stack = new Stack<>();
        slow = slow.next;
        while (slow != null) {
            stack.add(slow);
            slow = slow.next;
        }
        while (!stack.isEmpty()) {
            if (head.val != stack.pop().val) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    public static void printLinkedList(ListNode head) {
        while (head != null) {
            System.out.print(head.val + " -> ");
            head = head.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(2);
        head.next.next.next = new ListNode(1);

        ListNode test1 = new ListNode(1);
        test1.next = new ListNode(1);

        boolean test = isPalindromeBy3(head);
        System.out.println(test);
    }

}
