public class MathWithClass {

    public static void main(String[] args) {
        java.math.BigInteger a = java.math.BigInteger.TEN.negate().abs();
        java.math.BigInteger b = java.math.BigInteger.valueOf(90).max(java.math.BigInteger.valueOf(98));
        java.math.BigInteger c = java.math.BigInteger.valueOf(19).negate().max(java.math.BigInteger.TEN.negate()).min(java.math.BigInteger.valueOf(89).max(java.math.BigInteger.TWO.negate()));
        java.math.BigInteger d = Math.abs(15);
        java.math.BigInteger e = Math.min(56, 67);
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
