public class ParseInt {

    public static void main(String[] args) {
        String number = "10";
        String number1 = "11";
        java.math.BigInteger integer = java.math.BigInteger.valueOf(number);
        java.math.BigInteger integer1 = java.math.BigInteger.valueOf(number1);
        java.math.BigInteger a = java.math.BigInteger.valueOf(parseInt(10));
        integer = java.math.BigInteger.valueOf(number).multiply(java.math.BigInteger.valueOf(number));
    }

    public static int parseInt(int number) {
        return number;
    }
}
