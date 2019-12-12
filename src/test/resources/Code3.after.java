import java.util.Scanner;

public class Code3 {

    public static void main(String[] args) {
        // https://codeforces.com/contest/886/problem/B
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        java.math.BigInteger sum = java.math.BigInteger.ZERO;
        java.math.BigInteger[] a = new java.math.BigInteger[n];
        for (int aFilling1 = 0; aFilling1 < n; aFilling1++) {
            a[aFilling1] = java.math.BigInteger.ZERO;
        }
        java.math.BigInteger[] b = new java.math.BigInteger[200002];
        for (int bFilling1 = 0; bFilling1 < 200002; bFilling1++) {
            b[bFilling1] = java.math.BigInteger.ZERO;
        }
        for (int i = 0; i < 200002; i++) {
            b[i] = java.math.BigInteger.ZERO;
        }
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextBigInteger();
            b[a[i].intValue()] = java.math.BigInteger.ONE;
        }
        for (int i = 0; i < 200002; i++) {
            if (b[i].equals(java.math.BigInteger.ONE)) {
                sum = java.math.BigInteger.ONE.add(sum);
            }
        }
        for (int i = n - 1; i >= 0; i--) {
            if (sum.equals(java.math.BigInteger.ONE)) {
                break;
            }
            if (b[a[i].intValue()].equals(java.math.BigInteger.ONE)) {
                b[a[i].intValue()] = java.math.BigInteger.ZERO;
                sum = java.math.BigInteger.ONE.subtract(sum);
            }
        }
        for (int i = 0; i < 200001; i++) {
            if (b[i].equals(java.math.BigInteger.ONE)) {
                System.out.println(i);
                break;
            }
        }
    }
}
