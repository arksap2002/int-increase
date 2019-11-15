public class Signum {

    public static void main(String[] args) {
        java.math.BigInteger a = java.math.BigInteger.TEN.add(java.math.BigInteger.valueOf(30)).signum();
        int b = 55;
        java.math.BigInteger c = java.math.BigInteger.valueOf(b).signum();
        java.math.BigInteger d = signum(566);
        java.math.BigInteger f = d.signum();
        java.math.BigInteger e = java.math.BigInteger.valueOf(b).add(c).signum();
    }

    public static int signum(int number) {
        return number;
    }
}
