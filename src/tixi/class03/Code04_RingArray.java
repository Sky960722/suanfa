package tixi.class03;

public class Code04_RingArray {
    public static class MyQueue {
        public int[] arr;
        private int size;
        private int pushi;
        private int popi;
        private final int limit;

        public MyQueue(int limit) {
            arr = new int[limit];
            pushi = 0;
            popi = 0;
            size = 0;
            this.limit = limit;
        }

        public int nextIndex(int i) {
            return i < limit - 1 ? i + 1 : 0;
        }

        public void push(int value) {
            if (size == limit) {
                throw new RuntimeException("队列满了，不能在加了");
            }
            arr[pushi] = value;
            size++;
            pushi = nextIndex(pushi);

        }

        public int pop() {
            if (size == 0) {
                throw new RuntimeException("队列空了，不能再拿了");
            }
            int val = arr[popi];
            size--;
            popi = nextIndex(popi);
            return val;
        }

        public boolean isEmpty() {
            return size == 0;
        }
    }
}

