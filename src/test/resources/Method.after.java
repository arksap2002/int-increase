public class Method {

    public static void main(String[] args) {
        java.math.BigInteger a = java.math.BigInteger.TEN;
        int b = 10;
        int[][][] c = new int[a.intValue()][b][a.intValue()];
        java.math.BigInteger[][] f = new java.math.BigInteger[10][a.intValue()];
        java.math.BigInteger d = foo(a.intValue(), new java.math.BigInteger(b), c);
        int e = foo(foo(foo(new java.math.BigInteger(c[0][0][0]).multiply(f[0][0]).intValue(), new java.math.BigInteger(b), c).add(java.math.BigInteger.TEN).subtract(a).intValue(), new java.math.BigInteger(b).add(a), c).intValue(), a.add(new java.math.BigInteger(b)), c).intValue();
    }

    private static java.math.BigInteger foo(int a, java.math.BigInteger b, int[][][] c) {
        return new java.math.BigInteger(a).add(b.multiply(new java.math.BigInteger(c[0][0][0]))).subtract(foo(new java.math.BigInteger(a).add(b).intValue(), new java.math.BigInteger(a).add(b), c));
    }
}
