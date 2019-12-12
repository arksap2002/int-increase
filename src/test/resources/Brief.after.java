public class Brief {

    public static void main(String[] args) {
        java.math.BigInteger a = java.math.BigInteger.ZERO;
        a = a.add(java.math.BigInteger.ONE);
        a = a.subtract(java.math.BigInteger.ONE);
        a = a.add(java.math.BigInteger.TEN);
        a = a.subtract(java.math.BigInteger.ONE.negate());
        a = a.divide(java.math.BigInteger.TWO);
        a = a.remainder(java.math.BigInteger.valueOf(3));
        a = java.math.BigInteger.valueOf(100);
    }
}
