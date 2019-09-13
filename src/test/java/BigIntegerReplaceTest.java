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
    public void testNothingChanges() throws IOException, ClassNotFoundException {
        runTestFromFile("NothingChanges");
    }

    @Test
    public void testReplaceVariableDeclarationType() throws IOException, ClassNotFoundException {
        runTestFromFile("ReplaceVariableDeclarationType");
    }

    @Test
    public void testScannerFromImport() throws IOException, ClassNotFoundException {
        runTestFromFile("ScannerFromImport");
    }

    @Test
    public void testScannerWithClass() throws IOException, ClassNotFoundException {
        runTestFromFile("ScannerWithClass");
    }
}
