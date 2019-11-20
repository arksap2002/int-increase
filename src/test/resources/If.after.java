public class If {

    public static void main(String[] args) {
        java.math.BigInteger a = java.math.BigInteger.ZERO;
        int b = 1;
        if (a.compareTo(new java.math.BigInteger(5).subtract(java.math.BigInteger.TWO).min(new java.math.BigInteger(3).negate())) > 0) {
            a = new java.math.BigInteger(3).negate();
        }
        if (a.equals(java.math.BigInteger.ZERO)) {
            a = java.math.BigInteger.TWO.negate();
        }
        if (!a.equals(java.math.BigInteger.ZERO)) {
            a = java.math.BigInteger.TWO.negate();
        }
        if (!(a.compareTo(java.math.BigInteger.ZERO) > 0)) {
            a = java.math.BigInteger.TWO.negate();
        }
        if (a.compareTo(java.math.BigInteger.TEN.negate().abs()) <= 0) {
            a = java.math.BigInteger.ONE.negate();
        }
        if (new java.math.BigInteger(b).compareTo(a) > 0) {
            a = new java.math.BigInteger(b);
        }
    }
}
