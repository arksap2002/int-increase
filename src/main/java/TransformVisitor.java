import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.declarations.ResolvedMethodDeclaration;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;

class TransformVisitor
        extends VoidVisitorAdapter<JavaParserFacade> {

    @Override
    public void visit(
            final MethodCallExpr n,
            final JavaParserFacade javaParserFacade) {
        super.visit(n, javaParserFacade);
        ResolvedMethodDeclaration resolvedN = n.resolve();
        if (isMath(resolvedN) && resolvedN.getName().equals("abs")) {
            mathAbsChanging(n, javaParserFacade);
        }
        if (isMath(resolvedN) && resolvedN.getName().equals("min")) {
            mathMinOrMaxChanging("min", n, javaParserFacade);
        }
        if (isMath(resolvedN) && resolvedN.getName().equals("max")) {
            mathMinOrMaxChanging("max", n, javaParserFacade);
        }
        if (resolvedN.getName().equals("nextInt") && resolvedN.
                getPackageName().equals("java.util") && resolvedN.
                getClassName().equals("Scanner")) {
            n.setName(new SimpleName("nextBigInteger"));
        }
    }

    private void mathAbsChanging(final MethodCallExpr n,
                                 final JavaParserFacade javaParserFacade) {
        if (n.getArguments().get(0).isIntegerLiteralExpr()) {
            n.replace(new MethodCallExpr(createIntegerLiteralExpr(n.
                    getArguments().get(0).asIntegerLiteralExpr().asInt()),
                    "abs"));
        } else if (n.getArguments().get(0).isUnaryExpr()) {
            if (n.getArguments().get(0).asUnaryExpr().getOperator().equals(
                    UnaryExpr.Operator.MINUS)) {
                n.replace(new MethodCallExpr(new MethodCallExpr(
                        createIntegerLiteralExpr(n.getArguments().get(0).
                                asUnaryExpr().getExpression().
                                asIntegerLiteralExpr().asInt()), "negate"),
                        "abs"));
            } else if (n.getArguments().get(0).asUnaryExpr().getOperator().
                    equals(UnaryExpr.Operator.PLUS)) {
                n.replace(new MethodCallExpr(createIntegerLiteralExpr(
                        n.getArguments().get(0).asUnaryExpr().
                                getExpression().asIntegerLiteralExpr().
                                asInt()), "abs"));
            } else {
                throw new UnsupportedOperationException();
            }
        } else if (n.getArguments().get(0).isMethodCallExpr()) {
            visit(n.getArguments().get(0).
                    asMethodCallExpr(), javaParserFacade);
            n.replace(new MethodCallExpr(
                    n.getArguments().get(0).asMethodCallExpr(), "abs"));
        } else {
            throw new UnsupportedOperationException();
        }
    }

    private boolean isMath(final ResolvedMethodDeclaration resolvedN) {
        return resolvedN.getPackageName().equals("java.lang")
                && resolvedN.getClassName().equals("Math");
    }

    private void mathMinOrMaxChanging(final String string,
                                final MethodCallExpr n,
                                final JavaParserFacade javaParserFacade) {
        NodeList<Expression> nodeList = n.getArguments();
        Expression expressionFirst = null;
        Expression expressionSecond = null;
        for (int i = 0; i < 2; i++) {
            Expression expressionNow;
            Expression expr = nodeList.get(i);
            if (expr.isIntegerLiteralExpr()) {
                expressionNow = createIntegerLiteralExpr(
                        expr.asIntegerLiteralExpr().asInt());
            } else if (expr.isUnaryExpr()) {
                if (expr.asUnaryExpr().getOperator().equals(UnaryExpr.
                        Operator.MINUS)) {
                    expressionNow = new MethodCallExpr(
                            createIntegerLiteralExpr(expr.asUnaryExpr().
                                    getExpression().asIntegerLiteralExpr().
                                    asInt()), "negate");
                } else if (expr.asUnaryExpr().getOperator().equals(
                        UnaryExpr.Operator.PLUS)) {
                    expressionNow = createIntegerLiteralExpr(expr.
                            asUnaryExpr().getExpression().
                            asIntegerLiteralExpr().asInt());
                } else {
                    throw new UnsupportedOperationException();
                }
            } else if (expr.isMethodCallExpr()) {
                visit(expr.asMethodCallExpr(), javaParserFacade);
                expressionNow = nodeList.get(i).asMethodCallExpr();
            } else {
                throw new UnsupportedOperationException();
            }
            if (i == 0) {
                expressionFirst = expressionNow;
            } else {
                expressionSecond = expressionNow;
            }
        }
        n.replace(new MethodCallExpr(expressionFirst, string,
                new NodeList<>(expressionSecond)));
    }

    @Override
    public void visit(
            final VariableDeclarator n,
            final JavaParserFacade javaParserFacade) {
        super.visit(n, javaParserFacade);
        if (n.getType().equals(PrimitiveType.intType())) {
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
            final Expression n, final JavaParserFacade javaParserFacade) {
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
