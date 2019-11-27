public class Method {

    public static void main(String[] args) {
        java.math.BigInteger a = java.math.BigInteger.TEN;
        int b = 10;
        int[][][] c = new int[a.intValue()][b][a.intValue()];
        java.math.BigInteger d = new java.math.BigInteger(foo(a, b, c));
        int e = foo(foo(foo(a, b, c) + 10 - a, b + a, c), a + b, c);
    }

    private static java.math.BigInteger foo(int a, java.math.BigInteger b, java.math.BigInteger[][][] c) {
        return new java.math.BigInteger(a).add(b.multiply(c[0][0][0]));
    }
}
