import java.util.*;

public class Code8 {
    public static void main(String[] args) {
//        https://codeforces.com/contest/9/problem/D
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        int[][] /* BigInteger */ aa = new int[n + 1][k + 1];
        aa[0][0] = 1;
        for (int i = 1; i <= n; i++)
            for (int j = 0; j <= k; j++) {
                for (int l = 1; l <= i; l++)
                    if (j == 0) {
                        aa[i][0] += aa[l - 1][0] * aa[i - l][0];
                    } else {
                        aa[i][j] += aa[l - 1][0] * aa[i - l][j - 1];
                        aa[i][j] += aa[l - 1][j - 1] * aa[i - l][0];
                        aa[i][j] -= aa[l - 1][j - 1] * aa[i - l][j - 1];
                    }
            }
        System.out.println(aa[n][k]);
    }
}