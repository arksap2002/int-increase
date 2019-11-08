import java.io.IOException;
import java.util.Scanner;

public class PartialReplacePrint {

    public static void main(String[] args) throws IOException {
        java.math.BigInteger a = java.math.BigInteger.TWO;
        int b = 9;
        java.io.PrintWriter printWriter = new java.io.PrintWriter("output.out");
        printWriter.print(a.subtract(java.math.BigInteger.valueOf(b)));
        System.out.println(a.add(java.math.BigInteger.valueOf(b)));
        Scanner scanner = new Scanner(System.in);
        b = scanner.nextInt();
        a = scanner.nextBigInteger();
    }
}
