public class If {

    public static void main(String[] args) {
        int /* BigInteger */ a = 0;
        int b = 1;
        if (a > Math.min(5 - 2, -3)) {
            a = -3;
        }
        if (a == 0) {
            a = -2;
        }
        if (a != 0) {
            a = -2;
        }
        if (!(a > 0)) {
            a = -2;
        }
        if (a <= Math.abs(-10)) {
            a = -1;
        }
        if (b > a) {
            a = b;
        }
    }
}
