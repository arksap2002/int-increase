public class AssingExpr {

    public static void main(String[] args) {
        java.math.BigInteger a = java.math.BigInteger.TEN;
        java.math.BigInteger c, d = java.math.BigInteger.ONE;
        a = java.math.BigInteger.valueOf(5);
        a = ((java.math.BigInteger.ONE.add(java.math.BigInteger.TWO)).multiply((java.math.BigInteger.valueOf(100).subtract(java.math.BigInteger.valueOf(12)))).divide((java.math.BigInteger.TWO.negate())).multiply((java.math.BigInteger.ONE.negate()))).remainder(java.math.BigInteger.valueOf(239));
        a = a.min(java.math.BigInteger.valueOf(18).negate());
    }
}
