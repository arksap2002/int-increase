public class Signum {

    public static void main(String[] args) {
        java.math.BigInteger a = java.math.BigInteger.TEN.add(java.math.BigInteger.valueOf(30)).signum();
        java.math.BigInteger b = java.math.BigInteger.valueOf(55);
        java.math.BigInteger c = b.signum();
        java.math.BigInteger d = signum(566);
    }

    public static int signum(int number) {
        return number;
    }
}
