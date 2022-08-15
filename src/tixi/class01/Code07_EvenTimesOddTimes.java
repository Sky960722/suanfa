package tixi.class01;

public class Code07_EvenTimesOddTimes {

    public static void printOddTImesNum1(int[] arr) {
        int eor = 0;
        for (int i = 0; i < arr.length; i++) {
            eor ^= arr[i];
        }
        System.out.println(eor);
    }

    public static int rightOne(int x) {
        x = x & (-x);
        return x;
    }

    public static void printOddTimesNum2(int[] arr){
        int eor = 0;
        for(int i = 0;i < arr.length;i++){
            eor ^= arr[i];
        }

        int rightOne = eor & (-eor);
        int onlyOne = 0;
        for(int i = 0;i < arr.length ;i++){
            if((arr[i] & rightOne) !=0){
                onlyOne ^= arr[i];
            }
        }
        System.out.println(onlyOne + " " + (eor ^ onlyOne));
    }

}
