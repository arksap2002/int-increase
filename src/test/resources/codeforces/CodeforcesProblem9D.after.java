import java.util.Scanner;

public class CodeforcesProblem9D {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int h = sc.nextInt();
        java.math.BigInteger[][] dp = new java.math.BigInteger[n + 1][n + 1];
        for (int dpFilling1 = 0; dpFilling1 < n + 1; dpFilling1++) {
            for (int dpFilling2 = 0; dpFilling2 < n + 1; dpFilling2++) {
                dp[dpFilling1][dpFilling2] = java.math.BigInteger.ZERO;
            }
        }
        java.math.BigInteger[][] sum = new java.math.BigInteger[n + 1][n + 1];
        for (int sumFilling1 = 0; sumFilling1 < n + 1; sumFilling1++) {
            for (int sumFilling2 = 0; sumFilling2 < n + 1; sumFilling2++) {
                sum[sumFilling1][sumFilling2] = java.math.BigInteger.ZERO;
            }
        }
        dp[0][0] = java.math.BigInteger.ONE;
        for (int j = 0; j <= n; j++) {
            sum[0][j] = java.math.BigInteger.ONE;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                for (int k = 0; k < i; k++) {
                    dp[i][j] = dp[i][j].add(dp[k][j - 1].multiply(sum[i - 1 - k][j - 1]).add(sum[k][j - 1].multiply(dp[i - 1 - k][j - 1])).subtract(dp[k][j - 1].multiply(dp[i - 1 - k][j - 1])));
                }
                sum[i][j] = sum[i][j - 1].add(dp[i][j]);
            }
        }
        System.out.println(sum[n][n].subtract(sum[n][h - 1]));
        sc.close();
    }
}
