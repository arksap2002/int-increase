import java.io.IOException;
import java.io.PrintWriter;

public class Print {

    public static void main(String[] args) throws IOException {
        java.io.PrintWriter printWriter = new java.io.PrintWriter("output.out");
        java.math.BigInteger a = java.math.BigInteger.TWO;
        int b = 2;
        printWriter.println(a.add(java.math.BigInteger.valueOf(b)));
        printWriter.print(a.subtract(java.math.BigInteger.valueOf(b)));
        System.out.println(a.add(java.math.BigInteger.valueOf(b)));
        System.out.print(a.multiply(java.math.BigInteger.valueOf(b)));
        PrintWriter printWriter1 = new PrintWriter();
        printWriter1.println(100);
        printWriter.close();
    }

    static class PrintWriter {

        public static void println(int number) {
        }
    }
}
