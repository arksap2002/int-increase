import java.util.Scanner;

public class ScannerFromImport {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int b /* BigInteger */ = scanner.nextInt();
        int a = scanner.nextInt();
        String c = scanner.nextLine();
        double d = scanner.nextDouble();
        long e = scanner.nextLong();
    }
}
