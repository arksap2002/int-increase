import java.lang.Math;

public class MathFromImport {

    int a /* BigInteger */ = Math.abs(1);

    int c = Math.min(9, 10);

    int d /* BigInteger */ = Math.abs(Math.abs(98));

    int e = Math.abs(Math.min(90, 10));

    int h /* BigInteger */ = Math.min(Math.max(100, 9), Math.max(1, 2));

    int f = Math.abs(Math.min(Math.max(100, 9), Math.abs(Math.max(1, 2))));

    int k /* BigInteger */ = Math.abs(Math.min(Math.max(a, c), Math.abs(Math.max(c, d))));

    int m = Math.min(Math.min(Math.max(a, c), Math.abs(Math.max(d, f))), k);

    int n /* BigInteger */ = Math.abs(Math.min(Math.max(Math.max(d, e), Math.abs(Math.max(f, k))), f));

    public static void main(String[] args) {

        int g /* BigInteger */ = Math.abs(10);
    }
}
