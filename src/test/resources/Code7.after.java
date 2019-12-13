import java.util.*;

public class Code7 {

    // https://codeforces.com/problemset/problem/2/B
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        java.math.BigInteger countzero = java.math.BigInteger.ZERO;
        java.math.BigInteger pos = java.math.BigInteger.ZERO;
        java.math.BigInteger[][] ar = new java.math.BigInteger[n][n];
        for (int arFilling1 = 0; arFilling1 < n; arFilling1++) {
            for (int arFilling2 = 0; arFilling2 < n; arFilling2++) {
                ar[arFilling1][arFilling2] = java.math.BigInteger.ZERO;
            }
        }
        java.math.BigInteger[][] dp2 = new java.math.BigInteger[n][n];
        for (int dp2Filling1 = 0; dp2Filling1 < n; dp2Filling1++) {
            for (int dp2Filling2 = 0; dp2Filling2 < n; dp2Filling2++) {
                dp2[dp2Filling1][dp2Filling2] = java.math.BigInteger.ZERO;
            }
        }
        java.math.BigInteger[][] dp5 = new java.math.BigInteger[n][n];
        for (int dp5Filling1 = 0; dp5Filling1 < n; dp5Filling1++) {
            for (int dp5Filling2 = 0; dp5Filling2 < n; dp5Filling2++) {
                dp5[dp5Filling1][dp5Filling2] = java.math.BigInteger.ZERO;
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ar[i][j] = sc.nextBigInteger();
                if (ar[i][j].equals(java.math.BigInteger.ZERO)) {
                    countzero = countzero.add(java.math.BigInteger.ONE);
                    pos = java.math.BigInteger.valueOf(j);
                }
                if (i == 0 && j == 0) {
                    dp2[i][j] = fact(ar[i][j], java.math.BigInteger.valueOf(2));
                    dp5[i][j] = fact(ar[i][j], java.math.BigInteger.valueOf(5));
                } else if (i == 0) {
                    dp2[i][j] = fact(ar[i][j], java.math.BigInteger.valueOf(2)).add(dp2[i][j - 1]);
                    dp5[i][j] = fact(ar[i][j], java.math.BigInteger.valueOf(5)).add(dp5[i][j - 1]);
                } else if (j == 0) {
                    dp2[i][j] = fact(ar[i][j], java.math.BigInteger.valueOf(2)).add(dp2[i - 1][j]);
                    dp5[i][j] = fact(ar[i][j], java.math.BigInteger.valueOf(5)).add(dp5[i - 1][j]);
                } else {
                    dp2[i][j] = fact(ar[i][j], java.math.BigInteger.valueOf(2)).add(dp2[i - 1][j].min(dp2[i][j - 1]));
                    dp5[i][j] = fact(ar[i][j], java.math.BigInteger.valueOf(5)).add(dp5[i - 1][j].min(dp5[i][j - 1]));
                }
            }
        }
        java.math.BigInteger ans = dp2[n - 1][n - 1].min(dp5[n - 1][n - 1]);
        String s = "";
        String k = "";
        java.math.BigInteger techo = java.math.BigInteger.ZERO;
        java.math.BigInteger row = java.math.BigInteger.valueOf(n).subtract(java.math.BigInteger.ONE);
        java.math.BigInteger col = java.math.BigInteger.valueOf(n).subtract(java.math.BigInteger.ONE);
        if (countzero.compareTo(java.math.BigInteger.ZERO) > 0 && ans.compareTo(java.math.BigInteger.ONE) > 0) {
            techo = java.math.BigInteger.ONE;
            for (int i = 0; java.math.BigInteger.valueOf(i).compareTo(pos) < 0; i++) s = s + "R";
            for (int i = 0; i < n - 1; i++) s = s + "D";
            for (int i = pos.add(java.math.BigInteger.ONE).intValue(); i < n; i++) s = s + "R";
            ans = java.math.BigInteger.ONE;
        } else {
            while (row.compareTo(java.math.BigInteger.ZERO) > 0 || col.compareTo(java.math.BigInteger.ZERO) > 0) {
                if (row.equals(java.math.BigInteger.ZERO)) {
                    k = k + "R";
                    col = col.subtract(java.math.BigInteger.ONE);
                } else if (col.equals(java.math.BigInteger.ZERO)) {
                    k = k + "D";
                    row = row.subtract(java.math.BigInteger.ONE);
                } else if (dp2[n - 1][n - 1].compareTo(dp5[n - 1][n - 1]) < 0 && row.compareTo(java.math.BigInteger.ZERO) > 0 && dp2[row.subtract(java.math.BigInteger.ONE).intValue()][col.intValue()].compareTo(dp2[row.intValue()][col.subtract(java.math.BigInteger.ONE).intValue()]) < 0) {
                    k = k + "D";
                    row = row.subtract(java.math.BigInteger.ONE);
                } else if (dp2[n - 1][n - 1].compareTo(dp5[n - 1][n - 1]) >= 0 && row.compareTo(java.math.BigInteger.ZERO) > 0 && dp5[row.subtract(java.math.BigInteger.ONE).intValue()][col.intValue()].compareTo(dp5[row.intValue()][col.subtract(java.math.BigInteger.ONE).intValue()]) < 0) {
                    k = k + "D";
                    row = row.subtract(java.math.BigInteger.ONE);
                } else {
                    k = k + "R";
                    col = col.subtract(java.math.BigInteger.ONE);
                }
            }
        }
        System.out.println(ans);
        if (techo.equals(java.math.BigInteger.ZERO)) {
            StringBuilder scd = new StringBuilder(k);
            System.out.println(scd.reverse());
        } else
            System.out.println(s);
    }

    public static java.math.BigInteger fact(java.math.BigInteger n, java.math.BigInteger k) {
        java.math.BigInteger count = java.math.BigInteger.ZERO;
        if (n.equals(java.math.BigInteger.ZERO))
            return java.math.BigInteger.ONE;
        else {
            while (n.remainder(k).equals(java.math.BigInteger.ZERO)) {
                count = count.add(java.math.BigInteger.ONE);
                n = n.divide(k);
            }
            return count;
        }
    }
}
