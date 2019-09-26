import java.lang.Math;

public class MathFromImport {

    java.math.BigInteger a = Math.abs(java.math.BigInteger.ONE);

    java.math.BigInteger c = java.math.BigInteger.valueOf(9).min(java.math.BigInteger.TEN);

    java.math.BigInteger d = Math.abs(Math.abs(-98));

    java.math.BigInteger e = Math.abs(java.math.BigInteger.valueOf(90).min(-10));

    java.math.BigInteger h = -100.max(-9).min(java.math.BigInteger.ONE.max(java.math.BigInteger.TWO));

    java.math.BigInteger f = Math.abs(-100.max(-9).min(Math.abs(java.math.BigInteger.ONE.max(java.math.BigInteger.TWO))));

    java.math.BigInteger k = Math.abs(a.max(c).min(Math.abs(c.max(d))));

    java.math.BigInteger m = a.max(c).min(Math.abs(d.max(f))).min(k);

    java.math.BigInteger n = Math.abs(d.max(e).max(Math.abs(f.max(k))).min(f));

    public static void main(String[] args) {
        java.math.BigInteger g = Math.abs(-10);
    }
}
