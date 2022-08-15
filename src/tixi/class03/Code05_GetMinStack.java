package tixi.class03;

import java.util.Stack;

//实现一个特殊的栈,在实现栈的基础功能的基础上,再实现返回栈中最小元素的操作
public class Code05_GetMinStack {
    public static class MyStack1{
        private Stack<Integer> stackData;
        private Stack<Integer> stackMin;
        public MyStack1(){
            this.stackData = new Stack<Integer>();
            this.stackMin = new Stack<Integer>();
        }

        public int getMin(){
            if(this.stackMin.isEmpty()){
                throw new RuntimeException("Your stack is empty");
            }
            return this.stackMin.peek();
        }

        public void push(int newNum){
            this.stackData.push(newNum);
            if(this.stackMin.isEmpty()){
                this.stackMin.push(newNum);
            } else if(newNum <= this.getMin()){
                this.stackMin.push(newNum);
            }
        }

        public int pop(){
            if(this.stackData.isEmpty()){
                throw new RuntimeException("Your stack is empty");
            }
            int val = this.stackData.pop();
            if(val == this.getMin()){
                this.stackMin.pop();
            }
            return val;
        }
    }

    public static class Mystack2{
        private Stack<Integer> stackData;
        private Stack<Integer> stackMin;

        public int getMin(){
            if(this.stackMin.isEmpty()){
                throw new RuntimeException("Your stack is empty");
            }
            return this.stackMin.peek();
        }

        public void push(int newNum){
            if (this.stackMin.isEmpty()) {
                this.stackMin.push(newNum);
            } else if (newNum < this.getMin()) {
                this.stackMin.push(newNum);
            } else {
                int newMin = this.stackMin.peek();
                this.stackMin.push(newMin);
            }
            this.stackData.push(newNum);
        }

        public int pop(){
            if(this.stackData.isEmpty()){
                throw new RuntimeException("Your stack is empty");
            }
            this.stackMin.pop();
            return this.stackData.pop();
        }
    }
}
