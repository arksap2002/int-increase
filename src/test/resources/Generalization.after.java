public class Generalization {

    public static void main(String[] args) {
        java.math.BigInteger a = java.math.BigInteger.TEN;
        int b = 9;
        int c = 8;
        java.math.BigInteger d = a.hashCode().add(java.math.BigInteger.valueOf(b)).add(new java.math.BigInteger(Integer.hashCode(c)));
        java.math.BigInteger e = new java.math.BigInteger("100").multiply(Integer.MAX_VALUE).add(java.math.BigInteger.valueOf(c));
        java.math.BigInteger f = a.add(java.math.BigInteger.valueOf(c)).hashCode();
    }
}
