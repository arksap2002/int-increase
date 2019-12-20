import java.util.*;

public class CodeforcesProblem1A {
    public static void main(String[] args) {
        //https://codeforces.com/contest/1/submission/66886960
        Scanner s = new Scanner(System.in);
        int /* BigInteger */ n = s.nextInt(), m = s.nextInt(), a = s.nextInt();
        System.out.print((n + a - 1) / a * ((m + a - 1) / a));
    }
}
