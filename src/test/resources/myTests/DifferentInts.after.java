public class DifferentInts {

    public static void main(String[] args) {
        java.math.BigInteger a = java.math.BigInteger.TEN;
        int b = a.intValue();
        java.math.BigInteger d = (a.add(java.math.BigInteger.valueOf(b))).multiply(java.math.BigInteger.valueOf(b));
        int e = (a.add(java.math.BigInteger.valueOf(b))).multiply(java.math.BigInteger.valueOf(b)).intValue();
        int c = 0;
        java.math.BigInteger f = java.math.BigInteger.valueOf(8);
        c = b;
        f = ((a.add(java.math.BigInteger.valueOf(b))).multiply(java.math.BigInteger.valueOf(c)).add((d.remainder(java.math.BigInteger.valueOf(e)))));
        f = java.math.BigInteger.valueOf(b).add(java.math.BigInteger.valueOf(b));
    }
}
