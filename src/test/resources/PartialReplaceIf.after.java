import java.io.IOException;
import java.util.Scanner;

public class PartialReplaceIf {

    public static void main(String[] args) throws IOException {
        java.math.BigInteger a = java.math.BigInteger.TWO;
        int b = 9;
        if (b > 10) {
            b = 10;
        }
        if (a.compareTo(new java.math.BigInteger(5).subtract(java.math.BigInteger.TWO).min(new java.math.BigInteger(3).negate())) > 0) {
            a = new java.math.BigInteger(3).negate();
        }
        if (new java.math.BigInteger(b).compareTo(a) > 0) {
            a = new java.math.BigInteger(b);
        }
    }
}
