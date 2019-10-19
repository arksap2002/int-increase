import java.io.IOException;
import java.io.PrintWriter;

public class Print {
    public static void main(String[] args) throws IOException {
        java.io.PrintWriter printWriter = new java.io.PrintWriter("output.out");
        int a /* BigInteger */ = 2;
        int b = 2;
        printWriter.println(a + b);
        printWriter.print(a - b);
        System.out.println(a + b);
        System.out.print(a * b);
        PrintWriter printWriter1 = new PrintWriter();
        printWriter1.println(100);
        printWriter.close();
    }

    static class PrintWriter {
        public static void println(int number) {}
    }
}
