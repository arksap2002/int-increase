import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
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
            n.replace(new MethodCallExpr(n.asBinaryExpr().getLeft(),
                    operationOfBinaryExpr(n.asBinaryExpr()),
                    new NodeList<>(n.asBinaryExpr().getRight())));
        } else if (n.isUnaryExpr()) {
            changeInitializerOfVariableDeclarator(n.asUnaryExpr().
                    getExpression(), javaParserFacade);
            if (n.asUnaryExpr().getOperator().equals(UnaryExpr.
                    Operator.MINUS)) {
                n.replace(new MethodCallExpr(
                        n.asUnaryExpr().getExpression(), "negate"));
            } else if (n.asUnaryExpr().getOperator().equals(UnaryExpr.
                    Operator.PLUS)) {
                n.replace(n.asUnaryExpr().getExpression());
            } else {
                throw new UnsupportedOperationException();
            }
        } else if (n.isEnclosedExpr()) {
            changeInitializerOfVariableDeclarator(
                    n.asEnclosedExpr().getInner(), javaParserFacade);
        } else if (n.isMethodCallExpr()) {
            visit(n.asMethodCallExpr(), javaParserFacade);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    private String operationOfBinaryExpr(final BinaryExpr binaryExpr) {
        if (binaryExpr.getOperator().equals(
                BinaryExpr.Operator.PLUS)) {
            return "add";
        }
        if (binaryExpr.getOperator().equals(
                BinaryExpr.Operator.MINUS)) {
            return "subtract";
        }
        if (binaryExpr.getOperator().equals(
                BinaryExpr.Operator.MULTIPLY)) {
            return "multiply";
        }
        if (binaryExpr.getOperator().equals(
                BinaryExpr.Operator.DIVIDE)) {
            return "divide";
        }
        if (binaryExpr.getOperator().equals(
                BinaryExpr.Operator.REMAINDER)) {
            return "remainder";
        }
        throw new UnsupportedOperationException();
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
