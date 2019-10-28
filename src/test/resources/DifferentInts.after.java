public class DifferentInts {

    public static void main(String[] args) {
        java.math.BigInteger a = java.math.BigInteger.TEN;
        int b = a.intValue();
        java.math.BigInteger d = (a.add(java.math.BigInteger.valueOf(b))).multiply(java.math.BigInteger.valueOf(b));
        int e = (a.add(java.math.BigInteger.valueOf(b))).multiply(java.math.BigInteger.valueOf(b)).intValue();
        if (a.compareTo(java.math.BigInteger.valueOf(b)) > 0) {
            System.out.println(a.add(java.math.BigInteger.valueOf(b)));
        }
        int c = 0;
        java.math.BigInteger f = java.math.BigInteger.valueOf(8);
        c = a.intValue();
        f = ((a.add(java.math.BigInteger.valueOf(b))).multiply(java.math.BigInteger.valueOf(c)).add((d.remainder(java.math.BigInteger.valueOf(e)))));
        f = java.math.BigInteger.valueOf(b).add(java.math.BigInteger.valueOf(b));
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
