public class OtherTypeValues {

    public static void main(String[] args) {
        java.math.BigInteger a = java.math.BigInteger.ZERO;
        Integer integer = 100;
        long b = java.math.BigInteger.valueOf(Integer.valueOf(a)).longValue();
        long b2 = java.math.BigInteger.valueOf(integer).longValue();
        short c = java.math.BigInteger.valueOf(Integer.valueOf(a)).shortValue();
        short c2 = java.math.BigInteger.valueOf(integer).shortValue();
        byte d = java.math.BigInteger.valueOf(Integer.valueOf(a)).byteValue();
        byte d2 = java.math.BigInteger.valueOf(integer).byteValue();
        double e = java.math.BigInteger.valueOf(Integer.valueOf(a)).doubleValue();
        double e2 = java.math.BigInteger.valueOf(integer).doubleValue();
        float f = java.math.BigInteger.valueOf(Integer.valueOf(a)).floatValue();
        float f2 = java.math.BigInteger.valueOf(integer).floatValue();
    }
}
