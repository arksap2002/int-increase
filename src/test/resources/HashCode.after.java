public class HashCode {

    public static void main(String[] args) {
        java.math.BigInteger a = java.math.BigInteger.TEN;
        int b = a.hashCode();
        int c = 10;
        int d = Integer.hashCode(c);
        java.math.BigInteger e = new java.math.BigInteger(a.hashCode());
        java.math.BigInteger f = new java.math.BigInteger(Integer.hashCode(c));
    }
}
