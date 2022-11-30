package tixi.class17;

import java.util.Stack;

public class Code05_ReverseStackUsingRecursive {

    //给你一个栈，请你逆序这个栈，不能申请额外的数据结构，只能使用递归函数。如何实现？
    //1.声明一个函数每次返回他栈底的元素
    //2.递归调用该函数，每次都收集他对应栈底的元素，直到栈空
    //3.栈空，则将沿途堆中弹出的元素再加入栈中
    public static void reverse(Stack<Integer> stack) {
        if(stack.isEmpty()){
            return;
        }
        int i = f(stack);
        reverse(stack);
        stack.push(i);
    }

    //思路
    //1.先将栈底的最后一个元素弹出
    //1.在每次递归调用该函数时，先将栈顶的元素取出，
    // 2.然后递归调用这个元素，直到栈空，弹出最后一个元素，
    // 3然后将最后一个元素弹出，并将沿途弹出的元素入栈
    public static int  f(Stack<Integer> stack){
        int res = stack.pop();
        if(stack.isEmpty()){
            return res;
        }
        int last = f(stack);
        stack.push(res);
        return last;
    }
}
