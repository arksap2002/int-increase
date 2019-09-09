import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.Assert.*;

public class BigIntegerReplaceTest {

    private void runTestFromFile(String filePrefix) throws IOException {
        BigIntegerReplace bigIntegerReplace = new BigIntegerReplace();
        String gotOutput = bigIntegerReplace.transform(IOUtils.resourceToString(filePrefix + ".before.java", Charset.defaultCharset()));
        assertEquals(IOUtils.resourceToString(filePrefix + ".after.java", Charset.defaultCharset()), gotOutput);
    }

    @Test
    public void testNothingChanges() throws IOException {
        runTestFromFile("/NothingChanges");
    }
  
    @Test
    public void testReplaceVariableDeclarationType() throws IOException {
        runTestFromFile("/ReplaceVariableDeclarationType");
    }

    @Test
    public void testScannerFromImport() throws IOException {
        runTestFromFile("/ScannerFromImport");
    }

    @Test
    public void testScannerWithClass() throws IOException {
        runTestFromFile("/ScannerWithClass");
    }

    @Test
    public void testMathFromImport() throws IOException {
        runTestFromFile("/MathFromImport");
    }

    @Test
    public void testMathWithClass() throws IOException {
        runTestFromFile("/MathWithClass");
    }
}
