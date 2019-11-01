import java.util.Scanner;

public class ScannerFromImport {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        java.math.BigInteger b = scanner.nextBigInteger();
        java.math.BigInteger a = scanner.nextBigInteger();
        String c = scanner.nextLine();
        double d = scanner.nextDouble();
        long e = scanner.nextLong();
    }
}
