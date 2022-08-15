package tixi.class10;

//判断两个链表是否相交，并返回交点
public class Code01_FindFirstIntersectNode {

    public static class Node {
        public int value;
        public Node next;

        public Node(int data) {
            this.value = data;
        }
    }

    //设两个快慢指针，快指针是fast，慢指针是slow。快指针步行2，慢指针步行1
    // 起点与入环节点之间的距离是x，慢指针和快指针相遇的节点距离入环节点的一侧距离是y，另一侧距离是z，则环长度是r=y+z
    //快慢指针相遇的时候，快指针的路程必然是慢指针的两倍,2(x+y) = n(y+z)+x => x = (n-1)(y+z) + z
    //y+z又是r，可以知道慢指针从相遇节点出发，另一个指针从头节点出发，两者相遇就是x的距离、即是z的距离，即能得到入环节点
    //返回入环节点，如果不是入环链表，返回null
    public static Node getLoopNode(Node head) {
        if (head == null || head.next == null || head.next.next == null) {
            return null;
        }
        Node fast = head.next.next;
        Node slow = head.next;
        while (slow != fast) {
            if (fast.next == null) {
                return null;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        fast = head;
        while (fast != slow) {
            fast = fast.next;
            slow = slow.next;
        }
        return fast;
    }

    //两个无环链表，不相交，返回null
    //两个无环链表如果相交，end1 == end2，end1 != end2 则不相交，直接返回null
    //相交的话，两个无环链表相交的点是一样的，求出长链表和短链表节点个数的差值n
    //然后让长链表移动n个节点，在开始和短链表同时移动，长链表和短链表节点相等
    //就是相遇节点
    public static Node noLoop(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return null;
        }
        Node cur1 = head1;
        Node cur2 = head2;
        int n = 0;
        while (cur1.next != null) {
            n++;
            cur1 = cur1.next;
        }
        while (cur2.next != null) {
            n--;
            cur2 = cur2.next;
        }
        if (cur1 != cur2) {
            return null;
        }
        cur1 = n > 0 ? head1 : head2;
        cur2 = n > 0 ? head2 : head1;
        n = Math.abs(n);
        //长链表和短链表相差n个节点，如果相差1个节点，cur1就往下走一格
        while (n != 0) {
            n--;
            cur1 = cur1.next;
        }
        while (cur1 != cur2) {
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return cur1;
    }

    //如果入环节点相同，loop1 == loop2，则说明两个链表是在入环节点之前相遇
    //按照无环节点思路考虑即可，n统计的是两个链表到入环节点的个数
    //如果入环节点不同，需要判断的是两个链表是否入的同一个环，
    //只要对其中一个入环节点做遍历，能否遇到另一个入环节点，遇到，返回，没遇到，说明没相交
    public static Node Loop(Node head1, Node loop1, Node head2, Node loop2) {
        Node cur1 = head1;
        Node cur2 = head2;
        if (loop1 == loop2) {
            int n = 0;
            while (cur1 != loop1) {
                n++;
                cur1 = cur1.next;
            }
            while (cur2 != loop2) {
                n--;
                cur2 = cur2.next;
            }
            cur1 = n > 0 ? head1 : head2;
            cur2 = n > 0 ? head2 : head1;
            n = Math.abs(n);
            while (n > 0) {
                n--;
                cur1 = cur1.next;
            }
            while (cur1 != cur2){
                cur1 = cur1.next;
                cur2 = cur2.next;
            }
            return cur1;
        } else {
            cur1 = loop1.next;
            while (cur1 != loop1){
                if(cur1 == loop2){
                    return loop2;
                }
                cur1 = cur1.next;
            }
            return null;
        }
    }

    public static Node getIntersectNode(Node head1,Node head2){
        Node loop1 = getLoopNode(head1);
        Node loop2 = getLoopNode(head2);
        //两个都是无环链表
        if(loop1 == null && loop2 == null){
            Node ans = noLoop(head1,head2);
            return ans;
        }
        if(loop1 != null && loop2 != null){
            Node ans = Loop(head1,loop1,head2,loop2);
            return ans;
        }
        return null;
    }

    public static void main(String[] args) {
        // 1->2->3->4->5->6->7->null
        Node head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);

        // 0->9->8->6->7->null
        Node head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(getIntersectNode(head1, head2).value);

        // 1->2->3->4->5->6->7->4...
        head1 = new Node(1);
        head1.next = new Node(2);
        head1.next.next = new Node(3);
        head1.next.next.next = new Node(4);
        head1.next.next.next.next = new Node(5);
        head1.next.next.next.next.next = new Node(6);
        head1.next.next.next.next.next.next = new Node(7);
        head1.next.next.next.next.next.next = head1.next.next.next; // 7->4

        // 0->9->8->2...
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next; // 8->2
        System.out.println(getIntersectNode(head1, head2).value);

        // 0->9->8->6->4->5->6..
        head2 = new Node(0);
        head2.next = new Node(9);
        head2.next.next = new Node(8);
        head2.next.next.next = head1.next.next.next.next.next; // 8->6
        System.out.println(getIntersectNode(head1, head2).value);

    }
}
