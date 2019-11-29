import java.util.ArrayList;

public class Array {

    public static void main(String[] args) {
        int intNumber = 8;
        java.math.BigInteger bigIntNumber = new java.math.BigInteger(8);
        java.math.BigInteger[] a = new java.math.BigInteger[intNumber + 1];
        int[] b = new int[bigIntNumber.add(java.math.BigInteger.ONE).intValue()];
        int[][] c = new int[intNumber + 1][bigIntNumber.add(java.math.BigInteger.ONE).intValue()];
        java.math.BigInteger[][] d = new java.math.BigInteger[intNumber + 1][bigIntNumber.add(java.math.BigInteger.ONE).intValue()];
        java.math.BigInteger[][][][][][][] e = new java.math.BigInteger[10][10][10][10][10][10][10];
        int[][] arr = { { 1, 2, bigIntNumber.add(java.math.BigInteger.ONE) }, { 2, 2, intNumber + 1 }, { 3, 2, 1 } };
        java.math.BigInteger[][][] arr2 = { { { java.math.BigInteger.ONE }, { java.math.BigInteger.TWO, bigIntNumber.add(java.math.BigInteger.ONE) } }, { { java.math.BigInteger.TWO }, { java.math.BigInteger.TWO, java.math.BigInteger.TWO } }, { { new java.math.BigInteger(intNumber).add(java.math.BigInteger.ONE) }, { java.math.BigInteger.TWO }, { java.math.BigInteger.ONE } } };
        for (java.math.BigInteger i = java.math.BigInteger.ZERO; i.compareTo(new java.math.BigInteger(a.length).min(new java.math.BigInteger(b.length))) < 0; i = java.math.BigInteger.ONE.add(i)) {
            a[i.intValue()] = i.add(java.math.BigInteger.ONE);
            b[i.intValue()] = i.add(java.math.BigInteger.ONE).intValue();
        }
        for (int i = 0; i < Math.min(a.length, b.length); i++) {
            a[i] = new java.math.BigInteger(i).add(java.math.BigInteger.ONE);
            b[i] = i + 1;
        }
        for (int i = 0; i < intNumber + 1; i++) {
            for (java.math.BigInteger j = java.math.BigInteger.ZERO; j.compareTo(bigIntNumber.add(java.math.BigInteger.ONE)) < 0; j = java.math.BigInteger.ONE.add(j)) {
                c[i][j.intValue()] = new java.math.BigInteger(i).multiply(j).intValue();
                d[i][j.intValue()] = new java.math.BigInteger(i).multiply(j);
            }
        }
        for (java.math.BigInteger i = java.math.BigInteger.ZERO; i.compareTo(new java.math.BigInteger(intNumber).add(java.math.BigInteger.ONE)) < 0; i = java.math.BigInteger.ONE.add(i)) {
            for (int j = 0; new java.math.BigInteger(j).compareTo(bigIntNumber.add(java.math.BigInteger.ONE)) < 0; j++) {
                c[i.intValue()][j] = i.multiply(new java.math.BigInteger(j)).intValue();
                d[i.intValue()][j] = i.multiply(new java.math.BigInteger(j));
            }
        }
        for (java.math.BigInteger i = java.math.BigInteger.ZERO; i.compareTo(new java.math.BigInteger(intNumber).add(java.math.BigInteger.ONE)) < 0; i = java.math.BigInteger.ONE.add(i)) {
            for (java.math.BigInteger j = java.math.BigInteger.ZERO; j.compareTo(bigIntNumber.add(java.math.BigInteger.ONE)) < 0; j = java.math.BigInteger.ONE.add(j)) {
                c[i.intValue()][j.intValue()] = i.multiply(j).intValue();
                d[i.intValue()][j.intValue()] = i.multiply(j);
                e[i.intValue()][j.intValue()][i.intValue()][j.intValue()][i.intValue()][j.intValue()][i.intValue()] = i.multiply(j);
            }
        }
        for (int i = 0; i < intNumber + 1; i++) {
            for (int j = 0; new java.math.BigInteger(j).compareTo(bigIntNumber.add(java.math.BigInteger.ONE)) < 0; j++) {
                c[i][j] = i * j;
                d[i][j] = new java.math.BigInteger(i).multiply(new java.math.BigInteger(j));
                e[i][j][i][j][i][j][i] = new java.math.BigInteger(i).multiply(new java.math.BigInteger(j));
            }
        }
    }
}
