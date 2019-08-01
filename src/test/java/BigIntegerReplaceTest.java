import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.Assert.*;

public class BigIntegerReplaceTest {

    @Test
    public void transform() throws IOException {
        String filename = "/Foo.before.java";
        CompilationUnit cu = JavaParser.parse(IOUtils.resourceToString(filename, Charset.defaultCharset()));
        String string = BigIntegerReplace.transform(JavaParser.parse(String.valueOf(cu)));
        assertEquals(IOUtils.resourceToString("/Foo.after.java", Charset.defaultCharset()), string);
    }
}