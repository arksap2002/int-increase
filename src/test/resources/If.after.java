public class If {

    public static void main(String[] args) {
        java.math.BigInteger a = java.math.BigInteger.ZERO;
        if (a.compareTo(java.math.BigInteger.valueOf(5).subtract(java.math.BigInteger.TWO).min(java.math.BigInteger.valueOf(3).negate())) > 0) {
            a = -3;
        }
        if (a.equals(java.math.BigInteger.ZERO)) {
            a = -2;
        }
        if (!a.equals(java.math.BigInteger.ZERO)) {
            a = -2;
        }
        if (!(a.compareTo(java.math.BigInteger.ZERO) > 0)) {
            a = -2;
        }
        if (a.compareTo(java.math.BigInteger.TEN.negate().abs()) <= 0) {
            a = -1;
        }
    }
}
