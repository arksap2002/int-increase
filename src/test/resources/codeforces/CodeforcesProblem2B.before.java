import java.util.*;

public class Code7 {
    //https://codeforces.com/problemset/problem/2/B
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int /* BigInteger */ countzero = 0;
        int /* BigInteger */ pos = 0;
        int /* BigInteger */ar[][] = new int[n][n];
        int /* BigInteger */dp2[][] = new int[n][n];
        int /* BigInteger */dp5[][] = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ar[i][j] = sc.nextInt();
                if (ar[i][j] == 0) {
                    countzero++;
                    pos = j;
                }
                if (i == 0 && j == 0) {
                    dp2[i][j] = fact(ar[i][j], 2);
                    dp5[i][j] = fact(ar[i][j], 5);
                } else if (i == 0) {
                    dp2[i][j] = fact(ar[i][j], 2) + dp2[i][j - 1];
                    dp5[i][j] = fact(ar[i][j], 5) + dp5[i][j - 1];
                } else if (j == 0) {
                    dp2[i][j] = fact(ar[i][j], 2) + dp2[i - 1][j];
                    dp5[i][j] = fact(ar[i][j], 5) + dp5[i - 1][j];
                } else {
                    dp2[i][j] = fact(ar[i][j], 2) + Math.min(dp2[i - 1][j], dp2[i][j - 1]);
                    dp5[i][j] = fact(ar[i][j], 5) + Math.min(dp5[i - 1][j], dp5[i][j - 1]);
                }
            }
        }
        int /* BigInteger */ ans = Math.min(dp2[n - 1][n - 1], dp5[n - 1][n - 1]);

        String s = "";
        String k = "";
        int /* BigInteger */ techo = 0;
        int /* BigInteger */ row = n - 1;
        int /* BigInteger */ col = n - 1;
        if (countzero > 0 && ans > 1) {
            techo = 1;
            for (int i = 0; i < pos; i++)
                s = s + "R";
            for (int i = 0; i < n - 1; i++)
                s = s + "D";
            for (int i = pos + 1; i < n; i++)
                s = s + "R";
            ans = 1;
        } else {
            while (row > 0 || col > 0) {
                if (row == 0) {
                    k = k + "R";
                    col--;
                } else if (col == 0) {
                    k = k + "D";
                    row--;
                } else if (dp2[n - 1][n - 1] < dp5[n - 1][n - 1] && row > 0 && dp2[row - 1][col] < dp2[row][col - 1]) {
                    k = k + "D";
                    row--;
                } else if (dp2[n - 1][n - 1] >= dp5[n - 1][n - 1] && row > 0 && dp5[row - 1][col] < dp5[row][col - 1]) {
                    k = k + "D";
                    row--;
                } else {
                    k = k + "R";
                    col--;
                }

            }
        }
        System.out.println(ans);
        if (techo == 0) {
            StringBuilder scd = new StringBuilder(k);

            System.out.println(scd.reverse());
        } else
            System.out.println(s);
    }


    public static int /* BigInteger */ fact(int /* BigInteger */ n, int /* BigInteger */ k) {
        int /* BigInteger */ count = 0;
        if (n == 0)
            return 1;
        else {
            while (n % k == 0) {
                count++;
                n = n / k;
            }
            return count;
        }
    }
}
