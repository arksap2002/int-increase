import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.declarations.ResolvedValueDeclaration;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;
import com.github.javaparser.symbolsolver.javaparsermodel.declarations.JavaParserFieldDeclaration;
import com.github.javaparser.symbolsolver.javaparsermodel.declarations.JavaParserSymbolDeclaration;
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
                final MethodCallExpr n,
                final JavaParserFacade javaParserFacade) {
            super.visit(n, javaParserFacade);
            changingNextIntToNextBigInteger(n);
        }

        private void changingNextIntToNextBigInteger(final MethodCallExpr n) {
            if ((n.getName().getIdentifier().equals("nextInt"))) {
                if (n.getScope().isPresent()) {
                    NameExpr nameExpr = n.getScope().get().asNameExpr();
                    ResolvedValueDeclaration resolvedValueDeclaration =
                            nameExpr.resolve();
                    if (resolvedValueDeclaration
                            instanceof JavaParserSymbolDeclaration) {
                        VariableDeclarator variableDeclarator =
                                (VariableDeclarator)
                                        ((JavaParserSymbolDeclaration)
                                                (resolvedValueDeclaration)).
                                                getWrappedNode();
                        if (variableDeclarator.getType().toString().
                                equals("Scanner")) {
                            SimpleName simpleName = new SimpleName();
                            simpleName.setIdentifier("nextBigInteger");
                            n.setName(simpleName);
                        }
                    }
                    if (resolvedValueDeclaration
                            instanceof JavaParserFieldDeclaration) {
                        FieldDeclaration fieldDeclaration =
                                ((JavaParserFieldDeclaration)
                                        (resolvedValueDeclaration)).
                                        getWrappedNode();
                        if (fieldDeclaration.getVariable(0).getType().
                                toString().equals("Scanner")) {
                            SimpleName simpleName = new SimpleName();
                            simpleName.setIdentifier("nextBigInteger");
                            n.setName(simpleName);
                        }
                    }
                }
            }
        }

        @Override
        public void visit(
                final VariableDeclarator n,
                final JavaParserFacade javaParserFacade) {
            super.visit(n, javaParserFacade);
            if (n.getType().isPrimitiveType()) {
                if (n.getType().asPrimitiveType().equals(
                        PrimitiveType.intType())) {
                    ClassOrInterfaceType classOrInterfaceType =
                            new ClassOrInterfaceType(new ClassOrInterfaceType(
                                    new ClassOrInterfaceType("java"),
                                    "math"), "BigInteger");
                    n.setType(classOrInterfaceType);
                }
            }
        }
    }
}
