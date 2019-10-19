public class MathWithClass {

    public static void main(String[] args) {
        int a = java.lang.Math.abs(10);
        int b = java.lang.Math.max(90, 98);
        java.math.BigInteger c = java.math.BigInteger.TEN.max(java.math.BigInteger.valueOf(89).min(java.math.BigInteger.TWO));
        int d = java.lang.Math.abs(15);
        int e = java.lang.Math.min(56, 67);
        java.math.BigInteger f = java.math.BigInteger.valueOf(a).max(java.math.BigInteger.valueOf(b)).min(c.max(java.math.BigInteger.valueOf(d)));
        java.math.BigInteger g = c.max(java.math.BigInteger.valueOf(d)).max(java.math.BigInteger.valueOf(e).max(f)).abs();
        java.math.BigInteger h = java.math.BigInteger.valueOf(a).max(c).max(java.math.BigInteger.valueOf(e).max(g)).min(java.math.BigInteger.valueOf(b)).abs();
        System.out.println(java.lang.Math.max(java.lang.Math.abs(10), 90));
        System.out.println(java.math.BigInteger.TEN.abs().max(f));
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
