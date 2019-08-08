import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import java.io.IOException;

import static com.github.javaparser.ast.Node.SYMBOL_RESOLVER_KEY;

public final class BigIntegerReplace {

    public String transform(final String string) throws IOException {
        CompilationUnit cu = JavaParser.parse(string);
        return cu.toString();
    }

    public String firstTransform(final String string) throws IOException {
        CompilationUnit compilationUnit = JavaParser.parse(string);
        TypeSolver typeSolver = new CombinedTypeSolver(new ReflectionTypeSolver());
        compilationUnit.setData(SYMBOL_RESOLVER_KEY, new JavaSymbolSolver(typeSolver));
        compilationUnit.accept(new Finding(), JavaParserFacade.get(typeSolver));
        return compilationUnit.toString();
    }

    static class Finding extends VoidVisitorAdapter<JavaParserFacade> {
        @Override
        public void visit(VariableDeclarator n, JavaParserFacade javaParserFacade) {
            super.visit(n, javaParserFacade);
            if (n.getType().isPrimitiveType()){
                if (n.getType().asPrimitiveType().equals(PrimitiveType.intType())){
                    ClassOrInterfaceType classOrInterfaceType = new ClassOrInterfaceType();
                    SimpleName simpleName = new SimpleName();
                    simpleName.setIdentifier("BigInteger");
                    classOrInterfaceType.setName(simpleName);
                    n.setType(classOrInterfaceType);
                }
            }
        }
        @Override
        public void visit(MethodCallExpr n, JavaParserFacade javaParserFacade) {
            super.visit(n, javaParserFacade);
            if (n.getName().getIdentifier().equals("nextInt")){
                SimpleName simpleName = new SimpleName();
                simpleName.setIdentifier("nextBigInteger");
                n.setName(simpleName);
            }
        }
    }
}
