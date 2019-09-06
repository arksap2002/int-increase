import net.openhft.compiler.CompilerUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.Assert.*;

public class BigIntegerReplaceTest {

    private void runTestFromFile(String filePrefix) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        BigIntegerReplace bigIntegerReplace = new BigIntegerReplace();
        ((Runnable) (CompilerUtils.CACHED_COMPILER.loadFromJava("src.test.resources." + filePrefix + ".before.java",
                IOUtils.resourceToString("/" + filePrefix + ".before.java", Charset.defaultCharset()))).newInstance()).run();
        ((Runnable) (CompilerUtils.CACHED_COMPILER.loadFromJava("src.test.resources." + filePrefix + ".after.java",
                IOUtils.resourceToString("/" + filePrefix + ".after.java", Charset.defaultCharset()))).newInstance()).run();
        String gotOutput = bigIntegerReplace.transform(IOUtils.resourceToString("/" + filePrefix + ".before.java", Charset.defaultCharset()));
        assertEquals(IOUtils.resourceToString("/" + filePrefix + ".after.java", Charset.defaultCharset()), gotOutput);
    }

    @Test
    public void testNothingChanges() throws IOException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        runTestFromFile("NothingChanges");
    }

    @Test
    public void testReplaceVariableDeclarationType() throws IOException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        runTestFromFile("ReplaceVariableDeclarationType");
    }

    @Test
    public void testArithmeticOperations() throws IOException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        runTestFromFile("ArithmeticOperations");
    }

    @Test
    public void testUnary() throws IOException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        runTestFromFile("Unary");
    }
  
    @Test
    public void testScannerFromImport() throws IOException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        runTestFromFile("ScannerFromImport");
    }

    @Test
    public void testScannerWithClass() throws IOException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        runTestFromFile("ScannerWithClass");
    }
}
