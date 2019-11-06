import java.io.IOException;
import java.util.Scanner;

public class PartialReplacePrintAndParseInt {
    public static void main(String[] args) throws IOException {
        int /* BigInteger */ a = 2 + 9 + 12 * 8 - 200 / 100;
        int b = 2 + 9 + 12 * 8 - 200 / 100;
        String number = "10";
        String number1 = "11";
        int /* BigInteger */ integer = Integer.parseInt(number);
        int integer1 = Integer.parseInt(number1);
        java.io.PrintWriter printWriter = new java.io.PrintWriter("output.out");
        printWriter.print(a - b);
        System.out.println(a + b);
        int /* BigInteger */ f;
        int g;
        Scanner scanner = new Scanner(System.in);
        b = scanner.nextInt();
        a = scanner.nextInt();
        String string = Integer.toString(a);
        string = Integer.toString(b);
    }
}