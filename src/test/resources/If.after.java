public class If {

    public static void main(String[] args) {
        java.math.BigInteger a = java.math.BigInteger.ZERO;
        java.math.BigInteger b = java.math.BigInteger.ZERO;
        if (b.compareTo(java.math.BigInteger.TEN) > 0) {
            b = java.math.BigInteger.TEN;
        }
        if (a.compareTo(java.math.BigInteger.valueOf(5).subtract(java.math.BigInteger.TWO).min(java.math.BigInteger.valueOf(3).negate())) > 0) {
            a = java.math.BigInteger.valueOf(3).negate();
        }
        if (a.equals(java.math.BigInteger.ZERO)) {
            a = java.math.BigInteger.TWO.negate();
        }
        if (!a.equals(java.math.BigInteger.ZERO)) {
            a = java.math.BigInteger.TWO.negate();
        }
        if (!(a > 0)) {
            a = java.math.BigInteger.TWO.negate();
        }
        if (a.compareTo(java.math.BigInteger.TEN.negate().abs()) <= 0) {
            a = java.math.BigInteger.ONE.negate();
        }
        if (b.compareTo(a) > 0) {
            a = b;
        }
        if (b > 0) {
            b = 0;
        }
    }
}
