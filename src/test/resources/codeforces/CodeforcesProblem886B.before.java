import java.util.Scanner;

public class CodeforcesProblem886B {

    static Scanner read = new Scanner(System.in);

    public static void main(String[] args) {
        java.math.BigInteger n = read.nextBigInteger();
        java.math.BigInteger a;
        java.math.BigInteger[] t = new java.math.BigInteger[200001];
        for (int tFilling1 = 0; tFilling1 < 200001; tFilling1++) {
            t[tFilling1] = java.math.BigInteger.ZERO;
        }
        java.math.BigInteger max = java.math.BigInteger.ZERO;
        for (java.math.BigInteger i = java.math.BigInteger.ONE; i.compareTo(n) <= 0; i = i.add(java.math.BigInteger.ONE)) {
            a = read.nextBigInteger();
            t[a.intValue()] = i;
            if (a.compareTo(max) > 0)
                max = a;
        }
        java.math.BigInteger min = java.math.BigInteger.valueOf(200002);
        java.math.BigInteger imin = java.math.BigInteger.ZERO;
        for (java.math.BigInteger i = java.math.BigInteger.ZERO; i.compareTo(max) <= 0; i = i.add(java.math.BigInteger.ONE)) {
            if (!t[i.intValue()].equals(java.math.BigInteger.ZERO) && t[i.intValue()].compareTo(min) < 0) {
                min = t[i.intValue()];
                imin = i;
            }
        }
        System.out.println(imin);
    }
}
