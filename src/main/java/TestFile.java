import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestFile {
    public static void main(String[] args) throws IOException {
        InputStreamReader r = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(r);
        int a = br.read();
        int b = br.read();
        System.out.println(a);
        System.out.println(b);
        System.out.println(a + b);
        br.close();
    }
}
