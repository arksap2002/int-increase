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
    public void test631B() throws IOException, ClassNotFoundException {
        runTestFromFile("CodeforcesProblem631B");
    }

    @Test
    public void test857A() throws IOException, ClassNotFoundException {
        runTestFromFile("CodeforcesProblem857A");
    }

    @Test
    public void test886B() throws IOException, ClassNotFoundException {
        runTestFromFile("CodeforcesProblem886B");
    }

    @Test
    public void test929B() throws IOException, ClassNotFoundException {
        runTestFromFile("CodeforcesProblem929B");
    }

    @Test
    public void test1A() throws IOException, ClassNotFoundException {
        runTestFromFile("CodeforcesProblem1A");
    }

    @Test
    public void test1011A() throws IOException, ClassNotFoundException {
        runTestFromFile("CodeforcesProblem1011A");
    }

    @Test
    public void test2B() throws IOException, ClassNotFoundException {
        runTestFromFile("CodeforcesProblem2B");
    }

    @Test
    public void test9D() throws IOException, ClassNotFoundException {
        runTestFromFile("CodeforcesProblem9D");
    }

    @Test
    public void test6A() throws IOException, ClassNotFoundException {
        runTestFromFile("CodeforcesProblem6A");
    }
  
    @Test
    public void test521A() throws IOException, ClassNotFoundException {
        runTestFromFile("CodeforcesProblem521A");
    }
}
