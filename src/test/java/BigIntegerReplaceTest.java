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
    public void testScannerWithClass() throws IOException, ClassNotFoundException {
        runTestFromFile("ScannerWithClass");
    }

    @Test
    public void testScannerFromImport() throws IOException, ClassNotFoundException {
        runTestFromFile("ScannerFromImport");
    }

    @Test
    public void testMathWithClass() throws IOException, ClassNotFoundException {
        runTestFromFile("MathWithClass");
    }

    @Test
    public void testMathFromImport() throws IOException, ClassNotFoundException {
        runTestFromFile("MathFromImport");
    }

    @Test
    public void testParseInt() throws IOException, ClassNotFoundException {
        runTestFromFile("ParseInt");
    }

    @Test
    public void testArithmeticOperations() throws IOException, ClassNotFoundException {
        runTestFromFile("ArithmeticOperations");
    }

    @Test
    public void testUnary() throws IOException, ClassNotFoundException {
        runTestFromFile("Unary");
    }

    @Test
    public void testIf() throws IOException, ClassNotFoundException {
        runTestFromFile("If");
    }
  
    @Test
    public void testAssingExpr() throws IOException, ClassNotFoundException {
        runTestFromFile("AssingExpr");
    }

    @Test
    public void testBrief() throws IOException, ClassNotFoundException {
        runTestFromFile("Brief");
    }

    @Test
    public void testPrint() throws IOException, ClassNotFoundException {
        runTestFromFile("Print");
    }

    @Test
    public void testForWhile() throws IOException, ClassNotFoundException {
        runTestFromFile("ForWhile");
    }

    @Test
    public void testToString() throws IOException, ClassNotFoundException {
        runTestFromFile("ToString");
    }

    @Test
    public void testVarDecExpr() throws IOException, ClassNotFoundException {
        runTestFromFile("VarDecExprComment");
    }

    @Test
    public void testDifferentInts() throws IOException, ClassNotFoundException {
        runTestFromFile("DifferentInts");
    }

    @Test
    public void testFunctions() throws IOException, ClassNotFoundException {
        runTestFromFile("Functions");
    }
}
