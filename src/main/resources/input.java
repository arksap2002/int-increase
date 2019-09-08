public class ToString {

    public static void main(String[] args) {

        int a = 10;
        String string = Integer.toString(a);
        System.out.println(Integer.toString(2));
        System.out.println(Integer.toString(8));
        java.math.BigInteger bigInteger = java.math.BigInteger.valueOf(11);
        String string1 = bigInteger.toString();
        String string2 = java.math.BigInteger.valueOf(11).toString();
    }
}
