public class ScannerWithClass {

    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        Scanner scanner1 = new Scanner();
        scanner1.printOne();
        int a = scanner.nextInt();
        java.math.BigInteger b = scanner.nextBigInteger();
        String c = scanner.nextLine();
        double d = scanner.nextDouble();
        long e = scanner.nextLong();
        int f = scanner1.nextInt();
    }

    static class Scanner {

        private void method() {
            java.util.Scanner scanner = new java.util.Scanner(System.in);
            int f = scanner.nextInt();
            int g = nextInt();
        }

        private void printOne() {
        }

        private int nextInt() {
            return 42;
        }
    }
}
