public class OtherTypeValues {

    public static void main(String[] args) {
        java.math.BigInteger a = java.math.BigInteger.ZERO;
        Integer integer = 100;
        long b = a.longValue();
        long b2 = integer.longValue();
        short c = a.shortValue();
        short c2 = integer.shortValue();
        byte d = a.byteValue();
        byte d2 = integer.byteValue();
        double e = a.doubleValue();
        double e2 = integer.doubleValue();
        float f = a.floatValue();
        float f2 = integer.floatValue();
    }
}
