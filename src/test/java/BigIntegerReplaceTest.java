import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.Assert.*;

public class BigIntegerReplaceTest {


    @Test
    public void transform() throws IOException {
        String filename = "/Foo.before.java";
        Charset charset = Charset.defaultCharset();
        String string = IOUtils.resourceToString(filename, charset);
        assertEquals(IOUtils.resourceToString("/Foo.after.java", Charset.defaultCharset()), string);
    }
}