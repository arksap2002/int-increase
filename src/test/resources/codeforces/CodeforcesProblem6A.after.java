import java.util.*;
import java.lang.*;

public class CodeforcesProblem6A {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        java.math.BigInteger[] a = new java.math.BigInteger[4];
        for (int aFilling1 = 0; aFilling1 < 4; aFilling1++) {
            a[aFilling1] = java.math.BigInteger.ZERO;
        }
        java.math.BigInteger i;
        for (i = java.math.BigInteger.ZERO; i.compareTo(java.math.BigInteger.valueOf(4)) < 0; i = i.add(java.math.BigInteger.ONE)) {
            a[i.intValue()] = scan.nextBigInteger();
        }
        Arrays.sort(a);
        if (a[0].add(a[1]).compareTo(a[2]) > 0 || a[1].add(a[2]).compareTo(a[3]) > 0) {
            System.out.println("TRIANGLE");
        } else if (a[0].add(a[1]).equals(a[2]) || a[1].add(a[2]).equals(a[3])) {
            System.out.println("SEGMENT");
        } else {
            System.out.println("IMPOSSIBLE");
        }
    }
}
