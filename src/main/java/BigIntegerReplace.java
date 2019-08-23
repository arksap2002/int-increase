import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.NameExpr;
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
            if (isInt(n)) {
                super.visit(n, javaParserFacade);
                if (n.getInitializer().isPresent()) {
                    changeInitializerOfVariableDeclarator(n.getInitializer().
                            get());
                }
                ClassOrInterfaceType classOrInterfaceType =
                        new ClassOrInterfaceType(new ClassOrInterfaceType(
                                new ClassOrInterfaceType("java"),
                                "math"), "BigInteger");
                n.setType(classOrInterfaceType);
            }
        }

        private void changeInitializerOfVariableDeclarator(
                final Expression n) {
            if (n.isIntegerLiteralExpr()) {
                n.replace(createIntegerLiteralExpr(n.
                        asIntegerLiteralExpr().asInt()));
            } else if (n.isBinaryExpr()) {
                changeInitializerOfVariableDeclarator(n.asBinaryExpr().
                        getLeft());
                changeInitializerOfVariableDeclarator(n.asBinaryExpr().
                        getRight());
                MethodCallExpr methodCallExpr = new MethodCallExpr();
                methodCallExpr.setScope(n.asBinaryExpr().getLeft());
                methodCallExpr.setArguments(new NodeList<>(n.asBinaryExpr().
                        getRight()));
                methodCallExpr.setName(operationOfBinaryExpr(
                        n.asBinaryExpr()));
                n.replace(methodCallExpr);
            } else if (n.isUnaryExpr()) {
                changeInitializerOfVariableDeclarator(n.asUnaryExpr().
                        getExpression());
                if (n.asUnaryExpr().getOperator().equals(UnaryExpr.
                        Operator.MINUS)) {
                    MethodCallExpr methodCallExpr = new MethodCallExpr(
                            n.asUnaryExpr().getExpression(), "negative");
                    n.replace(methodCallExpr);
                } else if (n.asUnaryExpr().getOperator().equals(UnaryExpr.
                        Operator.PLUS)) {
                    changeInitializerOfVariableDeclarator(n.asUnaryExpr().
                            getExpression());
                }
            } else if (n.isEnclosedExpr()) {
                changeInitializerOfVariableDeclarator(n.asEnclosedExpr().
                        getInner());
            }
        }

        private String operationOfBinaryExpr(final BinaryExpr binaryExpr) {
            if (binaryExpr.getOperator().equals(BinaryExpr.Operator.PLUS)) {
                return "add";
            }
            if (binaryExpr.getOperator().equals(BinaryExpr.Operator.MINUS)) {
                return "subtract";
            }
            if (binaryExpr.getOperator().equals(BinaryExpr.Operator.MULTIPLY)) {
                return "multiply";
            }
            if (binaryExpr.getOperator().equals(BinaryExpr.Operator.DIVIDE)) {
                return "divide";
            }
            return "remainder";
        }

        private Expression createIntegerLiteralExpr(
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
