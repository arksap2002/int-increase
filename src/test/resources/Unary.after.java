public class Unary {

    java.math.BigInteger a = java.math.BigInteger.TWO.negate();

    java.math.BigInteger b = new java.math.BigInteger(3);

    java.math.BigInteger c = java.math.BigInteger.TWO.negate().add(new java.math.BigInteger(3));

    java.math.BigInteger d = new java.math.BigInteger(3).subtract(java.math.BigInteger.TWO.negate());

    java.math.BigInteger e = (java.math.BigInteger.TWO.multiply(new java.math.BigInteger(3))).negate();

    java.math.BigInteger f = java.math.BigInteger.TWO.negate().add(new java.math.BigInteger(9)).add(new java.math.BigInteger(12).multiply(new java.math.BigInteger(8))).subtract(new java.math.BigInteger(200).divide(new java.math.BigInteger(100)));

    public static void main(String[] args) {
        java.math.BigInteger g = new java.math.BigInteger(4).negate();
        java.math.BigInteger k = java.math.BigInteger.TEN;
        java.math.BigInteger l = new java.math.BigInteger(7).negate().add(java.math.BigInteger.TEN);
        java.math.BigInteger m = new java.math.BigInteger(90).subtract(java.math.BigInteger.ONE.negate());
        java.math.BigInteger n = (new java.math.BigInteger(21).multiply(new java.math.BigInteger(53))).negate();
        java.math.BigInteger o = java.math.BigInteger.ONE.add((java.math.BigInteger.TWO.multiply(new java.math.BigInteger(3)))).subtract(new java.math.BigInteger(100).multiply((new java.math.BigInteger(12).subtract(new java.math.BigInteger(11))).negate())).subtract((new java.math.BigInteger(13).multiply((new java.math.BigInteger(12).divide(new java.math.BigInteger(3)))).add(java.math.BigInteger.ONE)).divide(new java.math.BigInteger(3)));
    }
}
