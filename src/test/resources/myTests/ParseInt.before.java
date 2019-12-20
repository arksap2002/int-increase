public class ParseInt {
    public static void main(String[] args) {
        String number = "10";
        String number1 = "11";
        int /* BigInteger */ integer = Integer.parseInt(number);
        int /* BigInteger */ integer1 = Integer.parseInt(number1);
        int /* BigInteger */ a = parseInt(10);
        integer = Integer.parseInt(number) * Integer.parseInt(number);
    }

    public static int parseInt(int number) {
        return number;
    }
}
