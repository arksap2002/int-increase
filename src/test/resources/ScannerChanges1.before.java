public class ScannerChanges1 {

    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        Scanner scanner1 = new Scanner();
        scanner1.printOne();
        java.math.BigInteger a = scanner.nextBigInteger();
        int b = scanner.nextInt();
        String c = scanner.nextLine();
        double d = scanner.nextDouble();
        long e = scanner.nextLong();
    }

    static class Scanner {

        private void method() {
            java.util.Scanner scanner = new java.util.Scanner(System.in);
            int f = scanner.nextInt();
        }

        private void printOne() {
            System.out.println(1);
        }
    }
}
