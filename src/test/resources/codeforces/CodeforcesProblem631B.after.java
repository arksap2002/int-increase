import java.util.*;

public class Code1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // https://codeforces.com/contest/631/submission/66743397
        java.math.BigInteger n, m, k, a, b, c;
        n = scanner.nextBigInteger();
        m = scanner.nextBigInteger();
        k = scanner.nextBigInteger();
        java.math.BigInteger[] x = new java.math.BigInteger[500000];
        for (int xFilling1 = 0; xFilling1 < 500000; xFilling1++) {
            x[xFilling1] = java.math.BigInteger.ZERO;
        }
        java.math.BigInteger[] y = new java.math.BigInteger[500000];
        for (int yFilling1 = 0; yFilling1 < 500000; yFilling1++) {
            y[yFilling1] = java.math.BigInteger.ZERO;
        }
        java.math.BigInteger[] ans = new java.math.BigInteger[500000];
        for (int ansFilling1 = 0; ansFilling1 < 500000; ansFilling1++) {
            ans[ansFilling1] = java.math.BigInteger.ZERO;
        }
        for (int i = 1; java.math.BigInteger.valueOf(i).compareTo(k) <= 0; i++) {
            a = scanner.nextBigInteger();
            b = scanner.nextBigInteger();
            ans[i] = scanner.nextBigInteger();
            if (a.equals(java.math.BigInteger.ONE))
                x[b.subtract(java.math.BigInteger.ONE).intValue()] = java.math.BigInteger.valueOf(i);
            else
                y[b.subtract(java.math.BigInteger.ONE).intValue()] = java.math.BigInteger.valueOf(i);
        }
        for (java.math.BigInteger i = java.math.BigInteger.ZERO; i.compareTo(n) < 0; i = i.add(java.math.BigInteger.ONE)) {
            for (java.math.BigInteger j = java.math.BigInteger.ZERO; j.compareTo(m) < 0; j = j.add(java.math.BigInteger.ONE)) {
                System.out.print(ans[x[i.intValue()].max(y[j.intValue()]).intValue()] + " ");
            }
            System.out.println();
        }
    }
}
