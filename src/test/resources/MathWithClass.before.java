public class MathWithClass {

    public static void main(String[] args) {
        int a = java.lang.Math.abs(-10);
        int b = java.lang.Math.max(90, 98);
        int c = java.lang.Math.min(java.lang.Math.max(-19, -10), java.lang.Math.max(89, -2));
        int d = Math.abs(15);
        int e = Math.min(56, 67);
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
