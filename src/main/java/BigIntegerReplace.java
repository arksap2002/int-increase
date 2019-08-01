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
        Charset charset = Charset.defaultCharset();
        String resourceString = IOUtils.resourceToString(filename, charset);
        CompilationUnit cu = JavaParser.parse(resourceString);
        System.out.println(transform(cu));
    }

    public static String transform(final CompilationUnit compilationUnit) {
        return compilationUnit.toString();
    }
}
