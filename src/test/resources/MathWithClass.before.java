public class MathWithClass {

    public static void main(String[] args) {
        int a = java.lang.Math.abs(-10);
        int b = java.lang.Math.max(90, a);
        int c = -java.lang.Math.min(java.lang.Math.max(b, a), java.lang.Math.max(89, a));
        int d = Math.abs(15);
        int e = Math.min(56, 67);
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
