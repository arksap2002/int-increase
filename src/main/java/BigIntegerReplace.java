import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import static com.github.javaparser.ast.Node.SYMBOL_RESOLVER_KEY;

public final class BigIntegerReplace {

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
                final VariableDeclarator n,
                final JavaParserFacade javaParserFacade) {
            super.visit(n, javaParserFacade);
            if (n.getType().isPrimitiveType()) {
                if (n.getType().asPrimitiveType().equals(
                        PrimitiveType.intType())) {
                    ClassOrInterfaceType classOrInterfaceType1 =
                            new ClassOrInterfaceType();
                    ClassOrInterfaceType classOrInterfaceType2 =
                            new ClassOrInterfaceType();
                    ClassOrInterfaceType classOrInterfaceType3 =
                            new ClassOrInterfaceType();
                    SimpleName simpleName = new SimpleName();
                    simpleName.setIdentifier("java");
                    classOrInterfaceType3.setName(simpleName);
                    simpleName = new SimpleName();
                    simpleName.setIdentifier("math");
                    classOrInterfaceType2.setName(simpleName);
                    classOrInterfaceType2.setScope(classOrInterfaceType3);
                    simpleName = new SimpleName();
                    simpleName.setIdentifier("BigInteger");
                    classOrInterfaceType1.setName(simpleName);
                    classOrInterfaceType1.setScope(classOrInterfaceType2);
                    classOrInterfaceType1.setName(simpleName);
                    n.setType(classOrInterfaceType1);
                }
            }
        }
    }
}
