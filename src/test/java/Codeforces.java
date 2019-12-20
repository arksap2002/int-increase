import net.openhft.compiler.CompilerUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.Assert.*;

public class Codeforces {
    private void runTestFromFile(String filePrefix) throws IOException, ClassNotFoundException {
        BigIntegerReplace bigIntegerReplace = new BigIntegerReplace();
        CompilerUtils.CACHED_COMPILER.loadFromJava(filePrefix,
                IOUtils.resourceToString("/codeforces/" +
                        filePrefix + ".before.java", Charset.defaultCharset()));
        CompilerUtils.CACHED_COMPILER.loadFromJava(filePrefix,
                IOUtils.resourceToString("/codeforces/" +
                        filePrefix + ".after.java", Charset.defaultCharset()));
        String gotOutput = bigIntegerReplace.transform(
                IOUtils.resourceToString("/codeforces/" +
                        filePrefix + ".before.java", Charset.defaultCharset()));
        assertEquals(IOUtils.resourceToString("/codeforces/" +
                filePrefix + ".after.java", Charset.defaultCharset()), gotOutput);
    }

    @Test
    public void testCf1() throws IOException, ClassNotFoundException {
        runTestFromFile("CodeforcesProblem631B");
    }

    @Test
    public void testCf2() throws IOException, ClassNotFoundException {
        runTestFromFile("CodeforcesProblem857A");
    }

    @Test
    public void testCf3() throws IOException, ClassNotFoundException {
        runTestFromFile("CodeforcesProblem886B");
    }

    @Test
    public void testCf4() throws IOException, ClassNotFoundException {
        runTestFromFile("CodeforcesProblem929B");
    }

    @Test
    public void testCf5() throws IOException, ClassNotFoundException {
        runTestFromFile("CodeforcesProblem1A");
    }

    @Test
    public void testCf6() throws IOException, ClassNotFoundException {
        runTestFromFile("CodeforcesProblem1011A");
    }

    @Test
    public void testCf7() throws IOException, ClassNotFoundException {
        runTestFromFile("CodeforcesProblem2B");
    }

    @Test
    public void testCf8() throws IOException, ClassNotFoundException {
        runTestFromFile("CodeforcesProblem9D");
    }

    @Test
    public void testCf9() throws IOException, ClassNotFoundException {
        runTestFromFile("CodeforcesProblem6A");
    }
  
    @Test
    public void testCf10() throws IOException, ClassNotFoundException {
        runTestFromFile("CodeforcesProblem521A");
    }
}