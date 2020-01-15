import java.io.IOException;
import java.util.Scanner;

public class PartialReplaceArithmetic {
    public static void main(String[] args) throws IOException {
        int /* BigInteger */ a = 2 + 9 + 12 * 8 - 200 / 100;
        int b = 2 + 9 + 12 * 8 - 200 / 100;
        a = ((1 + 2) * (100 - 12) / (-2) * (-1)) % 239;
        b = (5 + 6 + 6) * (2 + 3 + 9);
        a--;
        a += 10;
        b++;
        b *= 200;
        b = -(21 * 53);
        a = 1 + (2 * 3) - 100 * -(12 - 11) - (+13 * +(12 / 3) + 1) / 3;
        int /* BigInteger */ d = Math.abs(Math.min(a, b));
        int e = Math.abs(Math.min(90, 10));
    }
}
