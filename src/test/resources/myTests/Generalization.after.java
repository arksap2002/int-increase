public class Generalization {

    public static void main(String[] args) {
        java.math.BigInteger a = java.math.BigInteger.TEN;
        int b = 9;
        int c = 8;
        java.math.BigInteger d = java.math.BigInteger.valueOf(Integer.hashCode(a)).add(java.math.BigInteger.valueOf(b)).add(java.math.BigInteger.valueOf(Integer.hashCode(c)));
        java.math.BigInteger e = java.math.BigInteger.valueOf("100").multiply(java.math.BigInteger.valueOf(Integer.MAX_VALUE)).add(java.math.BigInteger.valueOf(c));
        java.math.BigInteger f = java.math.BigInteger.valueOf(Integer.hashCode(a + c));
    }
}
