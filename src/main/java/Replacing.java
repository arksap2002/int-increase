import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import java.util.ArrayList;

class Replacing {

    private ArrayList<Expression[]> expressions = new ArrayList<>();
    private ArrayList<VariableDeclarator> variableDeclaratorsBefore =
            new ArrayList<>();
    private ArrayList<Expression> variableDeclaratorsAfter =
            new ArrayList<>();

    final void doReplace(final CompilationUnit compilationUnit,
                         final ReflectionTypeSolver reflectionTypeSolver) {
        mainReplace(compilationUnit, reflectionTypeSolver);
    }

    private void mainReplace(final CompilationUnit compilationUnit,
                             final ReflectionTypeSolver reflectionTypeSolver) {
        compilationUnit.accept(new TransformVisitor(),
                JavaParserFacade.get(reflectionTypeSolver));
        for (Expression[] expression : expressions) {
            expression[0].replace(expression[1]);
        }
        for (int i = 0; i < variableDeclaratorsBefore.size(); i++) {
            variableDeclaratorsBefore.get(i).setType(new ClassOrInterfaceType(new ClassOrInterfaceType(
                    new ClassOrInterfaceType("java"),
                    "math"), "BigInteger"));
            variableDeclaratorsBefore.get(i).replace(variableDeclaratorsAfter.get(i));
        }
    }

    private void expressionsChanging(
            final Expression n) {
        if (n.isIntegerLiteralExpr()) {
            n.replace(createIntegerLiteralExpr(n.
                    asIntegerLiteralExpr().asInt()));
        } else if (n.isBinaryExpr()) {
            expressionsChanging(n.asBinaryExpr().getLeft());
            expressionsChanging(n.asBinaryExpr().getRight());
        } else if (n.isMethodCallExpr()) {
            if (n.asMethodCallExpr().resolve().getName().equals("nextInt")
                    && n.asMethodCallExpr().resolve().getPackageName().
                    equals("java.util") && n.asMethodCallExpr().resolve().
                    getClassName().equals("Scanner")) {
                n.asMethodCallExpr().setName(new SimpleName("nextBigInteger"));
            }
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

    private class TransformVisitor
            extends VoidVisitorAdapter<JavaParserFacade> {

        @Override
        public void visit(
                final VariableDeclarator n,
                final JavaParserFacade javaParserFacade) {
            super.visit(n, javaParserFacade);
            if (n.getType().equals(PrimitiveType.intType())) {
                variableDeclaratorsBefore.add(n);
                if (n.getInitializer().isPresent()) {
                    variableDeclaratorsAfter.add(makingAfter(
                            n.getInitializer().get()));
                }
            }
        }

        private Expression makingAfter(Expression n) {
            if (n.isIntegerLiteralExpr()) {
                return createIntegerLiteralExpr(n.asIntegerLiteralExpr().
                        asInt());
            } else if (n.isUnaryExpr()) {
                if (n.asUnaryExpr().getOperator().equals(
                        UnaryExpr.Operator.MINUS)) {
                    return new MethodCallExpr(n.asUnaryExpr().getExpression(),
                            "negate");
                } else if (n.asUnaryExpr().getOperator().equals(
                        UnaryExpr.Operator.PLUS)) {
                    variableDeclaratorsAfter.add(
                            n.asUnaryExpr().getExpression());
                }
                // add nameExpr
            }
            return n;
        }

        @Override
        public void visit(
                final MethodCallExpr n,
                final JavaParserFacade javaParserFacade) {
            super.visit(n, javaParserFacade);
            expressions.add(n);
        }

        @Override
        public void visit(
                final BinaryExpr n,
                final JavaParserFacade javaParserFacade) {
            super.visit(n, javaParserFacade);
            if ()
            expressions.add(n);
        }

        @Override
        public void visit(
                final EnclosedExpr n,
                final JavaParserFacade javaParserFacade) {
            super.visit(n, javaParserFacade);
            expressions.add(new Expression[]{n, n.asEnclosedExpr().getInner()});
        }
    }
}
