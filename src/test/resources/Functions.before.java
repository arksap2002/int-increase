public class Functions {

    public static void main(String[] args) {
        int a /* BigInteger */ = 10;
        int b = 0;
        int c /* BigInteger */ = f * (a + b);
        int d = f * (a + b);
        int e /* BigInteger */ = f1 * (a + b);
        int f = f1 * (a + b);
    }

    public static int f /* BigInteger */ (int a, int b /* BigInteger */) {
        return a + b;
    }

    public static int f1(int a /* BigInteger */, int b) {
        return b;
    }
}
