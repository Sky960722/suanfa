package tixi.class03;

import java.util.Stack;

//栈实现队列
public class Code06_TwoStacksImplementQueue {
    public static class MyQueue{
        private Stack<Integer> stackPush;
        private Stack<Integer> stackPop;

        public void add(int val){
            this.stackPush.push(val);
            this.pushToPop();
        }

        public int pop(){
            if(this.stackPush.isEmpty() && this.stackPop.isEmpty()){
                throw new RuntimeException("Your Queue is Empty");
            }
            this.pushToPop();
            return stackPop.pop();
        }

        public int peek(){
            if(this.stackPush.isEmpty() && this.stackPop.isEmpty()){
                throw new RuntimeException("Your Queue is Empty");
            }
            this.pushToPop();
            return stackPop.peek();
        }

        private void pushToPop() {
            if(stackPop.isEmpty()){
//                for (Integer num : stackPush) {
//                    stackPush.pop();
//                    stackPop.push(num);
//                }
                if(stackPop.isEmpty()){
                    while (!stackPush.isEmpty()){
                        stackPop.push(stackPush.pop());
                    }
                }
            }
        }


    }
}
