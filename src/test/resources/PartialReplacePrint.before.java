import java.io.IOException;
import java.util.Scanner;

public class PartialReplacePrint {
    public static void main(String[] args) throws IOException {
        int /* BigInteger */ a = 2;
        int b = 9;
        java.io.PrintWriter printWriter = new java.io.PrintWriter("output.out");
        printWriter.print(a - b);
        System.out.println(a + b);
        Scanner scanner = new Scanner(System.in);
        b = scanner.nextInt();
        a = scanner.nextInt();
    }
}
