import java.util.*;

public class Code10 {

    public static void main(String[] args) {
        // https://codeforces.com/problemset/problem/6/C
        java.math.BigInteger n, a = java.math.BigInteger.ZERO, b = java.math.BigInteger.ZERO, j, k, t[] = new java.math.BigInteger[1000005];
        for (int tFilling1 = 0; tFilling1 < 1000005; tFilling1++) {
            t[tFilling1] = java.math.BigInteger.ZERO;
        }
        Scanner sc = new Scanner(System.in);
        n = sc.nextBigInteger();
        for (int i = 0; java.math.BigInteger.valueOf(i).compareTo(n) < 0; i++) {
            t[i] = sc.nextBigInteger();
        }
        j = java.math.BigInteger.ZERO;
        k = n.subtract(java.math.BigInteger.ONE);
        while (j.compareTo(k) <= 0) {
            if (a.compareTo(b) <= 0) {
//                a = a.add(t[j = j.add(java.math.BigInteger.ONE).intValue()]);
            } else {
//                b = b.add(t[k = k.subtract(java.math.BigInteger.ONE).intValue()]);
            }
        }
        System.out.println(j + " " + (n.subtract(j)));
        sc.close();
    }
}
