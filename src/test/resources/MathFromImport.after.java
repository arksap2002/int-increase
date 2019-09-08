import java.lang.Math;

public class MathFromImport {

    java.math.BigInteger a = java.math.BigInteger.ONE.abs();

    java.math.BigInteger c = java.math.BigInteger.valueOf(9).min(java.math.BigInteger.TEN);

    java.math.BigInteger d = java.math.BigInteger.valueOf(98).negate().abs().abs();

    java.math.BigInteger e = java.math.BigInteger.valueOf(90).min(java.math.BigInteger.TEN.negate()).abs();

    java.math.BigInteger h = java.math.BigInteger.valueOf(100).negate().max(java.math.BigInteger.valueOf(9).negate()).min(java.math.BigInteger.ONE.max(java.math.BigInteger.TWO));

    java.math.BigInteger f = java.math.BigInteger.valueOf(100).negate().max(java.math.BigInteger.valueOf(9).negate()).min(java.math.BigInteger.ONE.max(java.math.BigInteger.TWO).abs()).abs();

    public static void main(String[] args) {
        java.math.BigInteger g = java.math.BigInteger.TEN.negate().abs();
    }
}
