import java.lang.Math;

public class MathFromImport {

    java.math.BigInteger a = java.math.BigInteger.ONE.abs();

    java.math.BigInteger c = java.math.BigInteger.valueOf(9).min(java.math.BigInteger.TEN);

    java.math.BigInteger d = java.math.BigInteger.valueOf(98).abs().abs();

    java.math.BigInteger e = java.math.BigInteger.valueOf(90).min(java.math.BigInteger.TEN).abs();

    java.math.BigInteger h = java.math.BigInteger.valueOf(100).max(java.math.BigInteger.valueOf(9)).min(java.math.BigInteger.ONE.max(java.math.BigInteger.TWO));

    java.math.BigInteger f = java.math.BigInteger.valueOf(100).max(java.math.BigInteger.valueOf(9)).min(java.math.BigInteger.ONE.max(java.math.BigInteger.TWO).abs()).abs();

    java.math.BigInteger k = a.max(c).min(c.max(d).abs()).abs();

    java.math.BigInteger m = a.max(c).min(d.max(f).abs()).min(k);

    java.math.BigInteger n = d.max(e).max(f.max(k).abs()).min(f).abs();

    java.math.BigInteger o = java.math.BigInteger.valueOf(Math.getExponent(-2));

    java.math.BigInteger p = java.math.BigInteger.valueOf(Math.multiplyExact(1, 2));

    java.math.BigInteger q = java.math.BigInteger.valueOf(Math.addExact(10, 2));

    public static void main(String[] args) {
        java.math.BigInteger g = java.math.BigInteger.TEN.abs();
    }
}
