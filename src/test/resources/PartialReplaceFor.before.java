import java.io.IOException;
import java.util.Scanner;

public class PartialReplaceFor {
    public static void main(String[] args) throws IOException {
        for (int /* BigInteger */ i = 0; i < 200; i++) {
            System.out.println(-1);
        }
        for (int i = 0; i < 200; i++) {
            System.out.println(-1);
        }
        int /* BigInteger */ i = 0;
        while (i < 100) {
            System.out.println(-1);
            i++;
        }
    }
}
