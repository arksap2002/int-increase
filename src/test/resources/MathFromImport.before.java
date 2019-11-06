import java.lang.Math;

public class MathFromImport {

    int /* BigInteger */ a = Math.abs(1);

    int /* BigInteger */ c = Math.min(9, 10);

    int /* BigInteger */ d = Math.abs(Math.abs(98));

    int /* BigInteger */ e = Math.abs(Math.min(90, 10));

    int /* BigInteger */ h = Math.min(Math.max(100, 9), Math.max(1, 2));

    int /* BigInteger */ f = Math.abs(Math.min(Math.max(100, 9), Math.abs(Math.max(1, 2))));

    int /* BigInteger */ k = Math.abs(Math.min(Math.max(a, c), Math.abs(Math.max(c, d))));

    int /* BigInteger */ m = Math.min(Math.min(Math.max(a, c), Math.abs(Math.max(d, f))), k);

    int /* BigInteger */ n = Math.abs(Math.min(Math.max(Math.max(d, e), Math.abs(Math.max(f, k))), f));

    public static void main(String[] args) {

        int /* BigInteger */ g = Math.abs(10);
    }
}
