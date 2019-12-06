import net.openhft.compiler.CompilerUtils;
import org.apache.commons.io.IOUtils;

import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.Assert.*;

public class BigIntegerReplaceTest {

    private void runTestFromFile(String filePrefix) throws IOException, ClassNotFoundException {
        BigIntegerReplace bigIntegerReplace = new BigIntegerReplace();
        CompilerUtils.CACHED_COMPILER.loadFromJava(filePrefix,
                IOUtils.resourceToString("/" +
                        filePrefix + ".before.java", Charset.defaultCharset()));
        CompilerUtils.CACHED_COMPILER.loadFromJava(filePrefix,
                IOUtils.resourceToString("/" +
                        filePrefix + ".after.java", Charset.defaultCharset()));
        String gotOutput = bigIntegerReplace.transform(
                IOUtils.resourceToString("/" +
                        filePrefix + ".before.java", Charset.defaultCharset()));
        assertEquals(IOUtils.resourceToString("/" +
                filePrefix + ".after.java", Charset.defaultCharset()), gotOutput);
    }

    private void runOnlyBeforeTestFromFile(String filePrefix) throws IOException, ClassNotFoundException {
        BigIntegerReplace bigIntegerReplace = new BigIntegerReplace();
        CompilerUtils.CACHED_COMPILER.loadFromJava(filePrefix,
                IOUtils.resourceToString("/" +
                        filePrefix + ".before.java", Charset.defaultCharset()));
    }

    @Test
    public void testCf1() throws IOException, ClassNotFoundException {
        runTestFromFile("Code1");
    }

    @Test
    public void testCf2() throws IOException, ClassNotFoundException {
        runTestFromFile("Code2");
    }

    @Test
    public void testCf3() throws IOException, ClassNotFoundException {
        runTestFromFile("Code3");
    }

    @Test
    public void testCf4() throws IOException, ClassNotFoundException {
        runTestFromFile("Code4");
    }

    @Test
    public void testCf5() throws IOException, ClassNotFoundException {
        runTestFromFile("Code5");
    }

    @Test
    public void testCf6() throws IOException, ClassNotFoundException {
        runTestFromFile("Code6");
    }

    @Test
    public void testCf7() throws IOException, ClassNotFoundException {
        runTestFromFile("Code7");
    }

    @Test
    public void testCf8() throws IOException, ClassNotFoundException {
        runTestFromFile("Code8");
    }

    @Test
    public void testCf9() throws IOException, ClassNotFoundException {
        runTestFromFile("Code9");
    }

    @Test
    public void testCf10() throws IOException, ClassNotFoundException {
        runTestFromFile("Code10");
    }
}
