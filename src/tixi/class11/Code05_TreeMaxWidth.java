package tixi.class11;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Code05_TreeMaxWidth {

    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    //1.用哈希表存储当前层数
    //2.声明一个队列，按层遍历
    //3.每弹出一个节点，如果当前节点的层数等于当前层数，widh++,否则，就是到下层
    public static int maxWidthUseMap(Node head) {
        if (head == null) {
            return 0;
        }
        Queue<Node> queue = new LinkedList<>();
        Map<Node, Integer> nodeMap = new HashMap<>();
        queue.add(head);
        nodeMap.put(head, 1);
        Node cur = null;
        int max = 1;
        int levelWidth = 0;
        int curLevel = 1;
        while (!queue.isEmpty()) {
            cur = queue.poll();
            int curNodeLevel = nodeMap.get(cur);
            if (cur.left != null) {
                queue.add(cur.left);
                nodeMap.put(cur.left, curNodeLevel + 1);
            }
            if (cur.right != null) {
                queue.add(cur.right);
                nodeMap.put(cur.right, curNodeLevel + 1);
            }
            if (curNodeLevel == curLevel) {
                levelWidth++;
            } else {
                max = Math.max(max, levelWidth);
                levelWidth = 1;
                curLevel++;
            }
        }
        max = Math.max(max, levelWidth);
        return max;
    }

    //curEnd和nextEnd记录当前节点
    public static int maxWidthNoMap(Node head) {
        if (head == null) {
            return 0;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        Node curEnd = head;
        Node nextEnd = null;
        Node cur = null;
        int max = 1;
        int levelWidth = 0;
        while (!queue.isEmpty()) {
            cur = queue.poll();
            if (cur.left != null) {
                queue.add(cur.left);
                nextEnd = cur.left;
            }
            if (cur.right != null) {
                queue.add(cur.right);
                nextEnd = cur.right;
            }
            if (cur == curEnd) {
                curEnd = nextEnd;
                max = Math.max(max, levelWidth);
                levelWidth = 0;
            }
            levelWidth++;
        }
        return max;
    }


    public static Node generateRandomBST(int maxLevel,int maxValue){
        return generate(1,maxLevel,maxValue);
    }

    public static Node generate(int level,int maxLevel,int maxValue){
        if(level > maxLevel || Math.random()<0.5){
            return null;
        }
        Node head = new Node((int) (Math.random()*maxValue));
        head.left = generate(level+1,maxLevel,maxValue);
        head.right = generate(level+1,maxLevel,maxValue);
        return head;
    }

    public static void Preprint(Node head){
        if(head == null){
            return;
        }
        System.out.print(head.value+" ");
        Preprint(head.left);
        Preprint(head.right);
    }

    public static void Inprint(Node head){
        if(head == null){
            return;
        }

        Inprint(head.left);
        System.out.print(head.value+" ");
        Inprint(head.right);
    }

    public static void main(String[] args) {
        int testTimes = 1000;
        int maxLevel = 5;
        int maxValue = 100;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel,maxValue);
            if(maxWidthNoMap(head) != maxWidthUseMap(head)){
                System.out.println("有问题");
            }
        }
        System.out.println("finish!");
    }

    public static int maxWidthUseMap1(Node head) {
        if (head == null) {
            return 0;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        // key 在 哪一层，value
        HashMap<Node, Integer> levelMap = new HashMap<>();
        levelMap.put(head, 1);
        int curLevel = 1; // 当前你正在统计哪一层的宽度
        int curLevelNodes = 0; // 当前层curLevel层，宽度目前是多少
        int max = 0;
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            int curNodeLevel = levelMap.get(cur);
            if (cur.left != null) {
                levelMap.put(cur.left, curNodeLevel + 1);
                queue.add(cur.left);
            }
            if (cur.right != null) {
                levelMap.put(cur.right, curNodeLevel + 1);
                queue.add(cur.right);
            }
            if (curNodeLevel == curLevel) {
                curLevelNodes++;
            } else {
                max = Math.max(max, curLevelNodes);
                curLevel++;
                curLevelNodes = 1;
            }
        }
        max = Math.max(max, curLevelNodes);
        return max;
    }

}
