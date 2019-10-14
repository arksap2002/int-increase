import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.declarations.ResolvedMethodDeclaration;
import com.github.javaparser.resolution.types.ResolvedPrimitiveType;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Replacing {

    private ArrayList<Runnable> changes = new ArrayList<>();

    private FieldAccessExpr fieldAccessExpr = new FieldAccessExpr(
            new FieldAccessExpr(
                    new NameExpr("java"), "math"), "BigInteger");

    private static final Map<AssignExpr.Operator, String>
            OPERATOR_OF_ASSIGN = new HashMap<>();

    static {
        OPERATOR_OF_ASSIGN.put(AssignExpr.Operator.PLUS, "add");
        OPERATOR_OF_ASSIGN.put(AssignExpr.Operator.MINUS, "subtract");
        OPERATOR_OF_ASSIGN.put(AssignExpr.Operator.DIVIDE, "divide");
        OPERATOR_OF_ASSIGN.put(AssignExpr.Operator.MULTIPLY, "multiply");
        OPERATOR_OF_ASSIGN.put(AssignExpr.Operator.REMAINDER, "remainder");
    }

    private static final Map<BinaryExpr.Operator, String>
            OPERATOR_OF_BINARY = new HashMap<>();

    static {
        OPERATOR_OF_BINARY.put(BinaryExpr.Operator.PLUS, "add");
        OPERATOR_OF_BINARY.put(BinaryExpr.Operator.MINUS, "subtract");
        OPERATOR_OF_BINARY.put(BinaryExpr.Operator.DIVIDE, "divide");
        OPERATOR_OF_BINARY.put(BinaryExpr.Operator.MULTIPLY, "multiply");
        OPERATOR_OF_BINARY.put(BinaryExpr.Operator.REMAINDER, "remainder");
    }

    private ClassOrInterfaceType bigIntegerType =
            new ClassOrInterfaceType(new ClassOrInterfaceType(
                    new ClassOrInterfaceType("java"), "math"), "BigInteger");

    static void doReplace(final CompilationUnit compilationUnit,
                          final ReflectionTypeSolver reflectionTypeSolver) {
        Replacing replacing = new Replacing();
        replacing.mainReplace(compilationUnit, reflectionTypeSolver);
    }

    private void mainReplace(final CompilationUnit compilationUnit,
                             final ReflectionTypeSolver reflectionTypeSolver) {
        compilationUnit.accept(new TransformVisitor(),
                JavaParserFacade.get(reflectionTypeSolver));
        for (Runnable change : changes) {
            change.run();
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

    private boolean isMath(final ResolvedMethodDeclaration resolvedN) {
        return resolvedN.getPackageName().equals("java.lang")
                && resolvedN.getClassName().equals("Math");
    }

    private void changingOfBinaryExpr(final BinaryExpr binaryExpr) {
        makingAfter(binaryExpr.asBinaryExpr().getLeft());
        makingAfter(binaryExpr.asBinaryExpr().getRight());
        if (binaryExpr.getOperator().equals(
                BinaryExpr.Operator.EQUALS)) {
            changes.add(() -> binaryExpr.replace(new MethodCallExpr(
                    binaryExpr.asBinaryExpr().getLeft(),
                    new SimpleName("equals"),
                    new NodeList<>(binaryExpr.asBinaryExpr().getRight()))));
        } else if (binaryExpr.getOperator().equals(
                BinaryExpr.Operator.NOT_EQUALS)) {
            changes.add(() -> binaryExpr.replace(new UnaryExpr(
                    new MethodCallExpr(binaryExpr.asBinaryExpr().getLeft(),
                            new SimpleName("equals"),
                            new NodeList<>(binaryExpr.asBinaryExpr().
                                    getRight())),
                    UnaryExpr.Operator.LOGICAL_COMPLEMENT)));
        } else if (binaryExpr.getOperator().equals(
                BinaryExpr.Operator.GREATER)
                || binaryExpr.getOperator().equals(
                BinaryExpr.Operator.GREATER_EQUALS)
                || binaryExpr.getOperator().equals(
                BinaryExpr.Operator.LESS)
                || binaryExpr.getOperator().equals(
                BinaryExpr.Operator.LESS_EQUALS)) {
            changes.add(() -> binaryExpr.setLeft(new MethodCallExpr(
                    binaryExpr.asBinaryExpr().getLeft(),
                    new SimpleName("compareTo"),
                    new NodeList<>(binaryExpr.asBinaryExpr().getRight()))));
            changes.add(() -> binaryExpr.setRight(new IntegerLiteralExpr(0)));
        } else {
            changes.add(() -> binaryExpr.replace(new MethodCallExpr(
                    binaryExpr.asBinaryExpr().getLeft(),
                    OPERATOR_OF_BINARY.get(binaryExpr.getOperator()),
                    new NodeList<>(binaryExpr.asBinaryExpr().getRight()))));
        }
    }

    private void makingAfter(final Expression n) {
        if (n.isIntegerLiteralExpr()) {
            changes.add(() -> n.replace(createIntegerLiteralExpr(
                    n.asIntegerLiteralExpr().asInt())));
        }
        if (n.isMethodCallExpr()) {
            ResolvedMethodDeclaration resolvedN = n.asMethodCallExpr().
                    resolve();
            if (n.asMethodCallExpr().resolve().getName().equals("nextInt")
                    && n.asMethodCallExpr().resolve().getPackageName().
                    equals("java.util") && n.asMethodCallExpr().resolve().
                    getClassName().equals("Scanner")) {
                if (n.asMethodCallExpr().getScope().isPresent()) {
                    changes.add(() -> n.asMethodCallExpr().setName(
                            new SimpleName("nextBigInteger")));
                }
            }
            else if (isMath(resolvedN) && (resolvedN.getName().equals("abs"))) {
                if (isOfTypeInt(n.asMethodCallExpr().getArguments().
                        get(0))) {
                    makingAfter(n.asMethodCallExpr().getArguments().get(0));
                    changes.add(() -> n.replace(new MethodCallExpr(
                            n.asMethodCallExpr().getArguments().get(0),
                            new SimpleName("abs"))));
                }
            }
            else if (isMath(resolvedN) && resolvedN.getName().equals("min")) {
                if (isOfTypeInt(n.asMethodCallExpr().getArguments().
                        get(0)) && isOfTypeInt(n.asMethodCallExpr().
                        getArguments().get(1))) {
                    makingAfter(n.asMethodCallExpr().getArguments().get(0));
                    makingAfter(n.asMethodCallExpr().getArguments().get(1));
                    changes.add(() -> n.replace(new MethodCallExpr(
                            n.asMethodCallExpr().getArguments().get(0),
                            "min", new NodeList<>(
                            n.asMethodCallExpr().getArguments().get(1)))));
                }
            }
            else if (isMath(resolvedN) && resolvedN.getName().equals("max")) {
                if (isOfTypeInt(n.asMethodCallExpr().getArguments().
                        get(0)) && isOfTypeInt(n.asMethodCallExpr().
                        getArguments().get(1))) {
                    makingAfter(n.asMethodCallExpr().getArguments().get(0));
                    makingAfter(n.asMethodCallExpr().getArguments().get(1));
                    changes.add(() -> n.replace(new MethodCallExpr(
                            n.asMethodCallExpr().getArguments().get(0),
                            "max", new NodeList<>(
                            n.asMethodCallExpr().getArguments().get(1)))));
                }
            }
            else if (isMath(resolvedN)) {
                changes.add(() -> n.replace(new MethodCallExpr(
                        fieldAccessExpr, "valueOf",
                        new NodeList<>(n.clone().asMethodCallExpr()))));
            }
            else if (resolvedN.getName().equals("parseInt") && n.asMethodCallExpr().
                    getArguments().size() == 1 && resolvedN.getPackageName().
                    equals("java.lang") && resolvedN.getClassName().
                    equals("Integer")) {
                changes.add(() -> n.replace(new ObjectCreationExpr(
                        null, bigIntegerType,
                        n.asMethodCallExpr().getArguments())));
            }
        } else if (n.isBinaryExpr()) {
            changingOfBinaryExpr(n.asBinaryExpr());
        } else if (n.isEnclosedExpr()) {
            makingAfter(n.asEnclosedExpr().getInner());
        } else if (n.isUnaryExpr()) {
            makingAfter(n.asUnaryExpr().getExpression());
            if (n.asUnaryExpr().getOperator().equals(UnaryExpr.
                    Operator.MINUS)) {
                changes.add(() -> n.replace(new MethodCallExpr(
                        n.asUnaryExpr().getExpression(), "negate")));
            } else if (n.asUnaryExpr().getOperator().equals(UnaryExpr.
                    Operator.PLUS)) {
                changes.add(() -> n.replace(
                        n.asUnaryExpr().getExpression()));
            } else if (n.asUnaryExpr().getOperator().equals(
                    UnaryExpr.Operator.POSTFIX_INCREMENT)
                    && n.asUnaryExpr().getExpression().isNameExpr()
                    && isOfTypeInt(
                    n.asUnaryExpr().getExpression().asNameExpr())) {
                changes.add(() -> n.replace(new AssignExpr(
                        n.asUnaryExpr().getExpression().asNameExpr(),
                        new MethodCallExpr(createIntegerLiteralExpr(1),
                                new SimpleName("add"), new NodeList<>(
                                n.asUnaryExpr().getExpression().
                                        asNameExpr())),
                        AssignExpr.Operator.ASSIGN)));
            } else if (n.asUnaryExpr().getOperator().equals(
                    UnaryExpr.Operator.POSTFIX_DECREMENT)
                    && n.asUnaryExpr().getExpression().isNameExpr()
                    && isOfTypeInt(
                    n.asUnaryExpr().getExpression().asNameExpr())) {
                changes.add(() -> n.replace(new AssignExpr(
                        n.asUnaryExpr().getExpression().asNameExpr(),
                        new MethodCallExpr(createIntegerLiteralExpr(1),
                                new SimpleName("subtract"), new NodeList<>(
                                n.asUnaryExpr().getExpression().
                                        asNameExpr())),
                        AssignExpr.Operator.ASSIGN)));
            } else if (!n.asUnaryExpr().getOperator().equals(UnaryExpr.
                    Operator.LOGICAL_COMPLEMENT)) {
                throw new UnsupportedOperationException();
            }
        } else if (n.isNameExpr()) {
            if (!isOfTypeInt(n)) {
                throw new UnsupportedOperationException();
            }
        }
    }

    private boolean isOfTypeInt(final Expression n) {
        return (n.calculateResolvedType().equals(ResolvedPrimitiveType.INT));
    }

    private class TransformVisitor
            extends VoidVisitorAdapter<JavaParserFacade> {

        @Override
        public void visit(
                final VariableDeclarator n,
                final JavaParserFacade javaParserFacade) {
            super.visit(n, javaParserFacade);
            if (n.getType().equals(PrimitiveType.intType())) {
                if (n.getInitializer().isPresent()) {
                    makingAfter(n.getInitializer().get());
                }
                changes.add(() -> n.setType(bigIntegerType));
            }
        }

        @Override
        public void visit(
                final IfStmt n,
                final JavaParserFacade javaParserFacade) {
            super.visit(n, javaParserFacade);
            makingAfter(n.getCondition());
        }

        @Override
        public void visit(
                final AssignExpr n,
                final JavaParserFacade javaParserFacade) {
            super.visit(n, javaParserFacade);
            if (isOfTypeInt(n.getTarget())) {
                makingAfter(n.getValue());
                if (!n.getOperator().equals(AssignExpr.Operator.ASSIGN)) {
                    changes.add(() -> n.replace(new AssignExpr(n.getTarget(),
                            new MethodCallExpr(
                                    n.getValue(), OPERATOR_OF_ASSIGN.get(
                                    n.getOperator()),
                                    new NodeList<>(n.getTarget())),
                            AssignExpr.Operator.ASSIGN)));
                }
            }
        }

        @Override
        public void visit(
                final ExpressionStmt n,
                final JavaParserFacade javaParserFacade) {
            super.visit(n, javaParserFacade);
            makingAfter(n.getExpression());
        }
    }
}
