package rumeng.class01;

public class Code02_RandToRand {

    public static void main(String[] args) {
        System.out.println("测试开始");
        // Math.random（）-> double -> [0,1)
        //
        int testTimes = 10000000;
        int count = 0;
        for (int i = 0; i < testTimes; i++) {
            if (Math.random() < 0.75) {
                count++;
            }
        }
        System.out.println((double) count / (double) testTimes);

        System.out.println("=======================");

        // [0,1) -> [0,8)
        count = 0;
        for (int i = 0; i < testTimes; i++) {
            if (Math.random() * 8 < 5) {
                count++;
            }
        }
        System.out.println((double) count / (double) testTimes);

        int K = 9;


        int[] counts = new int[9];
        for (int i = 0; i < testTimes; i++) {
            int ans = (int) (Math.random() * K);
            counts[ans]++;
        }
        for (int i = 0; i < K; i++) {
            System.out.println(i + "这个数，出现了" + counts[i] + " 次");
        }

        System.out.println("=======================");

        count = 0;
        double x = 0.7;
        for (int i = 0; i < testTimes; i++) {
            if (xToXPower2() < x) {
                count++;
            }
        }

        System.out.println((double) count / (double) testTimes);
        System.out.println(Math.pow(x, 2));

        System.out.println("================");
        count = 0;

        for (int i = 0; i < testTimes; i++) {
            if (f2() == 0) {
                count++;
            }
        }

        System.out.println((double) count / (double) testTimes);
    }

    public static double xToXPower2() {
        return Math.max(Math.random(), Math.random());
    }

    // lib里的，不能改！
    public static int f1() {
        return (int) (Math.random() * 5) + 1;
    }

    public static int f2() {
        int ans = 0;
        do {
            ans = f1();
        } while (ans == 3);

        return ans < 3 ? 0 : 1;
    }

    //得到000~111 做到等概率 做到0~7等概率返回一个
    public static int f3() {
        int ans = (f2() << 2) + (f2() << 1) + (f2() << 0);
        return ans;
    }

    // 0~6等概率返回一个
    public static int f4() {
        int ans = 0;
        do {
            ans = f3();
        }while (ans == 7);
        return ans;
    }
}
