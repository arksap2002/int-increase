import java.io.IOException;
import java.util.Scanner;

public class PartialReplaceArithmetic {

    public static void main(String[] args) throws IOException {
        java.math.BigInteger a = java.math.BigInteger.TWO.add(new java.math.BigInteger(9)).add(new java.math.BigInteger(12).multiply(new java.math.BigInteger(8))).subtract(new java.math.BigInteger(200).divide(new java.math.BigInteger(100)));
        int b = 2 + 9 + 12 * 8 - 200 / 100;
        a = ((java.math.BigInteger.ONE.add(java.math.BigInteger.TWO)).multiply((new java.math.BigInteger(100).subtract(new java.math.BigInteger(12)))).divide((java.math.BigInteger.TWO.negate())).multiply((java.math.BigInteger.ONE.negate()))).remainder(new java.math.BigInteger(239));
        b = (5 + 6 + 6) * (2 + 3 + 9);
        a = java.math.BigInteger.ONE.subtract(a);
        a = java.math.BigInteger.TEN.add(a);
        b++;
        b *= 200;
        b = -(21 * 53);
        a = java.math.BigInteger.ONE.add((java.math.BigInteger.TWO.multiply(new java.math.BigInteger(3)))).subtract(new java.math.BigInteger(100).multiply((new java.math.BigInteger(12).subtract(new java.math.BigInteger(11))).negate())).subtract((new java.math.BigInteger(13).multiply((new java.math.BigInteger(12).divide(new java.math.BigInteger(3)))).add(java.math.BigInteger.ONE)).divide(new java.math.BigInteger(3)));
        java.math.BigInteger d = a.min(new java.math.BigInteger(b)).abs();
        int e = Math.abs(Math.min(90, 10));
    }
}
