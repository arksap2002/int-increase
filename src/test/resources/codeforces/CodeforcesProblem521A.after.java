import java.util.Scanner;

public class CodeforcesProblem521A {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // https://codeforces.com/contest/521/submission/66887430
        java.math.BigInteger n = sc.nextBigInteger();
        String s = sc.next();
        java.math.BigInteger[] count = new java.math.BigInteger[128];
        for (int countFilling1 = 0; countFilling1 < 128; countFilling1++) {
            count[countFilling1] = java.math.BigInteger.ZERO;
        }
        for (int i = 0; java.math.BigInteger.valueOf(i).compareTo(n) < 0; i++) {
            count[s.charAt(i)] = count[s.charAt(i)].add(java.math.BigInteger.ONE);
        }
        java.math.BigInteger max = java.math.BigInteger.ZERO;
        java.math.BigInteger maxnum = java.math.BigInteger.ZERO;
        for (int i = 0; i < 128; i++) {
            if (count[i].compareTo(max) > 0) {
                max = count[i];
                maxnum = java.math.BigInteger.ONE;
            } else if (count[i].equals(max)) {
                maxnum = maxnum.add(java.math.BigInteger.ONE);
            }
        }
        java.math.BigInteger ans = java.math.BigInteger.ONE;
        for (int i = 0; java.math.BigInteger.valueOf(i).compareTo(n) < 0; i++) {
            ans = ans.multiply(maxnum).remainder(java.math.BigInteger.valueOf(1000000007));
        }
        System.out.println(ans);
    }
}