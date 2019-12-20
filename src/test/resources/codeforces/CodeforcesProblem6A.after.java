import java.util.Scanner;

public class CodeforcesProblem6A {

    public static void main(String[] args) {
        // https://codeforces.com/contest/6/submission/66887362
        Scanner in = new Scanner(System.in);
        java.math.BigInteger[] a = new java.math.BigInteger[4];
        for (int aFilling1 = 0; aFilling1 < 4; aFilling1++) {
            a[aFilling1] = java.math.BigInteger.ZERO;
        }
        for (int i = 0; i < 4; ++i) a[i] = in.nextBigInteger();
        java.util.Arrays.sort(a);
        if (a[0].add(a[1]).compareTo(a[2]) > 0 || a[1].add(a[2]).compareTo(a[3]) > 0)
            System.out.println("TRIANGLE");
        else if (a[0].add(a[1]).equals(a[2]) || a[1].add(a[2]).equals(a[3]))
            System.out.println("SEGMENT");
        else
            System.out.println("IMPOSSIBLE");
    }
}
