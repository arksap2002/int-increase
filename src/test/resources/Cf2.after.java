import java.util.Scanner;

public class Cf2 {

    public static void main(String[] args) {
        // https://codeforces.com/contest/886/problem/B
        Scanner scanner = new Scanner(System.in);
        java.math.BigInteger n = scanner.nextBigInteger();
        java.math.BigInteger sum = java.math.BigInteger.ZERO;
        java.math.BigInteger[] a = new java.math.BigInteger[n.intValue()];
        java.math.BigInteger[] b = new java.math.BigInteger[200002];
        for (int i = 0; i < 200002; i++) {
            b[i] = java.math.BigInteger.ZERO;
        }
        for (int i = 0; new java.math.BigInteger(i).compareTo(n) < 0; i++) {
            a[i] = scanner.nextBigInteger();
            b[a[i]] = java.math.BigInteger.ONE;
        }
        for (int i = 0; i < 200002; i++) {
            if (b[i] == 1) {
                sum = java.math.BigInteger.ONE.add(sum);
            }
        }
        for (int i = n.subtract(java.math.BigInteger.ONE).intValue(); i >= 0; i--) {
            if (sum.equals(java.math.BigInteger.ONE)) {
                break;
            }
            if (b[a[i]] == 1) {
                b[a[i]] = java.math.BigInteger.ZERO;
                sum = java.math.BigInteger.ONE.subtract(sum);
            }
        }
        for (int i = 0; i < 200001; i++) {
            if (b[i] == 1) {
                System.out.println(i);
                break;
            }
        }
    }
}
