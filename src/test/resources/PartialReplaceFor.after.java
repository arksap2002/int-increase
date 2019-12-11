import java.io.IOException;
import java.util.Scanner;

public class PartialReplaceFor {

    public static void main(String[] args) throws IOException {
        for (java.math.BigInteger i = java.math.BigInteger.ZERO; i.compareTo(java.math.BigInteger.valueOf(200)) < 0; i = java.math.BigInteger.ONE.add(i)) {
            System.out.println(-1);
        }
        for (int i = 0; i < 200; i++) {
            System.out.println(-1);
        }
        java.math.BigInteger i = java.math.BigInteger.ZERO;
        while (i.compareTo(java.math.BigInteger.valueOf(100)) < 0) {
            System.out.println(-1);
            i = java.math.BigInteger.ONE.add(i);
        }
    }
}
