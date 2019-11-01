public class ScannerWithClass {

    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        Scanner scanner1 = new Scanner();
        scanner1.printOne();
        int a /* BigInteger */ = scanner.nextInt();
        int b /* BigInteger */ = scanner.nextInt();
        String c = scanner.nextLine();
        double d = scanner.nextDouble();
        long e = scanner.nextLong();
        int f /* BigInteger */ = scanner1.nextInt();
    }

    static class Scanner {

        private void method() {
            java.util.Scanner scanner = new java.util.Scanner(System.in);
            int f /* BigInteger */ = scanner.nextInt();
            int g /* BigInteger */ = nextInt();
        }

        private void printOne() {}

        private int nextInt() {
            return 42;
        }
    }
}
