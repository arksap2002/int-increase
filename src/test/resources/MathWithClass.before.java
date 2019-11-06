public class MathWithClass {

    public static void main(String[] args) {
        int /* BigInteger */ a = java.lang.Math.abs(10);
        int /* BigInteger */ b = java.lang.Math.max(90, 98);
        int /* BigInteger */ c = java.lang.Math.max(10, java.lang.Math.min(89, 2));
        int /* BigInteger */ d = java.lang.Math.abs(15);
        int /* BigInteger */ e = java.lang.Math.min(56, 67);
        int /* BigInteger */ f = java.lang.Math.min(java.lang.Math.max(a, b), java.lang.Math.max(c, d));
        int /* BigInteger */ g = java.lang.Math.abs(java.lang.Math.max(java.lang.Math.max(c, d), java.lang.Math.max(e, f)));
        int /* BigInteger */ h = java.lang.Math.abs(java.lang.Math.min(java.lang.Math.max(java.lang.Math.max(a, c), java.lang.Math.max(e, g)), b));
        int /* BigInteger */ j = java.lang.Math.getExponent(-2);
        int /* BigInteger */ k = java.lang.Math.multiplyExact(1, 2);
        int /* BigInteger */ l = java.lang.Math.addExact(10, 2);
        System.out.println(java.lang.Math.max(java.lang.Math.abs(10), 90));
        System.out.println(java.lang.Math.max(java.lang.Math.abs(10), f));
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
