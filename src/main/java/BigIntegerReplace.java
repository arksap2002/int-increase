import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.declarations.ResolvedMethodDeclaration;
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

    class TransformVisitor
            extends VoidVisitorAdapter<JavaParserFacade> {
        @Override
        public void visit(
                final MethodCallExpr n,
                final JavaParserFacade javaParserFacade) {
            super.visit(n, javaParserFacade);
            ResolvedMethodDeclaration resolvedN = n.resolve();
            if (resolvedN.getName().equals("nextInt") && resolvedN.
                    getPackageName().equals("java.util") && resolvedN.
                    getClassName().equals("Scanner")) {
                n.setName(new SimpleName("nextBigInteger"));
            }
        }

        @Override
        public void visit(
                final VariableDeclarator n,
                final JavaParserFacade javaParserFacade) {
            if (n.getType().equals(PrimitiveType.intType())) {
                super.visit(n, javaParserFacade);
                if (n.getInitializer().isPresent()) {
                    changeInitializerOfVariableDeclarator(n.getInitializer().
                            get(), javaParserFacade);
                }
                n.setType(new ClassOrInterfaceType(new ClassOrInterfaceType(
                        new ClassOrInterfaceType("java"),
                        "math"), "BigInteger"));
            }
        }

        private void changeInitializerOfVariableDeclarator(
                final Expression n,
                final JavaParserFacade javaParserFacade) {
            if (n.isIntegerLiteralExpr()) {
                n.replace(createIntegerLiteralExpr(n.
                        asIntegerLiteralExpr().asInt()));
            } else if (n.isBinaryExpr()) {
                changeInitializerOfVariableDeclarator(n.asBinaryExpr().
                        getLeft(), javaParserFacade);
                changeInitializerOfVariableDeclarator(n.asBinaryExpr().
                        getRight(), javaParserFacade);
            } else if (n.isMethodCallExpr()) {
                visit(n.asMethodCallExpr(), javaParserFacade);
            } else {
                throw new UnsupportedOperationException();
            }
        }

        private Expression createIntegerLiteralExpr(
                final int number) {
            FieldAccessExpr fieldAccessExpr = new FieldAccessExpr(
                    new FieldAccessExpr(
                            new NameExpr("java"), "math"), "BigInteger");
            if (number == 0) {
                return new FieldAccessExpr(
                        fieldAccessExpr, "ZERO");
            } else if (number == 1) {
                return new FieldAccessExpr(
                        fieldAccessExpr, "ONE");
            } else if (number == 2) {
                return new FieldAccessExpr(
                        fieldAccessExpr, "TWO");
            } else if (number == /*CHECKSTYLE:OFF*/10/*CHECKSTYLE:ON*/) {
                return new FieldAccessExpr(
                        fieldAccessExpr, "TEN");
            } else {
                return new MethodCallExpr(
                        fieldAccessExpr, "valueOf",
                        new NodeList<>(new IntegerLiteralExpr(number)));
            }
        }
    }
}
