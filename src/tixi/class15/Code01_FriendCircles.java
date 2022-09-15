package tixi.class15;

//https://leetcode.cn/problems/number-of-provinces/
//省份数量
//题目：有 n 个城市，其中一些彼此相连，另一些没有相连。如果城市 a 与城市 b 直接相连，且城市 b 与城市 c 直接相连，那么城市 a 与城市 c 间接相连。
//
//省份 是一组直接或间接相连的城市，组内不含其他没有相连的城市。
//
//给你一个 n x n 的矩阵 isConnected ，其中 isConnected[i][j] = 1 表示第 i 个城市和第 j 个城市直接相连，而 isConnected[i][j] = 0 表示二者不直接相连。
//
//返回矩阵中 省份 的数量。
//
//来源：力扣（LeetCode）
//链接：https://leetcode.cn/problems/number-of-provinces
//著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
//输入：isConnected = [[1,1,0],[1,1,0],[0,0,1]]
//        输出：2
public class Code01_FriendCircles {

    //思路
    //直接用并查集的思路去想这个问题
    //1.n*n矩阵表明了一共有n个城市，省份是一组直接或间接相连的城市，则直接用并查集的思路，每个城市自生都是一个集合，如果每两个城市之间相连，就把这个城市并入这个集合中，全部求完，知道当前这个集合中的sets数量，就是省份
    public static int findCircleNum(int[][] M) {
        int N = M.length;
        UnionFind unionFind = new UnionFind(N);
        //ij和ji必然一样，只要沿着n*n矩阵的对角线往下遍历，就能求出所有解
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if(M[i][j] == 1){
                    unionFind.union(i, j);
                }
            }
        }
        return unionFind.sets();
    }

    public static class UnionFind {
        //parent[i] = k; i的父亲是k
        private int[] parent;
        //size[i] = k ：如果i是代表节点，size[i]才有意义，否则无意义
        //i 所在的集合大小是多少
        private int[] size;
        // 辅助结构
        private int[] help;
        // 一共有多少个集合
        private int sets;

        public UnionFind(int N) {
            parent = new int[N];
            size = new int[N];
            help = new int[N];
            sets = N;
            for (int i = 0; i < N; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        //从i开始一直往上，往上到不能再往上，代表节点，返回
        //这个过程要做路径压缩
        public int find(int i) {
            int hi = 0;
            while (i != parent[i]) {
                //help存储的是每个指向父节点的节点
                help[hi++] = i;
                i = parent[i];
            }
            for (hi--; hi >= 0; hi--) {
                parent[help[hi]] = i;
            }
            return i;
        }

        //合并
        public void union(int i, int j) {
            int f1 = find(i);
            int f2 = find(j);
            if (f1 != f2) {
                if (size[f1] >= size[f2]) {
                    size[f1] += size[f2];
                    parent[f2] = f1;
                } else {
                    size[f2] += size[f1];
                    parent[f1] = f2;
                }
                sets--;
            }
        }

        public int sets() {
            return sets;
        }
    }
}
