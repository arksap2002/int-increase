import java.lang.Math;

public class MathFromImport {

    int a = Math.abs(-1);

    int e = Math.abs(a);

    int b = Math.max(a, 100);

    int c = Math.min(a, b) * -1 + Math.max(b, -10);

    public static void main(String[] args) {
        int d = Math.abs(-10);
        int e = Math.max(d, a);
        int f = Math.min(Math.max(d, a), Math.max(d, a));
    }
}
