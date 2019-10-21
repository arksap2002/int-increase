public class ForWhile {

    public static void main(String[] args) {
        for (java.math.BigInteger i = java.math.BigInteger.ZERO; i.compareTo(java.math.BigInteger.valueOf(200)) < 0; i = java.math.BigInteger.ONE.add(i)) {
            System.out.println(-1);
        }
        for (int i = 0; i < 200; i++) {
            System.out.println(-1);
        }
        int i = 0;
        while (i < 100) {
            System.out.println(-1);
            i++;
        }
        while (i < 100) {
            System.out.println(-1);
            i++;
        }
        while (true) {
            System.out.println(1);
        }
    }
}
