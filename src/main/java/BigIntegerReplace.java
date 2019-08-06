import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.Charset;

public final class BigIntegerReplace {

    public String transform(final String string) throws IOException {
        CompilationUnit cu = JavaParser.parse(string);
        return cu.toString();
    }
}
