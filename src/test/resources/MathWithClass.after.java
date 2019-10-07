public class MathWithClass {

    public static void main(String[] args) {
        java.math.BigInteger a = java.math.BigInteger.TEN.abs();
        java.math.BigInteger b = java.math.BigInteger.valueOf(90).max(java.math.BigInteger.valueOf(98));
        java.math.BigInteger c = java.math.BigInteger.valueOf(19).max(java.math.BigInteger.TEN).min(java.math.BigInteger.valueOf(89).max(java.math.BigInteger.TWO));
        java.math.BigInteger d = Math.abs(15);
        java.math.BigInteger e = Math.min(56, 67);
        java.math.BigInteger f = a.max(b).min(c.max(d));
        java.math.BigInteger g = c.max(d).max(e.max(f)).abs();
        java.math.BigInteger h = a.max(c).max(e.max(g)).min(b).abs();
        System.out.println(java.lang.Math.max(java.lang.Math.abs(10), 90));
    }

    static class Math {

        public static int abs(int number) {
            return number;
        }

        public static int min(int number, int number2) {
            return number + number2;
        }

        public static int max(int number, int number2) {
            return number + number2;
        }
    }
}
