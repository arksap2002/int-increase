import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.Assert.*;

public class BigIntegerReplaceTest {

    private void runTestFromFile(String string) throws IOException {
        BigIntegerReplace bigIntegerReplace = new BigIntegerReplace();
        String gotOutput = bigIntegerReplace.transform(IOUtils.resourceToString("/" + string + ".before.java", Charset.defaultCharset()));
        assertEquals(IOUtils.resourceToString("/" + string + ".after.java", Charset.defaultCharset()), gotOutput);
    }

    @Test
    public void testTransform() throws IOException {
        runTestFromFile("Foo");
    }

    @Test
    public void testReplaceVariableDeclarationType() throws IOException {
        runTestFromFile("OnlyTypeChanging");
    }
}
