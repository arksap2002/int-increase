import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.Assert.*;

public class BigIntegerReplaceTest {


    public void runTestOnFile(final String filename, final Object object) throws IOException {
        assertEquals(IOUtils.resourceToString(filename, Charset.defaultCharset()), object);
    }

    @Test
    public void transform() throws IOException {
        String filename = "/Foo.before.java";
        String string = BigIntegerReplace.transform(filename);
        runTestOnFile("/Foo.after.java", string);
    }
}