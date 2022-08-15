package tixi.class08;

import sun.text.normalizer.Trie;

import javax.sound.midi.Soundbank;
import java.util.HashMap;

    public class Code02_TrieTree {
    public static class Node1 {
        public int pass;
        public int end;
        public Node1[] nexts;

        public Node1() {
            pass = 0;
            end = 0;
            nexts = new Node1[26];
        }
    }

    public static class Trie1 {
        private Node1 root;

        public Trie1() {
            root = new Node1();
        }

        public void insert(String word) {
            if (word == null) {
                return;
            }
            char[] charArr = word.toCharArray();
            Node1 node = root;
            node.pass++;
            int path = 0;
            for (int i = 0; i < charArr.length; i++) {
                path = charArr[i] - 'a';
                if (node.nexts[path] == null) {
                    node.nexts[path] = new Node1();
                }
                node = node.nexts[path];
                node.pass++;
            }
            node.end++;
        }

        public int search(String word) {
            if (word == null) {
                return 0;
            }
            char[] charArr = word.toCharArray();
            Node1 node = root;
            int index = 0;
            for (int i = 0; i < charArr.length; i++) {
                index = charArr[i] - 'a';
                if (node.nexts[index] == null) {
                    return 0;
                }
                node = node.nexts[index];
            }
            return node.end;
        }

        public void delete(String word) {
            if (search(word) != 0) {
                char[] charArr = word.toCharArray();
                Node1 node = root;
                node.pass--;
                int path = 0;
                for (int i = 0; i < charArr.length; i++) {
                    path = charArr[i] - 'a';
                    if (--node.nexts[path].pass == 0) {
                        node.nexts[path] = null;
                        return;
                    }
                    node = node.nexts[path];
                }
                node.end--;
            }
        }

        public int prefixNumber(String pre) {
            if (pre == null) {
                return 0;
            }
            char[] charArr = pre.toCharArray();
            Node1 node = root;
            int index = 0;
            for (int i = 0; i < charArr.length; i++) {
                index = charArr[i] - 'a';
                if (node.nexts[index] == null) {
                    return 0;
                }
                node = node.nexts[index];
            }
            return node.pass;
        }
    }

    public static class Node2 {
        private int pass;
        private int end;
        public HashMap<Integer, Node2> nexts;

        public Node2() {
            pass = 0;
            end = 0;
            nexts = new HashMap<>();
        }
    }

    public static class Trie2 {
        private Node2 root;

        public Trie2() {
            root = new Node2();
        }

        public void insert(String word) {
            if (word == null) {
                return;
            }
            char[] charArr = word.toCharArray();
            Node2 node = root;
            root.pass++;
            int path = 0;
            for (int i = 0; i < charArr.length; i++) {
                path = charArr[i];
                if (!node.nexts.containsKey(path)) {
                    node.nexts.put(path, new Node2());
                }
                node = node.nexts.get(path);
                node.pass++;
            }
            node.end++;
        }

        public int search(String word) {
            if (word == null) {
                return 0;
            }
            char[] charArr = word.toCharArray();
            Node2 node = root;
            int path = 0;
            for (int i = 0; i < charArr.length; i++) {
                path = charArr[i];
                if (!node.nexts.containsKey(path)) {
                    return 0;
                }
                node = node.nexts.get(path);
            }
            return node.end;
        }

        public void delete(String word) {
            if (search(word) != 0) {
                char[] chArr = word.toCharArray();
                Node2 node = root;
                node.pass--;
                int path = 0;
                for (int i = 0; i < chArr.length; i++) {
                    path = chArr[i];
                    if (--node.nexts.get(path).pass == 0) {
                        node.nexts.remove(path);
                        return;
                    }
                    node = node.nexts.get(path);
                }
                node.end--;
            }
        }

        public int prefixNumber(String pre) {
            if (pre == null) {
                return 0;
            }
            char[] chArr = pre.toCharArray();
            Node2 node = root;
            int path = 0;
            for (int i = 0; i < chArr.length; i++) {
                path = chArr[i];
                if (!node.nexts.containsKey(path)) {
                    return 0;
                }
                node = node.nexts.get(path);
            }
            return node.pass;
        }

        public static class Right {
            private HashMap<String, Integer> box;

            public Right() {
                box = new HashMap<>();
            }

            public void insert(String word) {
                if (!box.containsKey(word)) {
                    box.put(word, 1);
                } else {
                    box.put(word, box.get(word) + 1);
                }
            }

            public int search(String word) {
                if (!box.containsKey(word)) {
                    return 0;
                } else {
                    return box.get(word);
                }
            }

            public void delete(String word) {
                if (box.containsKey(word)) {
                    if (box.get(word) == 1) {
                        box.remove(word);
                    } else {
                        box.put(word, box.get(word) - 1);
                    }
                }
            }

            public int prefixNumber(String pre) {
                int count = 0;
                for (String cur : box.keySet()) {
                    if (cur.startsWith(pre)) {
                        count += box.get(cur);
                    }
                }
                return count;
            }
        }

        public static String generateRandomString(int strLen) {
            char[] ans = new char[(int) (Math.random() * strLen) + 1];
            for (int i = 0; i < ans.length; i++) {
                int value = (int) (Math.random() * 26);
                //97是'a',97~122号为26个小写英文字母
                ans[i] = (char) (97 + value);
            }
            return String.valueOf(ans);
        }

        public static String[] generateStringArr(int arrLen, int strLen) {
            String[] ans = new String[(int) (Math.random() * arrLen + 1)];
            for (int i = 0; i < ans.length; i++) {
                ans[i] = generateRandomString(strLen);
            }
            return ans;
        }

        public static void main(String[] args) {
            System.out.println("start!");
            int arrLen = 100;
            int strLen = 20;
            int testTimes = 1000000;
            for (int i = 0; i < testTimes; i++) {
                String[] arr = generateStringArr(arrLen, strLen);
                Trie1 t1 = new Trie1();
                Trie2 t2 = new Trie2();
                Right r1 = new Right();
                for (int j = 0; j < arr.length; j++) {
                    double decide = Math.random();
                    if(decide < 0.25){
                        t1.insert(arr[j]);
                        t2.insert(arr[j]);
                        r1.insert(arr[j]);
                    } else if(decide < 0.5){
                        t1.delete(arr[j]);
                        t2.delete(arr[j]);
                        r1.delete(arr[j]);
                    } else if(decide<0.75){
                        int ans1 = t1.search(arr[j]);
                        int ans2 = t2.search(arr[j]);
                        int ans3 = r1.search(arr[j]);
                        if(ans1 != ans2 || ans1 != ans3){
                            System.out.println("Oops");
                        }
                    }else {
                        int ans1 = t1.prefixNumber(arr[j]);
                        int ans2 = t2.prefixNumber(arr[j]);
                        int ans3 = r1.prefixNumber(arr[j]);
                        if(ans1 != ans2 || ans1 != ans3){
                            System.out.println("Oops");
                        }
                    }
                }
            }
            System.out.println("finish!");
        }
    }

}
