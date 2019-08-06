import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.Assert.*;

public class BigIntegerReplaceTest {


    @Test
    public void testTransform() throws IOException {
        Charset charset = Charset.defaultCharset();
        String stringOutput = IOUtils.resourceToString("/Foo.before.java", charset);
        assertEquals(IOUtils.resourceToString("/Foo.after.java", charset), stringOutput);
    }
}