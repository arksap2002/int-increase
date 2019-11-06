import java.io.IOException;
import java.util.Scanner;

public class PartialReplaceIf {
    public static void main(String[] args) throws IOException {
        int /* BigInteger */ a = 2 + 9 + 12 * 8 - 200 / 100;
        int b = 2 + 9 + 12 * 8 - 200 / 100;
        if (b > 10) {
            b = 10;
        }
        if (a > Math.min(5 - 2, -3)) {
            a = -3;
        }
        if (b > a) {
            a = b;
        }
    }
}