public class ForWhile {
    public static void main(String[] args) {
        int a /* BigInteger */ = 10;
        int b = 9;
        for (int /* BigInteger */  j = 0; j < 200; j++) {
            System.out.println(-1);
        }
        int /* BigInteger */  j = 0;
        while(j < 100) {
            System.out.println(-1);
            j++;
        }
        for (int i = 0; i < a; i++) {
            System.out.println(1);
        }
        for (int i /* BigInteger */ = 0; i < a; i++) {
            System.out.println(1);
        }
        for (int i = 0; i < b; i++) {
            System.out.println(1);
        }
        for (int i /* BigInteger */ = 0; i < b; i++) {
            System.out.println(1);
        }
    }
}
