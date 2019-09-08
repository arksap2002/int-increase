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

    private void changeMethodCallExpr(MethodCallExpr n) {
        ResolvedMethodDeclaration resolvedN = n.resolve();
        if (resolvedN.getName().equals("nextInt") && resolvedN.
                getPackageName().equals("java.util") && resolvedN.
                getClassName().equals("Scanner")) {
            n.setName(new SimpleName("nextBigInteger"));
        }
        if (isMath(resolvedN) && resolvedN.getName().equals("abs")) {
            if (n.getArguments().get(0).isIntegerLiteralExpr()) {
                n.replace(new MethodCallExpr(createIntegerLiteralExpr(n.
                        getArguments().get(0).asIntegerLiteralExpr().asInt()),
                        "abs"));
            } else if (n.getArguments().get(0).isUnaryExpr()) {
                //unary
            } else if (n.getArguments().get(0).isMethodCallExpr()) {
                //is min bigInt
                //is max bigInt
                //is abs bigInt
                changeMethodCallExpr(n.getArguments().get(0).
                        asMethodCallExpr());
//            } else if (n.getArguments().get(0).isNameExpr()) {
//                n.replace(new MethodCallExpr(
//                        n.getArguments().get(0), "abs"));
            }
        }
        if (isMath(resolvedN) && resolvedN.getName().equals("min")) {
            minOrMaxChanging("min", n);
        }
        if (isMath(resolvedN) && resolvedN.getName().equals("max")) {
            minOrMaxChanging("max", n);
        }
    }

    private boolean isMath(ResolvedMethodDeclaration resolvedN) {
        return resolvedN.getPackageName().equals("java.lang") &&
                resolvedN.getClassName().equals("Math");
    }

    private void minOrMaxChanging(String string, MethodCallExpr n) {
        NodeList<Expression> nodeList = n.getArguments();
        Expression expressionFirst = null;
        Expression expressionSecond = null;
        for (int i = 0; i < 2; i++) {
            MethodCallExpr methodCallExpr = null;
            Expression expr = nodeList.get(i);
            if (expr.isIntegerLiteralExpr()) {
                //0, 1, 2, 10
                methodCallExpr = new MethodCallExpr(fieldAccessExpr, "valueOf",
                        new NodeList<>(expr));
            } else if (expr.isUnaryExpr()) {
                if (expr.asUnaryExpr().getOperator().equals(UnaryExpr.
                        Operator.MINUS)) {
                    methodCallExpr = new MethodCallExpr(new MethodCallExpr(
                            fieldAccessExpr,"valueOf", new NodeList<>(
                            expr.asUnaryExpr().getExpression())), "negate");
                } else if (expr.asUnaryExpr().getOperator().equals(
                        UnaryExpr.Operator.PLUS)) {
                    methodCallExpr = new MethodCallExpr(fieldAccessExpr,
                            "valueOf", new NodeList<>(expr.
                            asUnaryExpr().getExpression()));
                } else {
                    throw new UnsupportedOperationException();
                }
//            } else if (expr.isMethodCallExpr()) {

            }
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
                        get());
            }
            n.setType(new ClassOrInterfaceType(new ClassOrInterfaceType(
                    new ClassOrInterfaceType("java"),
                    "math"), "BigInteger"));
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
        } else if (n.isMethodCallExpr()) {
            changeMethodCallExpr(n.asMethodCallExpr());
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
