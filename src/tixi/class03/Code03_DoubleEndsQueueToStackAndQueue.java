package tixi.class03;

public class Code03_DoubleEndsQueueToStackAndQueue {

    public static class Node<T> {
        public T value;
        public Node<T> next;
        public Node<T> last;

        public Node(T value) {
            this.value = value;
        }
    }

    public static class DoubleEndsQueue<T> {
        public Node<T> head;
        public Node<T> tail;

        public void addFromHead(T value) {
            Node<T> cur = new Node<>(value);
            if (head == null) {
                head = cur;
                tail = cur;
            } else {
                cur.next = head;
                head.last = cur;
                head = cur;
            }
        }

        public void addFromBottom(T value) {
            Node<T> cur = new Node<>(value);
            if (tail == null) {
                head = cur;
                tail = cur;
            } else {
                tail.next = cur;
                cur.last = tail;
                tail = cur;
            }
        }

        public T popFromHead() {
            if (head == null) {
                return null;
            }
            Node<T> cur = head;
            if (head == tail) {
                head = null;
                tail = null;
            } else {
                head = head.next;
                head.last = null;
                cur.next = null;
            }
            return cur.value;
        }

        public T popFromBottom() {
            if (tail == null) {
                return null;
            }
            Node<T> cur = tail;
            if (head == tail) {
                head = null;
                tail = null;
            } else {
                tail = tail.last;
                tail.next = null;
                cur.last = null;
            }
            return cur.value;
        }

        public boolean isEmpty() {
            return head == null;
        }

        public static class MyStack<T> {
            public DoubleEndsQueue<T> queue;

            public MyStack() {
                queue = new DoubleEndsQueue<T>();
            }

            public void push(T value) {
                queue.addFromBottom(value);
            }

            public T pop() {
                return queue.popFromBottom();
            }

            public boolean isEmpty() {
                return queue.isEmpty();
            }
        }

        public static class MyQueue<T> {
            public DoubleEndsQueue<T> queue;

            public MyQueue() {
                queue = new DoubleEndsQueue<T>();
            }

            public void push(T val) {
                queue.addFromHead(val);
            }

            public T pop() {
                return queue.popFromBottom();
            }

            public boolean isEmpty() {
                return queue.isEmpty();
            }
        }
    }
}
