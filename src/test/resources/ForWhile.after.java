public class ForWhile {

    public static void main(String[] args) {
        for (java.math.BigInteger i = java.math.BigInteger.ZERO; i.compareTo(java.math.BigInteger.valueOf(200)) < 0; i = java.math.BigInteger.ONE.add(i)) {
            System.out.println(java.math.BigInteger.ONE.negate());
        }
        java.math.BigInteger i = java.math.BigInteger.ZERO;
        while (i.compareTo(java.math.BigInteger.valueOf(100)) < 0) {
            System.out.println(java.math.BigInteger.ONE.negate());
            i = java.math.BigInteger.ONE.add(i);
        }
        while (true) {
            System.out.println(java.math.BigInteger.ONE);
        }
    }
}
