package rumeng.class01;

public class Code06_PrintB {
    public static void main(String[] args) {
        int num = -1 ;
        System.out.println(num);
        print(num);
    }

    public  static void print(int num){
        for(int i = 31;i>=0;i--){
            System.out.print((num& (1<<i))==0? "0":"1");
        }
        System.out.println();
    }
}
