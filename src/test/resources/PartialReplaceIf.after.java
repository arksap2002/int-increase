import java.io.IOException;
import java.util.Scanner;

public class PartialReplaceIf {

    public static void main(String[] args) throws IOException {
        java.math.BigInteger a = java.math.BigInteger.TWO.add(java.math.BigInteger.valueOf(9)).add(java.math.BigInteger.valueOf(12).multiply(java.math.BigInteger.valueOf(8))).subtract(java.math.BigInteger.valueOf(200).divide(java.math.BigInteger.valueOf(100)));
        int b = 2 + 9 + 12 * 8 - 200 / 100;
        if (b > 10) {
            b = 10;
        }
        if (a.compareTo(java.math.BigInteger.valueOf(5).subtract(java.math.BigInteger.TWO).min(java.math.BigInteger.valueOf(3).negate())) > 0) {
            a = java.math.BigInteger.valueOf(3).negate();
        }
        if (java.math.BigInteger.valueOf(b).compareTo(a) > 0) {
            a = java.math.BigInteger.valueOf(b);
        }
    }
}
