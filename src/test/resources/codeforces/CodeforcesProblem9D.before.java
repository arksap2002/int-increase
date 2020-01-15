import java.util.Scanner;

public class CodeforcesProblem9D {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int h = sc.nextInt();
        int[][] /* BigInteger */ dp = new int[n + 1][n + 1];
        int[][] /* BigInteger */ sum = new int[n + 1][n + 1];
        dp[0][0] = 1;
        for (int j = 0; j <= n; j++) {
            sum[0][j] = 1;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                for (int k = 0; k < i; k++) {
                    dp[i][j] +=
                            dp[k][j - 1] * sum[i - 1 - k][j - 1] + sum[k][j - 1] * dp[i - 1 - k][j - 1]
                                    - dp[k][j - 1] * dp[i - 1 - k][j - 1];
                }
                sum[i][j] = sum[i][j - 1] + dp[i][j];
            }
        }
        System.out.println(sum[n][n] - sum[n][h - 1]);
        sc.close();
    }
}
