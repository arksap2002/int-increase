public class DifferentInts {

    public static void main(String[] args) {
        java.math.BigInteger a = java.math.BigInteger.TEN;
        int b = a.intValue();
        java.math.BigInteger d = (a.add(new java.math.BigInteger(b))).multiply(new java.math.BigInteger(b));
        int e = (a.add(new java.math.BigInteger(b))).multiply(new java.math.BigInteger(b)).intValue();
        int c = 0;
        java.math.BigInteger f = new java.math.BigInteger(8);
        c = b;
        f = ((a.add(new java.math.BigInteger(b))).multiply(new java.math.BigInteger(c)).add((d.remainder(new java.math.BigInteger(e)))));
        f = new java.math.BigInteger(b).add(new java.math.BigInteger(b));
    }
}
