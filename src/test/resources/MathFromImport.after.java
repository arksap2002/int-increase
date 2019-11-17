import java.lang.Math;

public class MathFromImport {

    java.math.BigInteger a = java.math.BigInteger.ONE.abs();

    java.math.BigInteger c = new java.math.BigInteger(9).min(java.math.BigInteger.TEN);

    java.math.BigInteger d = new java.math.BigInteger(98).abs().abs();

    java.math.BigInteger e = new java.math.BigInteger(90).min(java.math.BigInteger.TEN).abs();

    java.math.BigInteger h = new java.math.BigInteger(100).max(new java.math.BigInteger(9)).min(java.math.BigInteger.ONE.max(java.math.BigInteger.TWO));

    java.math.BigInteger f = new java.math.BigInteger(100).max(new java.math.BigInteger(9)).min(java.math.BigInteger.ONE.max(java.math.BigInteger.TWO).abs()).abs();

    java.math.BigInteger k = a.max(c).min(c.max(d).abs()).abs();

    java.math.BigInteger m = a.max(c).min(d.max(f).abs()).min(k);

    java.math.BigInteger n = d.max(e).max(f.max(k).abs()).min(f).abs();

    public static void main(String[] args) {
        java.math.BigInteger g = java.math.BigInteger.TEN.abs();
    }
}
