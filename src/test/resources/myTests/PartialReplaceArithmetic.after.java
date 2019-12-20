import java.io.IOException;
import java.util.Scanner;

public class PartialReplaceArithmetic {

    public static void main(String[] args) throws IOException {
        java.math.BigInteger a = java.math.BigInteger.TWO.add(java.math.BigInteger.valueOf(9)).add(java.math.BigInteger.valueOf(12).multiply(java.math.BigInteger.valueOf(8))).subtract(java.math.BigInteger.valueOf(200).divide(java.math.BigInteger.valueOf(100)));
        int b = 2 + 9 + 12 * 8 - 200 / 100;
        a = ((java.math.BigInteger.ONE.add(java.math.BigInteger.TWO)).multiply((java.math.BigInteger.valueOf(100).subtract(java.math.BigInteger.valueOf(12)))).divide((java.math.BigInteger.TWO.negate())).multiply((java.math.BigInteger.ONE.negate()))).remainder(java.math.BigInteger.valueOf(239));
        b = (5 + 6 + 6) * (2 + 3 + 9);
        a = a.subtract(java.math.BigInteger.ONE);
        a = a.add(java.math.BigInteger.TEN);
        b++;
        b *= 200;
        b = -(21 * 53);
        a = java.math.BigInteger.ONE.add((java.math.BigInteger.TWO.multiply(java.math.BigInteger.valueOf(3)))).subtract(java.math.BigInteger.valueOf(100).multiply((java.math.BigInteger.valueOf(12).subtract(java.math.BigInteger.valueOf(11))).negate())).subtract((java.math.BigInteger.valueOf(13).multiply((java.math.BigInteger.valueOf(12).divide(java.math.BigInteger.valueOf(3)))).add(java.math.BigInteger.ONE)).divide(java.math.BigInteger.valueOf(3)));
        java.math.BigInteger d = a.min(java.math.BigInteger.valueOf(b)).abs();
        int e = Math.abs(Math.min(90, 10));
    }
}
