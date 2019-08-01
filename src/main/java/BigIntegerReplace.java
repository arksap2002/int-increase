import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.Charset;

public class BigIntegerReplace {

    public static void main(String[] args) throws IOException {
        int vaSya_PetYa_DIma = 1;
        CompilationUnit cu = JavaParser.parse(IOUtils.resourceToString("/Foo.before.java", Charset.defaultCharset()));
        System.out.println(transform(cu));
    }

    public static String transform(CompilationUnit compilationUnit){
        return compilationUnit.toString();
    }
}
