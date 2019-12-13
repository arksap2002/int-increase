import java.util.*;

public class Code10 {
    public static void main(String args[]) {
//        https://codeforces.com/problemset/problem/6/C
        int /* BigInteger */ n, a = 0, b = 0, j, k, t[] = new int[1000005];
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            t[i] = sc.nextInt();
        }
        j = 0;
        k = n - 1;
        while (j <= k) {
            if (a <= b) {
                a += t[j++];
            } else {
                b += t[k--];
            }
        }
        System.out.println(j + " " + (n - j));
        sc.close();
    }

}