public class DifferentInts {

    public static void main(String[] args) {
        int a /* BigInteger */ = 10;
        int b = a;
        int d /* BigInteger */ = (a + b) * b;
        int e = (a + b) * b;
        int c = 0;
        int f /* BigInteger */ = 8;
        c = b;
        f = ((a + b) * c + (d % e));
        f = b + b;
    }
}
