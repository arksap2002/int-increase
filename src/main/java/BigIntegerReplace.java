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

import java.util.ArrayList;

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
            changingType(n);
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
                final VariableDeclarator n) {
            if (n.getInitializer().get().isIntegerLiteralExpr()) {
                initializerIntegerLiteralExpr(n);
            }
            if (n.getInitializer().get().isUnaryExpr()) {
                initializerUnaryExpr(n);
            }
            if (n.getInitializer().get().isBinaryExpr()) {
                initializerBinaryExpr(n);
            }
        }

        private void initializerBinaryExpr(VariableDeclarator n) {
            BinaryExpr binaryExpr = n.getInitializer().get().
                    asBinaryExpr();
            ArrayList<BinaryExpr> binaryExprs = new ArrayList<>();
            int index = 0;
            binaryExprs.add(binaryExpr);
            while (index < binaryExprs.size()) {
                if (binaryExprs.get(index).getLeft().isBinaryExpr()) {
                    binaryExprs.add(binaryExprs.get(index).getLeft().
                            asBinaryExpr());
                } else {
                    if (binaryExprs.get(index).getLeft().
                            isIntegerLiteralExpr()) {
                        int number = binaryExprs.get(index).getLeft().
                                asIntegerLiteralExpr().asInt();
                        binaryExprs.get(index).setLeft(
                                integerLiteralExprToBigIntegerValueOf(number));
                    }
                    if (binaryExprs.get(index).getLeft().isUnaryExpr()) {
                        int number = (-1) * binaryExprs.get(index).getLeft().
                                asUnaryExpr().
                                getExpression().asIntegerLiteralExpr().asInt();
                        binaryExprs.get(index).setLeft(
                                unaryExprToBigIntegerValueOf(number));
                    }
                }
                if (binaryExprs.get(index).getRight().isBinaryExpr()) {
                    binaryExprs.add(binaryExprs.get(index).getRight().
                            asBinaryExpr());
                } else {
                    if (binaryExprs.get(index).getRight().
                            isIntegerLiteralExpr()) {
                        int number = binaryExprs.get(index).getRight().
                                asIntegerLiteralExpr().asInt();
                        binaryExprs.get(index).setRight(
                                integerLiteralExprToBigIntegerValueOf(number));
                    }
                    if (binaryExprs.get(index).getRight().isUnaryExpr()) {
                        int number = (-1) * binaryExprs.get(index).getRight().
                                asUnaryExpr().
                                getExpression().asIntegerLiteralExpr().asInt();
                        binaryExprs.get(index).setRight(
                                unaryExprToBigIntegerValueOf(number));
                    }
                }
                index++;
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
