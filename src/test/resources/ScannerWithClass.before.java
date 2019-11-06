public class ScannerWithClass {

    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        Scanner scanner1 = new Scanner();
        scanner1.printOne();
        int /* BigInteger */ a = scanner.nextInt();
        int /* BigInteger */ b = scanner.nextInt();
        String c = scanner.nextLine();
        double d = scanner.nextDouble();
        long e = scanner.nextLong();
        int /* BigInteger */ f = scanner1.nextInt();
    }

    static class Scanner {

        private void method() {
            java.util.Scanner scanner = new java.util.Scanner(System.in);
            int /* BigInteger */ f = scanner.nextInt();
            int /* BigInteger */ g = nextInt();
        }

        private void printOne() {}

        private int nextInt() {
            return 42;
        }
    }
}
