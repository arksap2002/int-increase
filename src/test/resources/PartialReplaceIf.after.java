import java.io.IOException;
import java.util.Scanner;

public class PartialReplaceIf {

    public static void main(String[] args) throws IOException {
        java.math.BigInteger a = java.math.BigInteger.TWO;
        int b = 9;
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
