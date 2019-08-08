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
        CompilationUnit cu = JavaParser.parse(string);
        TypeSolver tS = new CombinedTypeSolver(new ReflectionTypeSolver());
        cu.setData(SYMBOL_RESOLVER_KEY, new JavaSymbolSolver(tS));
        cu.accept(new Finding(), JavaParserFacade.get(tS));
        return cu.toString();
    }

    static class Finding extends VoidVisitorAdapter<JavaParserFacade> {
        @Override
        public void visit(final VariableDeclarator n, final JavaParserFacade jpf) {
            super.visit(n, jpf);
            if (n.getType().isPrimitiveType()) {
                if (n.getType().asPrimitiveType().equals(PrimitiveType.intType())) {
                    ClassOrInterfaceType coit = new ClassOrInterfaceType();
                    SimpleName simpleName = new SimpleName();
                    simpleName.setIdentifier("BigInteger");
                    coit.setName(simpleName);
                    n.setType(coit);
                }
            }
        }

        @Override
        public void visit(final MethodCallExpr n, final JavaParserFacade jpf) {
            super.visit(n, jpf);
            if (n.getName().getIdentifier().equals("nextInt")) {
                SimpleName simpleName = new SimpleName();
                simpleName.setIdentifier("nextBigInteger");
                n.setName(simpleName);
            }
        }
    }
}
