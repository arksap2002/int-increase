public class OtherTypeValues {
    public static void main(String[] args) {
        int /* BigInteger */ a = 0;
        Integer integer = 100;
        long b = Integer.valueOf(Integer.valueOf(a).intValue()).longValue();
        long b2 = integer.longValue();
        short c = Integer.valueOf(a).shortValue();
        short c2 = integer.shortValue();
        byte d = Integer.valueOf(a).byteValue();
        byte d2 = integer.byteValue();
        double e = Integer.valueOf(a).doubleValue();
        double e2 = integer.doubleValue();
        float f = Integer.valueOf(a).floatValue();
        float f2 = integer.floatValue();
    }
}
