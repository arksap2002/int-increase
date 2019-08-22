import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
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
            if (isInt(n)) {
                if (n.getInitializer().isPresent()) {
                    changingInitializerOfVariableDeclarator(n.getInitializer().
                            get());
                }
                ClassOrInterfaceType classOrInterfaceType =
                        new ClassOrInterfaceType(new ClassOrInterfaceType(
                                new ClassOrInterfaceType("java"),
                                "math"), "BigInteger");
                n.setType(classOrInterfaceType);
            }
        }

        private void changingInitializerOfVariableDeclarator(
                final Expression n) {
            if (n.isIntegerLiteralExpr()) {
                n.replace(initializerIntegerLiteralExpr(n.
                        asIntegerLiteralExpr().asInt()));
            }
            if (n.isBinaryExpr()) {
                changingInitializerOfVariableDeclarator(n.asBinaryExpr().
                        getLeft());
                changingInitializerOfVariableDeclarator(n.asBinaryExpr().
                        getRight());
            }
            if (n.isUnaryExpr()) {
                changingInitializerOfVariableDeclarator(n.asUnaryExpr().
                        getExpression());
                MethodCallExpr methodCallExpr = new MethodCallExpr();
                methodCallExpr.setScope(n.asUnaryExpr().getExpression());
                methodCallExpr.setName("negative");
                n.replace(methodCallExpr);
            }
        }

        private Expression initializerIntegerLiteralExpr(
                final int number) {
            if (number == 0) {
                return new FieldAccessExpr(
                        new NameExpr("BigInteger"), "ZERO");
            }
            if (number == 1) {
                return new FieldAccessExpr(
                        new NameExpr("BigInteger"), "ONE");
            }
            if (number == 2) {
                return new FieldAccessExpr(
                        new NameExpr("BigInteger"), "TWO");
            }
            if (number == /*CHECKSTYLE:OFF*/10/*CHECKSTYLE:ON*/) {
                return new FieldAccessExpr(
                        new NameExpr("BigInteger"), "TEN");
            }
            MethodCallExpr methodCallExpr = new MethodCallExpr(
                    "valueOf");
            methodCallExpr.setArguments(new NodeList<>(
                    new IntegerLiteralExpr(number)));
            methodCallExpr.setScope(
                    new NameExpr("BigInteger"));
            return methodCallExpr;
        }

        private boolean isInt(final VariableDeclarator n) {
            return n.getType().isPrimitiveType() && n.getType().
                    asPrimitiveType().equals(PrimitiveType.intType());
        }
    }
}
