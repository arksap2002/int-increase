import java.util.Scanner;

public class Cf3 {

    public static void main(String[] args) {
        // https://codeforces.com/contest/908/problem/C
        Scanner scanner = new Scanner(System.in);
        java.math.BigInteger n = scanner.nextBigInteger();
        java.math.BigInteger r = scanner.nextBigInteger();
        java.math.BigInteger[] x = new java.math.BigInteger[n.intValue()];
        for (int i = 0; new java.math.BigInteger(i).compareTo(n) < 0; i++) {
            x[i] = scanner.nextBigInteger();
        }
        java.math.BigInteger length = java.math.BigInteger.ZERO;
        java.math.BigInteger[] radx = new java.math.BigInteger[n.intValue()];
        double[] /* BigInteger */
                rady = new double[n];
        radx[0] = new java.math.BigInteger(x[0]);
        rady[0] = r.intValue();
        double maxy;
        double anser;
        for (int i = 1; new java.math.BigInteger(i).compareTo(n) < 0; i++) {
            maxy = r.intValue();
            anser = 0;
            for (int j = 0; new java.math.BigInteger(j).compareTo(length.add(new java.math.BigInteger(i))) < 0; j++) {
                if (new java.math.BigInteger(x[i]).subtract(new java.math.BigInteger(radx[j])).abs().compareTo(java.math.BigInteger.TWO.multiply(r)) <= 0) {
                    anser = (2 * rady[j] + Math.sqrt(4 * rady[j] * rady[j] - 4 * (rady[j] * rady[j] + (radx[j] - x[i]) * (radx[j] - x[i]) - 4 * r * r))) / 2;
                    if (maxy < anser) {
                        maxy = anser;
                    }
                }
            }
            radx[i] = new java.math.BigInteger(x[i]);
            rady[i] = maxy;
        }
        for (int i = 0; new java.math.BigInteger(i).compareTo(n) < 0; i++) {
            System.out.print(rady[i] + " ");
        }
    }
}
