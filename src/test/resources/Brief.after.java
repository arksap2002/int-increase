public class Brief {

    public static void main(String[] args) {
        java.math.BigInteger a = java.math.BigInteger.ZERO;
        a = java.math.BigInteger.ONE.add(a);
        a = java.math.BigInteger.ONE.subtract(a);
        a = java.math.BigInteger.TEN.add(a);
        a = java.math.BigInteger.ONE.negate().subtract(a);
        a = java.math.BigInteger.TWO.divide(a);
        a = java.math.BigInteger.valueOf(3).remainder(a);
        a = java.math.BigInteger.valueOf(100);
    }
}
