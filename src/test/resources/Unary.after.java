public class Unary {

    java.math.BigInteger a = java.math.BigInteger.TWO.negate();

    java.math.BigInteger b = java.math.BigInteger.valueOf(3);

    int c = -2 + 3;

    java.math.BigInteger d = java.math.BigInteger.valueOf(3).subtract(java.math.BigInteger.TWO.negate());

    int e = -(2 * 3);

    java.math.BigInteger f = java.math.BigInteger.TWO.negate().add(java.math.BigInteger.valueOf(9)).add(java.math.BigInteger.valueOf(12).multiply(java.math.BigInteger.valueOf(8))).subtract(java.math.BigInteger.valueOf(200).divide(java.math.BigInteger.valueOf(100)));

    public static void main(String[] args) {
        java.math.BigInteger g = java.math.BigInteger.valueOf(4).negate();
        int k = +10;
        java.math.BigInteger l = java.math.BigInteger.valueOf(7).negate().add(java.math.BigInteger.TEN);
        int m = +90 - -1;
        java.math.BigInteger n = (java.math.BigInteger.valueOf(21).multiply(java.math.BigInteger.valueOf(53))).negate();
        java.math.BigInteger o = java.math.BigInteger.ONE.add((java.math.BigInteger.TWO.multiply(java.math.BigInteger.valueOf(3)))).subtract(java.math.BigInteger.valueOf(100).multiply((java.math.BigInteger.valueOf(12).subtract(java.math.BigInteger.valueOf(11))).negate())).subtract((java.math.BigInteger.valueOf(13).multiply((java.math.BigInteger.valueOf(12).divide(java.math.BigInteger.valueOf(3)))).add(java.math.BigInteger.ONE)).divide(java.math.BigInteger.valueOf(3)));
    }
}
