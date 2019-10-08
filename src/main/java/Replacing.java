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
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.declarations.ResolvedMethodDeclaration;
import com.github.javaparser.resolution.types.ResolvedPrimitiveType;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import java.util.ArrayList;

class Replacing {

    private ArrayList<Runnable> changes = new ArrayList<>();

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

    private boolean isMath(final ResolvedMethodDeclaration resolvedN) {
        return resolvedN.getPackageName().equals("java.lang")
                && resolvedN.getClassName().equals("Math");
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

    private void makingAfter(final Expression n) {
        if (n.isIntegerLiteralExpr()) {
            changes.add(() -> n.replace(createIntegerLiteralExpr(
                    n.asIntegerLiteralExpr().asInt())));
        } else if (n.isMethodCallExpr()) {
            ResolvedMethodDeclaration resolvedN = n.asMethodCallExpr().
                    resolve();
            if (n.asMethodCallExpr().getScope().isPresent()
                    && n.asMethodCallExpr().getScope().get().isNameExpr()
                    && n.asMethodCallExpr().getScope().get().asNameExpr().
                    getName().toString().equals("Integer")
                    && n.asMethodCallExpr().getName().toString().
                    equals("toString")) {
                makingAfter(n.asMethodCallExpr().getArgument(0));
                changes.add(() -> n.replace(new MethodCallExpr(
                        n.asMethodCallExpr().getArgument(0),
                        new SimpleName("toString"))));
            }
            if (n.asMethodCallExpr().resolve().getName().equals("nextInt")
                    && n.asMethodCallExpr().resolve().getPackageName().
                    equals("java.util") && n.asMethodCallExpr().resolve().
                    getClassName().equals("Scanner")) {
                if (n.asMethodCallExpr().getScope().isPresent()) {
                    changes.add(() -> n.asMethodCallExpr().setName(
                            new SimpleName("nextBigInteger")));
                }
            }
            if (isMath(resolvedN) && (resolvedN.getName().equals("abs"))) {
                if (isOfTypeInt(n.asMethodCallExpr().getArguments().
                        get(0))) {
                    makingAfter(n.asMethodCallExpr().getArguments().get(0));
                    changes.add(() -> n.replace(new MethodCallExpr(
                            n.asMethodCallExpr().getArguments().get(0),
                            new SimpleName("abs"))));
                }
            }
            if (isMath(resolvedN) && resolvedN.getName().equals("min")) {
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
            if (isMath(resolvedN) && resolvedN.getName().equals("max")) {
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
            if (resolvedN.getName().equals("parseInt") && n.asMethodCallExpr().
                    getArguments().size() == 1 && resolvedN.getPackageName().
                    equals("java.lang") && resolvedN.getClassName().
                    equals("Integer")) {
                changes.add(() -> n.replace(new ObjectCreationExpr(
                        null, bigIntegerType,
                        n.asMethodCallExpr().getArguments())));
            }
        } else if (n.isBinaryExpr()) {
            makingAfter(n.asBinaryExpr().getLeft());
            makingAfter(n.asBinaryExpr().getRight());
            changes.add(() -> n.replace(new MethodCallExpr(
                    n.asBinaryExpr().getLeft(),
                    operationOfBinaryExpr(n.asBinaryExpr()),
                    new NodeList<>(n.asBinaryExpr().getRight()))));
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
            } else {
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
                changes.add(() -> n.setType(new ClassOrInterfaceType(
                        new ClassOrInterfaceType(
                                new ClassOrInterfaceType("java"),
                                "math"), "BigInteger")));
                changes.add(() -> n.setType(bigIntegerType));
            }
        }
    }
}
