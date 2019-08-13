import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import java.util.ArrayList;


import static com.github.javaparser.ast.Node.SYMBOL_RESOLVER_KEY;

public final class BigIntegerReplace {

    public static ArrayList<String> scannerList = new ArrayList<>();

    public String transform(final String string) {
        CompilationUnit compilationUnit = JavaParser.parse(string);
        ReflectionTypeSolver reflectionTypeSolver =
                new ReflectionTypeSolver();
        compilationUnit.setData(
                SYMBOL_RESOLVER_KEY,
                new JavaSymbolSolver(reflectionTypeSolver));
        compilationUnit.accept(
                new TransformVisitor(),
                JavaParserFacade.get(reflectionTypeSolver));
        return compilationUnit.toString();
    }

    static class TransformVisitor
            extends VoidVisitorAdapter<JavaParserFacade> {
        @Override
        public void visit(
                final MethodCallExpr n,
                final JavaParserFacade javaParserFacade) {
            super.visit(n, javaParserFacade);
            for (String scannerName : scannerList) {
                if ((n.getName().getIdentifier().equals("nextInt")) &&
                        (n.getScope().get().asNameExpr().getName().toString().
                                equals(scannerName))) {
                    SimpleName simpleName = new SimpleName();
                    simpleName.setIdentifier("nextBigInteger");
                    n.setName(simpleName);
                }
            }
        }

        @Override
        public void visit(VariableDeclarator n,
                          JavaParserFacade javaParserFacade) {
            super.visit(n, javaParserFacade);
            if (n.getType().isClassOrInterfaceType() &&
                    n.getType().asClassOrInterfaceType().getName().toString().
                            equals("Scanner")) {
                scannerList.add(n.getName().toString());
            }
        }
    }
}
