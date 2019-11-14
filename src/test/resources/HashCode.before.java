public class HashCode {
    public static void main(String[] args) {
        int a /* BigInteger */ = 10;
        int b = Integer.hashCode(a);
        int c = 10;
        int d = Integer.hashCode(c);
        int e /* BigInteger */ = Integer.hashCode(a);
        int f /* BigInteger */ = Integer.hashCode(c);
    }
}
