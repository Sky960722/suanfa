package tixi.class03;

import java.util.LinkedList;
import java.util.Queue;

//队列实现栈
public class Code07_TwoQueueImplementStack {
    public static class TwoQueueStack<T>{
        private Queue<T> queue;
        private Queue<T> help;

        public TwoQueueStack(){
            queue = new LinkedList<T>();
            help = new LinkedList<T>();
        }

        public void push(T val){
            queue.offer(val);
        }

        public T poll(){
            if(queue.isEmpty()){
                throw new RuntimeException("This stack is empty");
            }
            while (queue.size() > 1){
                help.offer(queue.poll());
            }
            T ans =queue.poll();
            Queue<T> tmp = queue;
            queue = help;
            help = tmp;
            return ans;
        }

        public T peek(){
            if(queue.isEmpty()){
                throw new RuntimeException("This stack is empty");
            }
            while (queue.size() > 1){
                help.offer(queue.poll());
            }
            T ans = queue.peek();
            help.offer(ans);
            Queue<T> tmp = queue;
            queue = help;
            help = tmp;
            return ans;
        }

        public boolean isEmpty(){
            return this.queue.isEmpty();
        }
    }
}
