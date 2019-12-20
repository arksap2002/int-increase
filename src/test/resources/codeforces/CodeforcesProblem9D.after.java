import java.util.*;

public class CodeforcesProblem9D {

    public static void main(String[] args) {
        // https://codeforces.com/contest/9/problem/D
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int k = sc.nextInt();
        java.math.BigInteger[][] aa = new java.math.BigInteger[n + 1][k + 1];
        for (int aaFilling1 = 0; aaFilling1 < n + 1; aaFilling1++) {
            for (int aaFilling2 = 0; aaFilling2 < k + 1; aaFilling2++) {
                aa[aaFilling1][aaFilling2] = java.math.BigInteger.ZERO;
            }
        }
        aa[0][0] = java.math.BigInteger.ONE;
        for (int i = 1; i <= n; i++) for (int j = 0; j <= k; j++) {
            for (int l = 1; l <= i; l++) if (j == 0) {
                aa[i][0] = aa[i][0].add(aa[l - 1][0].multiply(aa[i - l][0]));
            } else {
                aa[i][j] = aa[i][j].add(aa[l - 1][0].multiply(aa[i - l][j - 1]));
                aa[i][j] = aa[i][j].add(aa[l - 1][j - 1].multiply(aa[i - l][0]));
                aa[i][j] = aa[i][j].subtract(aa[l - 1][j - 1].multiply(aa[i - l][j - 1]));
            }
        }
        System.out.println(aa[n][k]);
    }
}
