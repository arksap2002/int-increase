public class Method {

    public static void main(String[] args) {
        java.math.BigInteger a = java.math.BigInteger.TEN;
        int b = 10;
        int[][][] c = new int[a.intValue()][b][a.intValue()];
        java.math.BigInteger[][] f = new java.math.BigInteger[10][a.intValue()];
        java.math.BigInteger d = foo(a.intValue(), java.math.BigInteger.valueOf(b), c);
        int e = foo(foo(foo(java.math.BigInteger.valueOf(c[0][0][0]).multiply(f[0][0]).intValue(), java.math.BigInteger.valueOf(b), c).add(java.math.BigInteger.TEN).subtract(a).intValue(), java.math.BigInteger.valueOf(b).add(a), c).intValue(), a.add(java.math.BigInteger.valueOf(b)), c).intValue();
    }

    private static java.math.BigInteger foo(int a, java.math.BigInteger b, int[][][] c) {
        return java.math.BigInteger.valueOf(a).add(b.multiply(java.math.BigInteger.valueOf(c[0][0][0]))).subtract(foo(java.math.BigInteger.valueOf(a).add(b).intValue(), java.math.BigInteger.valueOf(a).add(b), c));
    }
}