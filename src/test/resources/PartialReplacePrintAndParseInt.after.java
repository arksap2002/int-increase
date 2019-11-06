import java.io.IOException;
import java.util.Scanner;

public class PartialReplacePrintAndParseInt {

    public static void main(String[] args) throws IOException {
        java.math.BigInteger a = java.math.BigInteger.TWO.add(java.math.BigInteger.valueOf(9)).add(java.math.BigInteger.valueOf(12).multiply(java.math.BigInteger.valueOf(8))).subtract(java.math.BigInteger.valueOf(200).divide(java.math.BigInteger.valueOf(100)));
        int b = 2 + 9 + 12 * 8 - 200 / 100;
        String number = "10";
        String number1 = "11";
        java.math.BigInteger integer = new java.math.BigInteger(number);
        int integer1 = Integer.parseInt(number1);
        java.io.PrintWriter printWriter = new java.io.PrintWriter("output.out");
        printWriter.print(a.subtract(java.math.BigInteger.valueOf(b)));
        System.out.println(a.add(java.math.BigInteger.valueOf(b)));
        java.math.BigInteger f;
        int g;
        Scanner scanner = new Scanner(System.in);
        b = scanner.nextInt();
        a = scanner.nextBigInteger();
        String string = a.toString();
        string = Integer.toString(b);
    }
}
