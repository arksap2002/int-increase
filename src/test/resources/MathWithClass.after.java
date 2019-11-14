public class MathWithClass {

    public static void main(String[] args) {
        java.math.BigInteger a = java.math.BigInteger.TEN.abs();
        java.math.BigInteger b = java.math.BigInteger.valueOf(90).max(java.math.BigInteger.valueOf(98));
        java.math.BigInteger c = java.math.BigInteger.TEN.max(java.math.BigInteger.valueOf(89).min(java.math.BigInteger.TWO));
        java.math.BigInteger d = java.math.BigInteger.valueOf(15).abs();
        java.math.BigInteger e = java.math.BigInteger.valueOf(56).min(java.math.BigInteger.valueOf(67));
        java.math.BigInteger f = a.max(b).min(c.max(d));
        java.math.BigInteger g = c.max(d).max(e.max(f)).abs();
        java.math.BigInteger h = a.max(c).max(e.max(g)).min(b).abs();
        java.math.BigInteger j = new java.math.BigInteger(java.lang.Math.getExponent(-2));
        java.math.BigInteger k = new java.math.BigInteger(java.lang.Math.multiplyExact(1, 2));
        java.math.BigInteger l = new java.math.BigInteger(java.lang.Math.addExact(10, 2));
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
