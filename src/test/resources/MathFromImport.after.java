import java.lang.Math;

public class MathFromImport {

    java.math.BigInteger a = java.math.BigInteger.ONE.abs();

    java.math.BigInteger c = java.math.BigInteger.valueOf(9).min(java.math.BigInteger.TEN);

    java.math.BigInteger d = java.math.BigInteger.valueOf(98).abs().abs();

    java.math.BigInteger e = java.math.BigInteger.valueOf(90).min(java.math.BigInteger.TEN).abs();

    java.math.BigInteger h = java.math.BigInteger.valueOf(100).max(java.math.BigInteger.valueOf(9)).min(java.math.BigInteger.ONE.max(java.math.BigInteger.TWO));

    java.math.BigInteger f = java.math.BigInteger.valueOf(100).max(java.math.BigInteger.valueOf(9)).min(java.math.BigInteger.ONE.max(java.math.BigInteger.TWO).abs()).abs();

    java.math.BigInteger k = java.math.BigInteger.valueOf(a).max(java.math.BigInteger.valueOf(c)).min(java.math.BigInteger.valueOf(c).max(java.math.BigInteger.valueOf(d)).abs()).abs();

    java.math.BigInteger m = java.math.BigInteger.valueOf(a).max(java.math.BigInteger.valueOf(c)).min(java.math.BigInteger.valueOf(d).max(java.math.BigInteger.valueOf(f)).abs()).min(java.math.BigInteger.valueOf(k));

    java.math.BigInteger n = java.math.BigInteger.valueOf(d).max(java.math.BigInteger.valueOf(e)).max(java.math.BigInteger.valueOf(f).max(java.math.BigInteger.valueOf(k)).abs()).min(java.math.BigInteger.valueOf(f)).abs();

    public static void main(String[] args) {
        java.math.BigInteger g = java.math.BigInteger.TEN.abs();
    }
}
