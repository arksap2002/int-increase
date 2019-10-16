import java.io.IOException;
import java.io.PrintWriter;

public class Print {

    public static void main(String[] args) throws IOException {
        java.io.PrintWriter printWriter = new java.io.PrintWriter("output.out");
        java.math.BigInteger a = java.math.BigInteger.TWO;
        java.math.BigInteger b = java.math.BigInteger.TWO;
        printWriter.println(a.add(b));
        printWriter.print(a.subtract(b));
        System.out.println(a.add(b));
        System.out.print(a.multiply(b));
        PrintWriter printWriter1 = new PrintWriter();
        printWriter1.println(100);
        printWriter.close();
    }

    static class PrintWriter {

        public static void println(int number) {
        }
    }
}
