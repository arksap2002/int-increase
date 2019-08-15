import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import static com.github.javaparser.ast.Node.SYMBOL_RESOLVER_KEY;

public final class BigIntegerReplace {

    private static boolean flagBinaryExprSearch = false;

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
                    changingInitializerOfVariableDeclarator(n,
                            javaParserFacade);
                }
            }
            changingType(n);
        }

        @Override
        public void visit(
                final BinaryExpr n,
                final JavaParserFacade javaParserFacade) {
            super.visit(n, javaParserFacade);
            if (flagBinaryExprSearch) {
                flagBinaryExprSearch = false;
                binaryExprWorking(n);
            }
        }

        private void binaryExprWorking(final BinaryExpr n) {
            // left
            if (n.getLeft().isIntegerLiteralExpr()) {
                int number = n.getLeft().asIntegerLiteralExpr().asInt();
                n.setLeft(integerLiteralExprToBigIntegerValueOf(number));
            }
            if (n.getLeft().isUnaryExpr()) {
                int number = (-1) * n.getLeft().asUnaryExpr().
                        getExpression().asIntegerLiteralExpr().asInt();
                n.setLeft(unaryExprToBigIntegerValueOf(number));
            }
            // right
            if (n.getRight().isIntegerLiteralExpr()) {
                int number = n.getRight().asIntegerLiteralExpr().asInt();
                n.setRight(integerLiteralExprToBigIntegerValueOf(number));
            }
            if (n.getRight().isUnaryExpr()) {
                int number = (-1) * n.getRight().asUnaryExpr().
                        getExpression().asIntegerLiteralExpr().asInt();
                n.setRight(unaryExprToBigIntegerValueOf(number));
            }
        }

        private void changingType(final VariableDeclarator n) {
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
                final VariableDeclarator n,
                final JavaParserFacade javaParserFacade) {
            if (n.getInitializer().get().isIntegerLiteralExpr()) {
                initializerIntegerLiteralExpr(n);
            }
            if (n.getInitializer().get().isUnaryExpr()) {
                initializerUnaryExpr(n);
            }
            if (n.getInitializer().get().isBinaryExpr()) {
                BinaryExpr binaryExpr = n.getInitializer().get().
                        asBinaryExpr();
                binaryExprWorking(binaryExpr);
                while (binaryExpr.getLeft().isBinaryExpr()
                        || binaryExpr.getRight().isBinaryExpr()) {
                    flagBinaryExprSearch = true;
                    if (binaryExpr.getLeft().isBinaryExpr()) {
                        binaryExpr = binaryExpr.getLeft().asBinaryExpr();
                        visit(binaryExpr, javaParserFacade);
                    }
                    if (binaryExpr.getRight().isBinaryExpr()) {
                        binaryExpr = binaryExpr.getRight().asBinaryExpr();
                        visit(binaryExpr, javaParserFacade);
                    }
                }
            }
        }

        private void initializerUnaryExpr(final VariableDeclarator n) {
            int number = (-1) * n.getInitializer().get().
                    asUnaryExpr().getExpression().
                    asIntegerLiteralExpr().asInt();
            n.setInitializer(unaryExprToBigIntegerValueOf(number));
        }

        private void initializerIntegerLiteralExpr(
                final VariableDeclarator n) {
            int number = n.getInitializer().get().
                    asIntegerLiteralExpr().asInt();
            n.setInitializer(integerLiteralExprToBigIntegerValueOf(number));
        }

        private Expression unaryExprToBigIntegerValueOf(final int number) {
            MethodCallExpr methodCallExpr = new MethodCallExpr(
                    "valueOf");
            methodCallExpr.setArguments(new NodeList<>(
                    new IntegerLiteralExpr(number)));
            methodCallExpr.setScope(new NameExpr("BigInteger"));
            return methodCallExpr;
        }

        private Expression integerLiteralExprToBigIntegerValueOf(
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
