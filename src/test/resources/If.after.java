public class If {

    public static void main(String[] args) {
        java.math.BigInteger a = java.math.BigInteger.ZERO;
        if (a.compareTo(java.math.BigInteger.valueOf(5).subtract(java.math.BigInteger.TWO).min(java.math.BigInteger.valueOf(3).negate())) > 0) {
            a = java.math.BigInteger.valueOf(3).negate();
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
    }
}
