import java.util.*;

public class CodeforcesProblem1A {
    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);
        int /* BigInteger */ n = sc.nextInt();
        int /* BigInteger */ m = sc.nextInt();
        int /* BigInteger */ a = sc.nextInt();
        System.out.println(
                (((n-1)/a)+1) * (((m-1)/a)+1)
        );
    }
}
