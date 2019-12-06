import java.util.*;

public class Test {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        java.math.BigInteger n, m, k, a, b, c;
        n = scanner.nextBigInteger();
        m = scanner.nextBigInteger();
        k = scanner.nextBigInteger();
        java.math.BigInteger[] x = new java.math.BigInteger[500000];
        java.math.BigInteger[] y = new java.math.BigInteger[500000];
        java.math.BigInteger[] ans = new java.math.BigInteger[500000];
        for (int i = 0; i < 500000; i++) {
            x[i] = java.math.BigInteger.ZERO;
            y[i] = java.math.BigInteger.ZERO;
            ans[i] = java.math.BigInteger.ZERO;
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
        for (java.math.BigInteger i = java.math.BigInteger.ZERO; i.compareTo(n) < 0; i = java.math.BigInteger.ONE.add(i)) {
            for (java.math.BigInteger j = java.math.BigInteger.ZERO; j.compareTo(m) < 0; j = java.math.BigInteger.ONE.add(j)) {
                System.out.print(ans[x[i.intValue()].max(y[j.intValue()]).intValue()] + " ");
            }
            System.out.println();
        }
    }
}
