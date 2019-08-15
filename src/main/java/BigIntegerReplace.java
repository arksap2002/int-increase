import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.*;
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
            if ((n.getType().isPrimitiveType() && n.getType().
                    asPrimitiveType().equals(PrimitiveType.intType()))
                    ||
                    (n.getType().isClassOrInterfaceType() && n.getType().
                            asClassOrInterfaceType().getName().toString().
                            equals("BigInteger"))) {
                if (n.getInitializer().isPresent()) {
                    changingInitializerOfVariableDeclarator(n);
                }
            }
            if (n.getType().isPrimitiveType() && n.getType().
                    asPrimitiveType().equals(PrimitiveType.intType())) {
                ClassOrInterfaceType classOrInterfaceType =
                        new ClassOrInterfaceType(new ClassOrInterfaceType(
                                new ClassOrInterfaceType("java"),
                                "math"), "BigInteger");
                n.setType(classOrInterfaceType);
            }
        }

        private void changingInitializerOfVariableDeclarator(
                VariableDeclarator n) {
            if (n.getInitializer().get().isIntegerLiteralExpr()) {
                int number = n.getInitializer().get().
                        asIntegerLiteralExpr().asInt();
                n.setInitializer(integerLiteralExprToBigIntegerValueOf(number));
            }
            if (n.getInitializer().get().isUnaryExpr()) {
                int number = (-1) * n.getInitializer().get().
                        asUnaryExpr().getExpression().
                        asIntegerLiteralExpr().asInt();
                n.setInitializer(unaryExprToBigIntegerValueOf(number));
            }
            if (n.getInitializer().get().isBinaryExpr()) {
                // left
                if (n.getInitializer().get().asBinaryExpr().getLeft().
                        isIntegerLiteralExpr()) {
                    int number = n.getInitializer().get().asBinaryExpr().
                            getLeft().asIntegerLiteralExpr().asInt();
                    n.getInitializer().get().asBinaryExpr().setLeft(
                            integerLiteralExprToBigIntegerValueOf(number));
                }
                if (n.getInitializer().get().asBinaryExpr().getLeft().
                        isUnaryExpr()) {
                    int number = (-1) * n.getInitializer().get().
                            asBinaryExpr().getLeft().asUnaryExpr().
                            getExpression().asIntegerLiteralExpr().asInt();
                    n.getInitializer().get().asBinaryExpr().setLeft(
                            unaryExprToBigIntegerValueOf(number));
                }
                // right
                if (n.getInitializer().get().asBinaryExpr().getRight().
                        isIntegerLiteralExpr()) {
                    int number = n.getInitializer().get().asBinaryExpr().
                            getRight().asIntegerLiteralExpr().asInt();
                    n.getInitializer().get().asBinaryExpr().setRight(
                            integerLiteralExprToBigIntegerValueOf(number));
                }
                if (n.getInitializer().get().asBinaryExpr().getRight().
                        isUnaryExpr()) {
                    int number = (-1) * n.getInitializer().get().
                            asBinaryExpr().getRight().asUnaryExpr().
                            getExpression().asIntegerLiteralExpr().asInt();
                    n.getInitializer().get().asBinaryExpr().setRight(
                            unaryExprToBigIntegerValueOf(number));
                }
            }
        }

        private Expression unaryExprToBigIntegerValueOf(int number) {
            MethodCallExpr methodCallExpr = new MethodCallExpr(
                    "valueOf");
            methodCallExpr.setArguments(new NodeList<>(
                    new IntegerLiteralExpr(number)));
            methodCallExpr.setScope(new NameExpr("BigInteger"));
            return methodCallExpr;
        }

        private Expression integerLiteralExprToBigIntegerValueOf(int number) {
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
            //CHECKSTYLE:OFF
            if (number == 10) {
                //CHECKSTYLE:ON
                return new FieldAccessExpr(
                        new NameExpr("BigInteger"), "TEN");
            }
            //CHECKSTYLE:ON
            MethodCallExpr methodCallExpr = new MethodCallExpr(
                    "valueOf");
            methodCallExpr.setArguments(new NodeList<>(
                    new IntegerLiteralExpr(number)));
            methodCallExpr.setScope(
                    new NameExpr("BigInteger"));
            return methodCallExpr;
        }
    }
}
