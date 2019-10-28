public class DifferentInts {

    public static void main(String[] args) {
        int a /* BigInteger */ = 10;
        int b = a;
        int d /* BigInteger */ = (a + b) * b;
        int e = (a + b) * b;
        if (a > b) {
            System.out.println(a + b);
        }
        int c = 0;
        int f /* BigInteger */ = 8;
        c = a;
        f = ((a + b) * c + (d % e));
        f = b + b;
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
