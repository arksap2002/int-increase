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

    private FieldAccessExpr fieldAccessExpr = new FieldAccessExpr(
            new FieldAccessExpr(new NameExpr("java"), "math"), "BigInteger");

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
        if (resolvedN.getPackageName().equals("java.lang") &&
                resolvedN.getClassName().equals("Math") &&
                resolvedN.getName().equals("abs")) {
            if (n.getArguments().get(0).isNameExpr()) {
                n.replace(new MethodCallExpr(
                        n.getArguments().get(0), "abs"));
            } else if (n.getArguments().get(0).isUnaryExpr() ||
                    n.getArguments().get(0).isIntegerLiteralExpr()) {
                n.replace(new MethodCallExpr(new MethodCallExpr(
                        fieldAccessExpr, "valueOf", n.getArguments()), "abs"));
            } else {
                throw new UnsupportedOperationException();
            }
        }
        if (resolvedN.getPackageName().equals("java.lang") &&
                resolvedN.getClassName().equals("Math") &&
                resolvedN.getName().equals("min")) {
            minOrMaxChanging("min", n);
        }
        if (resolvedN.getPackageName().equals("java.lang") &&
                resolvedN.getClassName().equals("Math") &&
                resolvedN.getName().equals("max")) {
            minOrMaxChanging("max", n);
        }
    }

    private void minOrMaxChanging(String string, MethodCallExpr n) {
        NodeList<Expression> nodeList = n.getArguments();
        Expression expressionFirst = null;
        Expression expressionSecond = null;
        for (int i = 0; i < 2; i++) {
            MethodCallExpr methodCallExpr = null;
            if (nodeList.get(i).isIntegerLiteralExpr()) {
                methodCallExpr = new MethodCallExpr(fieldAccessExpr, "valueOf",
                        new NodeList<>(nodeList.get(i)));
            } else if (nodeList.get(i).isUnaryExpr()) {
                if (nodeList.get(i).asUnaryExpr().getOperator().equals(UnaryExpr.
                        Operator.MINUS)) {
                    methodCallExpr = new MethodCallExpr(new MethodCallExpr(
                            fieldAccessExpr,"valueOf", new NodeList<>(
                                    nodeList.get(i).asUnaryExpr().
                            getExpression())), "negate");
                } else if (nodeList.get(i).asUnaryExpr().getOperator().equals(
                        UnaryExpr.Operator.PLUS)) {
                    methodCallExpr = new MethodCallExpr(fieldAccessExpr,
                            "valueOf", new NodeList<>(nodeList.get(i).
                            asUnaryExpr().getExpression()));
                } else {
                    throw new UnsupportedOperationException();
                }
            }
            //NameExpr
            //MethodCallExpr
            if (i == 0) {
                expressionFirst = methodCallExpr;
            } else {
                expressionSecond = methodCallExpr;
            }
        }
        n.replace(new MethodCallExpr(expressionFirst, string,
                new NodeList<>(expressionSecond)));
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
