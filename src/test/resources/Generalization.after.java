public class Generalization {

    public static void main(String[] args) {
        java.math.BigInteger a = java.math.BigInteger.TEN;
        int b = 9;
        int c = 8;
        java.math.BigInteger d = new java.math.BigInteger(Integer.hashCode(a)).add(new java.math.BigInteger(b)).add(new java.math.BigInteger(Integer.hashCode(c)));
        java.math.BigInteger e = new java.math.BigInteger("100").multiply(new java.math.BigInteger(Integer.MAX_VALUE)).add(new java.math.BigInteger(c));
        java.math.BigInteger f = new java.math.BigInteger(Integer.hashCode(a + c));
    }
}
