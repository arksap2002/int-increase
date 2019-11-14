public class HashCode {

    public static void main(String[] args) {
        java.math.BigInteger a = java.math.BigInteger.TEN;
        int b = a.hashCode();
        int c = 10;
        int d = Integer.hashCode(c);
        java.math.BigInteger e = java.math.BigInteger.valueOf(a.hashCode());
        java.math.BigInteger f = java.math.BigInteger.valueOf(Integer.hashCode(c));
    }
}
