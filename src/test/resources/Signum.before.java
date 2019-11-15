public class Signum {
    public static void main(String[] args) {
        int a  /* BigInteger */ = Integer.signum(10 + 30);
        int b = 55;
        int c  /* BigInteger */ = Integer.signum(b);
        int d  /* BigInteger */= signum(566);
        int f /* BigInteger */ = Integer.signum(d);
        int e /* BigInteger */ = Integer.signum(b + c);
    }

    public static int signum(int number) {
        return number;
    }
}
