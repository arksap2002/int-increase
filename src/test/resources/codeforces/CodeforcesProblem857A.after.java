import java.util.Scanner;

public class CodeforcesProblem857A {

    public static void main(String[] args) {
        // https://codeforces.com/contest/857/problem/A
        Scanner in = new Scanner(System.in);
        java.math.BigInteger n = in.nextBigInteger();
        if (n.remainder(java.math.BigInteger.valueOf(3)).equals(java.math.BigInteger.ZERO)) {
            System.out.println(n.divide(java.math.BigInteger.valueOf(3)).subtract(java.math.BigInteger.ONE) + " " + n.divide(java.math.BigInteger.valueOf(3)) + " " + (n.divide(java.math.BigInteger.valueOf(3)).add(java.math.BigInteger.ONE)));
        } else {
            System.out.println(-1);
        }
    }
}
