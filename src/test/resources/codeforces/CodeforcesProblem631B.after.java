import java.util.*;

public class CodeforcesProblem631B {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        int k = in.nextInt();
        java.math.BigInteger[] rows = new java.math.BigInteger[n];
        for (int rowsFilling1 = 0; rowsFilling1 < n; rowsFilling1++) {
            rows[rowsFilling1] = java.math.BigInteger.ZERO;
        }
        java.math.BigInteger[] timestamp_rows = new java.math.BigInteger[n];
        for (int timestamp_rowsFilling1 = 0; timestamp_rowsFilling1 < n; timestamp_rowsFilling1++) {
            timestamp_rows[timestamp_rowsFilling1] = java.math.BigInteger.ZERO;
        }
        java.math.BigInteger[] timestamp_cols = new java.math.BigInteger[m];
        for (int timestamp_colsFilling1 = 0; timestamp_colsFilling1 < m; timestamp_colsFilling1++) {
            timestamp_cols[timestamp_colsFilling1] = java.math.BigInteger.ZERO;
        }
        java.math.BigInteger[] cols = new java.math.BigInteger[m];
        for (int colsFilling1 = 0; colsFilling1 < m; colsFilling1++) {
            cols[colsFilling1] = java.math.BigInteger.ZERO;
        }
        for (int i = 0; i < k; i++) {
            java.math.BigInteger type = in.nextBigInteger();
            java.math.BigInteger rc = in.nextBigInteger();
            java.math.BigInteger color = in.nextBigInteger();
            if (type.equals(java.math.BigInteger.ONE)) {
                rows[rc.subtract(java.math.BigInteger.ONE).intValue()] = color;
                timestamp_rows[rc.subtract(java.math.BigInteger.ONE).intValue()] = java.math.BigInteger.valueOf(i).add(java.math.BigInteger.ONE);
            } else {
                cols[rc.subtract(java.math.BigInteger.ONE).intValue()] = color;
                timestamp_cols[rc.subtract(java.math.BigInteger.ONE).intValue()] = java.math.BigInteger.valueOf(i).add(java.math.BigInteger.ONE);
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (timestamp_rows[i].compareTo(timestamp_cols[j]) > 0) {
                    System.out.print(rows[i] + " ");
                } else {
                    System.out.print(cols[j] + " ");
                }
            }
            System.out.println();
        }
    }
}
