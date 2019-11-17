public class AssingExpr {

    public static void main(String[] args) {
        java.math.BigInteger a = java.math.BigInteger.TEN;
        java.math.BigInteger c, d = java.math.BigInteger.ONE;
        a = new java.math.BigInteger(5);
        a = ((java.math.BigInteger.ONE.add(java.math.BigInteger.TWO)).multiply((new java.math.BigInteger(100).subtract(new java.math.BigInteger(12)))).divide((java.math.BigInteger.TWO.negate())).multiply((java.math.BigInteger.ONE.negate()))).remainder(new java.math.BigInteger(239));
        a = a.min(new java.math.BigInteger(18).negate());
    }
}
