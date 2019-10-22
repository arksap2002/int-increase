import java.lang.Math;

public class MathFromImport {

    java.math.BigInteger a = java.math.BigInteger.ONE.abs();

    int c = Math.min(9, 10);

    java.math.BigInteger d = java.math.BigInteger.valueOf(98).abs().abs();

    int e = Math.abs(Math.min(90, 10));

    java.math.BigInteger h = java.math.BigInteger.valueOf(100).max(java.math.BigInteger.valueOf(9)).min(java.math.BigInteger.ONE.max(java.math.BigInteger.TWO));

    int f = Math.abs(Math.min(Math.max(100, 9), Math.abs(Math.max(1, 2))));

    java.math.BigInteger k = java.math.BigInteger.valueOf(a).max(java.math.BigInteger.valueOf(c)).min(java.math.BigInteger.valueOf(c).max(java.math.BigInteger.valueOf(d)).abs()).abs();

    int m = Math.min(Math.min(Math.max(a, c), Math.abs(Math.max(d, f))), k);

    java.math.BigInteger n = java.math.BigInteger.valueOf(d).max(java.math.BigInteger.valueOf(e)).max(java.math.BigInteger.valueOf(f).max(java.math.BigInteger.valueOf(k)).abs()).min(java.math.BigInteger.valueOf(f)).abs();

    public static void main(String[] args) {
        java.math.BigInteger g = java.math.BigInteger.TEN.abs();
    }
}
