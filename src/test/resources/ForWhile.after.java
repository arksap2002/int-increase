public class ForWhile {

    public static void main(String[] args) {
        java.math.BigInteger a = java.math.BigInteger.TEN;
        int b = 9;
        for (java.math.BigInteger j = java.math.BigInteger.ZERO; j.compareTo(java.math.BigInteger.valueOf(200)) < 0; j = java.math.BigInteger.ONE.add(j)) {
            System.out.println(-1);
        }
        java.math.BigInteger j = java.math.BigInteger.ZERO;
        while (j.compareTo(java.math.BigInteger.valueOf(100)) < 0) {
            System.out.println(-1);
            j = java.math.BigInteger.ONE.add(j);
        }
        for (int i = 0; java.math.BigInteger.valueOf(i).compareTo(a) < 0; i++) {
            System.out.println(1);
        }
        for (java.math.BigInteger i = java.math.BigInteger.ZERO; i.compareTo(a) < 0; i = java.math.BigInteger.ONE.add(i)) {
            System.out.println(1);
        }
        for (int i = 0; i < b; i++) {
            System.out.println(1);
        }
        for (java.math.BigInteger i = java.math.BigInteger.ZERO; i.compareTo(java.math.BigInteger.valueOf(b)) < 0; i = java.math.BigInteger.ONE.add(i)) {
            System.out.println(1);
        }
    }
}
