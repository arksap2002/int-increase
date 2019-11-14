public class ScannerWithClass {

    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        Scanner scanner1 = new Scanner();
        scanner1.printOne();
        java.math.BigInteger a = scanner.nextBigInteger();
        java.math.BigInteger b = scanner.nextBigInteger();
        String c = scanner.nextLine();
        double d = scanner.nextDouble();
        long e = scanner.nextLong();
        java.math.BigInteger f = new java.math.BigInteger(scanner1.nextInt());
    }

    static class Scanner {

        private void method() {
            java.util.Scanner scanner = new java.util.Scanner(System.in);
            java.math.BigInteger f = scanner.nextBigInteger();
            java.math.BigInteger g = new java.math.BigInteger(nextInt());
        }

        private void printOne() {
        }

        private int nextInt() {
            return 42;
        }
    }
}
