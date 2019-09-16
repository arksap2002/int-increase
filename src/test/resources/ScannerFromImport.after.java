import java.util.Scanner;

public class ScannerFromImport {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        java.math.BigInteger b = scanner.nextBigInteger();
        String c = scanner.nextLine();
        double d = scanner.nextDouble();
        long e = scanner.nextLong();
        System.out.println(scanner.nextBigInteger());
    }
}
