import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.Assert.*;

public class BigIntegerReplaceTest {

    @Test
    public void testTransform() throws IOException {
        BigIntegerReplace bigIntegerReplace = new BigIntegerReplace();
        String gotOutput = bigIntegerReplace.transform(IOUtils.resourceToString("/Foo.before.java", Charset.defaultCharset()));
        assertEquals(IOUtils.resourceToString("/Foo.after.java", Charset.defaultCharset()), gotOutput);
    }
}
