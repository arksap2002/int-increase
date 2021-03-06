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
                IOUtils.resourceToString("/myTests/" +
                        filePrefix + ".before.java", Charset.defaultCharset()));
        CompilerUtils.CACHED_COMPILER.loadFromJava(filePrefix,
                IOUtils.resourceToString("/myTests/" +
                        filePrefix + ".after.java", Charset.defaultCharset()));
        String gotOutput = bigIntegerReplace.transform(
                IOUtils.resourceToString("/myTests/" +
                        filePrefix + ".before.java", Charset.defaultCharset()));
        assertEquals(IOUtils.resourceToString("/myTests/" +
                filePrefix + ".after.java", Charset.defaultCharset()), gotOutput);
    }

    private void runOnlyBeforeTestFromFile(String filePrefix) throws IOException, ClassNotFoundException {
        BigIntegerReplace bigIntegerReplace = new BigIntegerReplace();
        CompilerUtils.CACHED_COMPILER.loadFromJava(filePrefix,
                IOUtils.resourceToString("/myTests/" +
                        filePrefix + ".before.java", Charset.defaultCharset()));
        String gotOutput = bigIntegerReplace.transform(
                IOUtils.resourceToString("/myTests/" +
                        filePrefix + ".before.java", Charset.defaultCharset()));
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
    public void testPartialReplaceParseInt() throws IOException, ClassNotFoundException {
        runTestFromFile("PartialReplaceParseInt");
    }

    @Test
    public void testPartialReplaceIf() throws IOException, ClassNotFoundException {
        runTestFromFile("PartialReplaceIf");
    }

    @Test
    public void testPartialReplaceFor() throws IOException, ClassNotFoundException {
        runTestFromFile("PartialReplaceFor");
    }

    @Test
    public void testPartialReplaceArithmetic() throws IOException, ClassNotFoundException {
        runTestFromFile("PartialReplaceArithmetic");
    }

    @Test
    public void testPartialReplacePrint() throws IOException, ClassNotFoundException {
        runTestFromFile("PartialReplacePrint");
    }

    @Test
    public void testGeneralization() throws IOException, ClassNotFoundException {
        runTestFromFile("Generalization");
    }

    @Test
    public void testArray() throws IOException, ClassNotFoundException {
        runTestFromFile("Array");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMethodNoBigIntegerArrayParameters() throws IOException, ClassNotFoundException {
        runOnlyBeforeTestFromFile("MethodNoBigIntegerArrayParameters");
    }

    @Test
    public void testMethod() throws IOException, ClassNotFoundException {
        runTestFromFile("Method");
    }
}
