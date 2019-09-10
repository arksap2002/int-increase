public class MathWithClass {

    public static void main(String[] args) {
        int a = java.lang.Math.abs(-10);
        int b = java.lang.Math.max(90, 98);
        int c = java.lang.Math.min(java.lang.Math.max(-19, -10), java.lang.Math.max(89, -2));
        int d = Math.abs(15);
        int e = Math.min(56, 67);
        int f = java.lang.Math.min(java.lang.Math.max(a, b), java.lang.Math.max(c, d));
        int g = java.lang.Math.abs(java.lang.Math.max(java.lang.Math.max(c, d), java.lang.Math.max(e, f)));
        int h = java.lang.Math.abs(java.lang.Math.min(java.lang.Math.max(java.lang.Math.max(a, c), java.lang.Math.max(e, g)), b));
        System.out.println(java.lang.Math.max(java.lang.Math.abs(-10), 90));
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
