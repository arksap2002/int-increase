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

    @Test
    public void test1() throws IOException, ClassNotFoundException {
        runTestFromFile("Cf1");
    }

    @Test
    public void test2() throws IOException, ClassNotFoundException {
        runTestFromFile("Cf2");
    }

    @Test
    public void test3() throws IOException, ClassNotFoundException {
        runTestFromFile("Cf3");
    }

    @Test
    public void test4() throws IOException, ClassNotFoundException {
        runTestFromFile("Cf4");
    }

    @Test
    public void test5() throws IOException, ClassNotFoundException {
        runTestFromFile("Cf5");
    }

    @Test
    public void test6() throws IOException, ClassNotFoundException {
        runTestFromFile("Cf6");
    }

    @Test
    public void test7() throws IOException, ClassNotFoundException {
        runTestFromFile("Cf7");
    }
}
