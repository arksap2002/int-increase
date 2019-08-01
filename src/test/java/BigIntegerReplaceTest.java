import com.github.javaparser.JavaParser;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.Assert.*;

public class BigIntegerReplaceTest {

    @Test
    public void transform() throws IOException {
        assertEquals(IOUtils.resourceToString("/Foo.after.java", Charset.defaultCharset()), BigIntegerReplace.transform(JavaParser.parse(IOUtils.resourceToString("/Foo.before.java", Charset.defaultCharset()))));
    }
}