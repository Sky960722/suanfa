package tixi.class15;

import com.sun.org.apache.bcel.internal.generic.RET;

import javax.swing.*;
import java.sql.Statement;
import java.util.*;

//岛屿数量
//给你一个由'1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
//
//岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
//
//此外，你可以假设该网格的四条边均被水包围。
//
//示例 1：
//
//        输入：grid = [
//        ["1","1","1","1","0"],
//        ["1","1","0","1","0"],
//        ["1","1","0","0","0"],
//        ["0","0","0","0","0"]
//        ]
//        输出：1
//
//        来源：力扣（LeetCode）
//        链接：https://leetcode.cn/problems/number-of-islands
//        著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
public class Code02_NumberOfIslands {

    //思路一
    //感染
    //首先对当前数列遍历，凡是碰到陆地的，当前岛屿数量+1,然后将当前岛屿涉及的陆地全部变成0
    //遍历完所有网格，返回岛屿 numIslands3
    public static int numIslands3(char[][] board) {
        int ans = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == '1') {
                    ans++;
                    infect(board, i, j);
                }
            }
        }
        return ans;
    }

    //从(i,j)这个位置出发，把所有连成一片的'1'字符，变成0
    public static void infect(char[][] board, int i, int j) {

        if (i < 0 || i == board.length || j < 0 || j == board[i].length || board[i][j] != '1') {
            return;
        }
        board[i][j] = 0;
        infect(board, i + 1, j);
        infect(board, i - 1, j);
        infect(board, i, j + 1);
        infect(board, i, j - 1);
    }

    //并查集
    //x,y分别表示dots，然后对这些dots做并查集功能，求出sets numIslands1
    public static int numIslands1(char[][] board) {
        int row = board.length;
        int col = board[0].length;
        Dot[][] dots = new Dot[row][col];
        List<Dot> dotList = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == '1') {
                    dots[i][j] = new Dot();
                    dotList.add(dots[i][j]);
                }
            }
        }
        UnionFind1<Dot> uf = new UnionFind1<>(dotList);
        //对每个节点做循环，判断自身是否是1，上左是否是1，如果是，则将他的上下左右和自身union
        //又因为最上和最左没有上和左，因此单独提取出来，弄成三个for循环
        for (int j = 1; j < col; j++) {
            if (board[0][j - 1] == '1' && board[0][j] == '1') {
                uf.union(dots[0][j - 1], dots[0][j]);
            }
        }
        for (int i = 1; i < row; i++) {
            if (board[i - 1][0] == '1' && board[i][0] == '1') {
                uf.union(dots[i - 1][0], dots[i][0]);
            }
        }
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if (board[i][j] == '1') {
                    if (board[i][j - 1] == '1') {
                        uf.union(dots[i][j - 1], dots[i][j]);
                    }
                    if (board[i - 1][j] == '1') {
                        uf.union(dots[i - 1][j], dots[i][j]);
                    }
                }
            }
        }
        return uf.sets();
    }

    public static class Dot {

    }

    public static class Node<V> {
        V value;

        public Node(V v) {
            this.value = v;
        }
    }

    public static class UnionFind1<V> {
        private HashMap<V, Node<V>> nodes;
        private HashMap<Node<V>, Node<V>> parents;
        private HashMap<Node<V>, Integer> sizeMap;

        public UnionFind1(List<V> values) {
            nodes = new HashMap<>();
            parents = new HashMap<>();
            sizeMap = new HashMap<>();
            for (V cur : values) {
                Node<V> node = new Node<>(cur);
                nodes.put(cur, node);
                parents.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        public Node<V> findFather(Node<V> cur) {
            Stack<Node<V>> path = new Stack<>();
            while (cur != parents.get(cur)) {
                path.push(cur);
                cur = parents.get(cur);
            }
            while (!path.isEmpty()) {
                parents.put(path.pop(), cur);
            }
            return cur;
        }

        public void union(V a, V b) {
            Node<V> aHead = findFather(nodes.get(a));
            Node<V> bHead = findFather(nodes.get(b));
            if (aHead != bHead) {
                int aSetSize = sizeMap.get(aHead);
                int bSetSize = sizeMap.get(bHead);
                Node<V> big = aSetSize >= bSetSize ? aHead : bHead;
                Node<V> small = big == aHead ? bHead : aHead;
                parents.put(small, big);
                sizeMap.put(big, sizeMap.get(small) + sizeMap.get(big));
                sizeMap.remove(small);
            }
        }

        public int sets() {
            return sizeMap.size();
        }
    }

    public static int numIslands2(char[][] board) {
        int row = board.length;
        int col = board[0].length;
        UnionFind2 uf = new UnionFind2(board);
        for(int j = 1;j<col;j++){
            if(board[0][j-1] == '1' && board[0][j] == '1'){
                uf.union(0,j-1,0,j);
            }
        }
        for (int i = 1; i < row; i++) {
            if (board[i - 1][0] == '1' && board[i][0] == '1') {
                uf.union(i - 1, 0, i, 0);
            }
        }
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if (board[i][j] == '1') {
                    if (board[i][j - 1] == '1') {
                        uf.union(i, j - 1, i, j);
                    }
                    if (board[i - 1][j] == '1') {
                        uf.union(i - 1, j, i, j);
                    }
                }
            }
        }
        return uf.sets();
    }

    //row*col + col 表示当前节点
    public static class UnionFind2 {
        private int[] parent;
        private int[] size;
        private int[] help;
        private int col;
        private int sets;

        public UnionFind2(char[][] board) {
            col = board[0].length;
            sets = 0;
            int row = board.length;
            int len = row * col;
            parent = new int[len];
            size = new int[len];
            help = new int[len];
            for (int r = 0; r < row; r++) {
                for (int c = 0; c < col; c++) {
                    if (board[r][c] == '1') {
                        int i = index(r, c);
                        parent[i] = i;
                        size[i] = 1;
                        sets++;
                    }
                }
            }
        }

        //r,c坐标返回len上的值i
        private int index(int r, int c) {
            return r * col + c;
        }

        private int find(int i) {
            int hi = 0;
            while (i != parent[i]) {
                help[hi++] = i;
                i = parent[i];
            }
            for (hi--; hi >= 0; hi--) {
                parent[help[hi]] = i;
            }
            return i;
        }

        public void union(int r1, int c1, int r2, int c2) {
            int i1 = index(r1, c1);
            int i2 = index(r2, c2);
            int f1 = find(i1);
            int f2 = find(i2);
            if(f1 != f2){
                if(size[f1] >= size[f2]){
                    size[f1] += size[f2];
                    parent[f2] = f1;
                } else {
                    size[f2] += size[f1];
                    parent[f1] = f2;
                }
                sets--;
            }
        }

        public int sets(){return sets;}
    }

    // 为了测试
    public static char[][] generateRandomMatrix(int row, int col) {
        char[][] board = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                board[i][j] = Math.random() < 0.5 ? '1' : '0';
            }
        }
        return board;
    }

    // 为了测试
    public static char[][] copy(char[][] board) {
        int row = board.length;
        int col = board[0].length;
        char[][] ans = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                ans[i][j] = board[i][j];
            }
        }
        return ans;
    }

    // 为了测试
    public static void main(String[] args) {
        int row = 0;
        int col = 0;
        char[][] board1 = null;
        char[][] board2 = null;
        char[][] board3 = null;
        long start = 0;
        long end = 0;

        row = 1000;
        col = 1000;
        board1 = generateRandomMatrix(row, col);
        board2 = copy(board1);
        board3 = copy(board1);

        System.out.println("感染方法、并查集(map实现)、并查集(数组实现)的运行结果和运行时间");
        System.out.println("随机生成的二维矩阵规模 : " + row + " * " + col);

        start = System.currentTimeMillis();
        System.out.println("感染方法的运行结果: " + numIslands3(board1));
        end = System.currentTimeMillis();
        System.out.println("感染方法的运行时间: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        System.out.println("并查集(map实现)的运行结果: " + numIslands1(board2));
        end = System.currentTimeMillis();
        System.out.println("并查集(map实现)的运行时间: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        System.out.println("并查集(数组实现)的运行结果: " + numIslands2(board3));
        end = System.currentTimeMillis();
        System.out.println("并查集(数组实现)的运行时间: " + (end - start) + " ms");

        System.out.println();

        row = 10000;
        col = 10000;
        board1 = generateRandomMatrix(row, col);
        board3 = copy(board1);
        System.out.println("感染方法、并查集(数组实现)的运行结果和运行时间");
        System.out.println("随机生成的二维矩阵规模 : " + row + " * " + col);

        start = System.currentTimeMillis();
        System.out.println("感染方法的运行结果: " + numIslands3(board1));
        end = System.currentTimeMillis();
        System.out.println("感染方法的运行时间: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        System.out.println("并查集(数组实现)的运行结果: " + numIslands2(board3));
        end = System.currentTimeMillis();
        System.out.println("并查集(数组实现)的运行时间: " + (end - start) + " ms");

    }
}
