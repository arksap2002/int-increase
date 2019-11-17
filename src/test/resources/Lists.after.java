import java.util.ArrayList;

public class Lists {

    public static void main(String[] args) {
        ArrayList<java.math.BigInteger> arrayList = new ArrayList<>();
        java.math.BigInteger[] a = new java.math.BigInteger[10];
        int intNumber = 8;
        java.math.BigInteger bigIntNumber = new java.math.BigInteger(8);
        java.math.BigInteger[] b = new java.math.BigInteger[bigIntNumber.add(java.math.BigInteger.ONE).intValue()];
        for (java.math.BigInteger i = java.math.BigInteger.ZERO; i.compareTo(a.length) < 0; i = java.math.BigInteger.ONE.add(i)) {
            a[i.intValue()] = i;
        }
        for (int i = 0; i < a.length; i++) {
            a[i] = new java.math.BigInteger(i);
        }
        for (java.math.BigInteger i = java.math.BigInteger.ZERO; i.compareTo(new java.math.BigInteger(100)) < 0; i = java.math.BigInteger.ONE.add(i)) {
            arrayList.add(i);
        }
        for (int i = 0; i < 100; i++) {
            arrayList.add(new java.math.BigInteger(i));
        }
        arrayList.remove(intNumber);
        arrayList.remove(bigIntNumber.intValue());
        for (java.math.BigInteger i = java.math.BigInteger.ZERO; i.compareTo(new java.math.BigInteger(arrayList.size())) < 0; i = java.math.BigInteger.ONE.add(i)) {
            System.out.println(arrayList.get(i.intValue()));
        }
    }
}
