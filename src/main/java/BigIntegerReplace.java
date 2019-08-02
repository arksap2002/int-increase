import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.Charset;

public final class BigIntegerReplace {

    private BigIntegerReplace() {
        //not called
    }

    public static void main(final String[] args) throws IOException {
        String filename = "/Foo.before.java";
        System.out.println(transform("/Foo.before.java"));
    }

    public static String transform(final String filename) throws IOException {
        Charset charset = Charset.defaultCharset();
        String resourceString = IOUtils.resourceToString(filename, charset);
        CompilationUnit cu = JavaParser.parse(resourceString);
        return cu.toString();
    }
}
