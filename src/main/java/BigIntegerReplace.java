import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.io.File;
import java.io.IOException;

public final class BigIntegerReplace {

    public String transform(final String string) throws IOException {
        CompilationUnit cu = JavaParser.parse(string);
        return cu.toString();
    }
}
