import java.util.*;

public class Code5 {
    public static void main(String[] args) {
        //https://codeforces.com/contest/1/problem/A
        Scanner s = new Scanner(System.in);
        int /* BigInteger */ n = s.nextInt(), m = s.nextInt(), a = s.nextInt();
        System.out.print((n + a - 1) / a * ((m + a - 1) / a));
    }
}
