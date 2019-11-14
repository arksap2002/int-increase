public class ParseInt {

    public static void main(String[] args) {
        String number = "10";
        String number1 = "11";
        java.math.BigInteger integer = new java.math.BigInteger(number);
        java.math.BigInteger integer1 = new java.math.BigInteger(number1);
        java.math.BigInteger a = new java.math.BigInteger(parseInt(10));
        integer = new java.math.BigInteger(number).multiply(new java.math.BigInteger(number));
    }

    public static int parseInt(int number) {
        return number;
    }
}
