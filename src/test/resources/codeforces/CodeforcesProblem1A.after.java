import java.util.*;

public class CodeforcesProblem1A {

    public static void main(String[] args) {
        // https://codeforces.com/contest/1/problem/A
        Scanner s = new Scanner(System.in);
        java.math.BigInteger n = s.nextBigInteger(), m = s.nextBigInteger(), a = s.nextBigInteger();
        System.out.print((n.add(a).subtract(java.math.BigInteger.ONE)).divide(a).multiply(((m.add(a).subtract(java.math.BigInteger.ONE)).divide(a))));
    }
}
