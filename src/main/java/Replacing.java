import com.github.javaparser.Range;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.expr.ArrayAccessExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.type.ArrayType;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.declarations.ResolvedMethodDeclaration;
import com.github.javaparser.resolution.types.ResolvedPrimitiveType;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;
import com.github.javaparser.symbolsolver.javaparsermodel.declarations.JavaParserFieldDeclaration;
import com.github.javaparser.symbolsolver.javaparsermodel.declarations.JavaParserSymbolDeclaration;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;

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

    private final HashSet<Range> variableDeclsToReplace = new HashSet<>();

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
        compilationUnit.accept(new FindAndAlterVariableDeclarators(),
                JavaParserFacade.get(reflectionTypeSolver));
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
            return bigIntFromInt(new NodeList<>(
                    new IntegerLiteralExpr(number)));
        }
    }

    private boolean isMath(final ResolvedMethodDeclaration resolvedN) {
        return resolvedN.getPackageName().equals("java.lang")
                && resolvedN.getClassName().equals("Math");
    }

    private void changingOfBinaryExpr(final BinaryExpr binaryExpr) {
        updateIntsToBigInt(binaryExpr.asBinaryExpr().getLeft());
        updateIntsToBigInt(binaryExpr.asBinaryExpr().getRight());
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

    private void updateIntsToBigInt(final Expression n) {
        if (n.isIntegerLiteralExpr()) {
            changes.add(() -> n.replace(createIntegerLiteralExpr(
                    n.asIntegerLiteralExpr().asInt())));
            return;
        }
        if (n.isMethodCallExpr()) {
            ResolvedMethodDeclaration resolvedN = n.asMethodCallExpr().
                    resolve();
            if (resolvedN.getQualifiedName().
                    equals("java.util.Scanner.nextInt")) {
                if (n.asMethodCallExpr().getScope().isPresent()) {
                    changes.add(() -> n.asMethodCallExpr().setName(
                            new SimpleName("nextBigInteger")));
                }
            } else if (isMath(resolvedN)
                    && (resolvedN.getName().equals("abs"))) {
                if (isOfTypeInt(n.asMethodCallExpr().getArguments().
                        get(0))) {
                    updateIntsToBigInt(n.asMethodCallExpr().getArguments().
                            get(0));
                    changes.add(() -> n.replace(new MethodCallExpr(
                            n.asMethodCallExpr().getArguments().get(0),
                            new SimpleName("abs"))));
                }
            } else if (isMath(resolvedN) && resolvedN.getName().equals("min")) {
                if (isOfTypeInt(n.asMethodCallExpr().getArguments().
                        get(0)) && isOfTypeInt(n.asMethodCallExpr().
                        getArguments().get(1))) {
                    updateIntsToBigInt(n.asMethodCallExpr().getArguments().
                            get(0));
                    updateIntsToBigInt(n.asMethodCallExpr().getArguments().
                            get(1));
                    changes.add(() -> n.replace(new MethodCallExpr(
                            n.asMethodCallExpr().getArguments().get(0),
                            "min", new NodeList<>(
                            n.asMethodCallExpr().getArguments().get(1)))));
                }
            } else if (isMath(resolvedN) && resolvedN.getName().equals("max")) {
                if (isOfTypeInt(n.asMethodCallExpr().getArguments().get(0))
                        && isOfTypeInt(n.asMethodCallExpr().
                        getArguments().get(1))) {
                    updateIntsToBigInt(n.asMethodCallExpr().getArguments().
                            get(0));
                    updateIntsToBigInt(n.asMethodCallExpr().getArguments().
                            get(1));
                    changes.add(() -> n.replace(new MethodCallExpr(
                            n.asMethodCallExpr().getArguments().get(0),
                            "max", new NodeList<>(
                            n.asMethodCallExpr().getArguments().get(1)))));
                }
            } else if (resolvedN.getQualifiedName().
                    equals("java.lang.Integer.parseInt")
                    && n.asMethodCallExpr().getArguments().size() == 1) {
                changes.add(() -> n.replace(bigIntFromInt(n.
                        asMethodCallExpr().getArguments())));
            } else if ((resolvedN.getQualifiedName().
                    equals("java.io.PrintWriter.print")
                    || resolvedN.getQualifiedName().
                    equals("java.io.PrintWriter.println")
                    || resolvedN.getQualifiedName().
                    equals("java.io.PrintStream.print")
                    || resolvedN.getQualifiedName().
                    equals("java.io.PrintStream.println"))
                    && n.asMethodCallExpr().getArguments().size() == 1) {
                updateIntsToBigInt(n.asMethodCallExpr().getArgument(0));
            } else {
                changes.add(() -> n.replace(bigIntFromInt(new NodeList<>(
                        n.clone().asMethodCallExpr()))));
            }
            return;
        }
        if (n.isBinaryExpr()) {
            changingOfBinaryExpr(n.asBinaryExpr());
            return;
        }
        if (n.isEnclosedExpr()) {
            updateIntsToBigInt(n.asEnclosedExpr().getInner());
            return;
        }
        if (n.isUnaryExpr()) {
            updateIntsToBigInt(n.asUnaryExpr().getExpression());
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
            return;
        }
        if (n.isNameExpr()) {
            if (!isVariableToReplace(n.asNameExpr())) {
                changes.add(() -> n.replace(bigIntFromInt(
                        new NodeList<>(n.clone()))));
            }
            return;
        }
        if (!isOfTypeInt(n)) {
            throw new UnsupportedOperationException();
        }
        changes.add(() -> n.replace(bigIntFromInt(
                new NodeList<>(n.clone()))));
    }

    private ObjectCreationExpr bigIntFromInt(
            final NodeList<Expression> expressions) {
        return new ObjectCreationExpr(null, bigIntegerType, expressions);
    }

    private MethodCallExpr intValueMaking(final Expression expression) {
        return new MethodCallExpr(expression, new SimpleName("intValue"));
    }

    private boolean isOfTypeInt(final Expression n) {
        return n.calculateResolvedType().equals(ResolvedPrimitiveType.INT);
    }

    private boolean isVariableToReplace(final NameExpr n) {
        VariableDeclarator variableDeclarator;
        if (n.resolve() instanceof JavaParserFieldDeclaration) {
            variableDeclarator = ((JavaParserFieldDeclaration) n.resolve()).
                    getVariableDeclarator();
        } else if (n.resolve() instanceof JavaParserSymbolDeclaration) {
            variableDeclarator = (VariableDeclarator)
                    ((JavaParserSymbolDeclaration) (n.resolve())).
                            getWrappedNode();
        } else {
            return false;
        }
        if (!variableDeclarator.getRange().isPresent()) {
            throw new IllegalArgumentException();
        }
        return variableDeclsToReplace.contains(variableDeclarator.
                getRange().get());
    }

    private ArrayType getLastArrayTypeOf(final ArrayType typeN) {
        if (!typeN.getComponentType().isArrayType()) {
            return typeN;
        }
        return getLastArrayTypeOf(typeN.getComponentType().asArrayType());
    }

    public class TransformVisitor
            extends VoidVisitorAdapter<JavaParserFacade> {

        private boolean isUpdateIntsToBitInt(final Expression n) {
            if (n.isIntegerLiteralExpr()) {
                return false;
            }
            if (n.isMethodCallExpr()) {
                ResolvedMethodDeclaration resolvedN = n.asMethodCallExpr().
                        resolve();
                if (isMath(resolvedN)
                        && (resolvedN.getName().equals("abs"))) {
                    if (isOfTypeInt(n.asMethodCallExpr().getArguments().
                            get(0))) {
                        return isUpdateIntsToBitInt(n.asMethodCallExpr().
                                getArguments().get(0));
                    }
                } else if (isMath(resolvedN) && (resolvedN.getName().
                        equals("min")
                        || resolvedN.getName().equals("max"))) {
                    if (isOfTypeInt(n.asMethodCallExpr().getArguments().
                            get(0)) && isOfTypeInt(n.asMethodCallExpr().
                            getArguments().get(1))) {
                        return isUpdateIntsToBitInt(n.asMethodCallExpr().
                                getArguments().get(0))
                                || isUpdateIntsToBitInt(n.asMethodCallExpr().
                                getArguments().get(1));
                    }
                } else if ((resolvedN.getQualifiedName().
                        equals("java.io.PrintWriter.print")
                        || resolvedN.getQualifiedName().
                        equals("java.io.PrintWriter.println")
                        || resolvedN.getQualifiedName().
                        equals("java.io.PrintStream.print")
                        || resolvedN.getQualifiedName().
                        equals("java.io.PrintStream.println"))
                        && n.asMethodCallExpr().getArguments().size() == 1) {
                    return isUpdateIntsToBitInt(n.asMethodCallExpr().
                            getArgument(0));
                }
                return false;
            }
            if (n.isBinaryExpr()) {
                return isUpdateIntsToBitInt(n.asBinaryExpr().getLeft())
                        || isUpdateIntsToBitInt(n.asBinaryExpr().getRight());
            }
            if (n.isEnclosedExpr()) {
                return isUpdateIntsToBitInt(n.asEnclosedExpr().getInner());
            }
            if (n.isUnaryExpr()) {
                if (n.asUnaryExpr().getOperator().equals(
                        UnaryExpr.Operator.POSTFIX_INCREMENT)
                        && n.asUnaryExpr().getExpression().isNameExpr()
                        && isOfTypeInt(
                        n.asUnaryExpr().getExpression().asNameExpr())) {
                    return isVariableToReplace(n.asUnaryExpr().
                            getExpression().asNameExpr());
                }
                if (n.asUnaryExpr().getOperator().equals(
                        UnaryExpr.Operator.POSTFIX_DECREMENT)
                        && n.asUnaryExpr().getExpression().isNameExpr()
                        && isOfTypeInt(
                        n.asUnaryExpr().getExpression().asNameExpr())) {
                    return isVariableToReplace(n.asUnaryExpr().
                            getExpression().asNameExpr());
                }
                return isUpdateIntsToBitInt(n.asUnaryExpr().getExpression());
            }
            if (n.isNameExpr()) {
                return isVariableToReplace(n.asNameExpr());
            }
            return false;
        }

        @Override
        public void visit(
                final VariableDeclarator n,
                final JavaParserFacade javaParserFacade) {
            super.visit(n, javaParserFacade);
            if (n.getType().equals(PrimitiveType.intType())) {
                usualVariablesMaking(n);
            }
            if (n.getType().isArrayType()) {
                if (getLastArrayTypeOf(n.getType().asArrayType()).
                        getComponentType().equals(PrimitiveType.intType())) {
                    arrayVariablesMaking(n);
                }
            }
        }

        private void arrayVariablesMaking(final VariableDeclarator n) {
            if (!n.getRange().isPresent()) {
                throw new IllegalArgumentException();
            }
            if (variableDeclsToReplace.contains(n.getRange().get())) {
                if (n.getType().isArrayType()) {
                    changes.add(() -> getLastArrayTypeOf(n.getType().
                            asArrayType()).setComponentType(bigIntegerType));
                }
                if (n.getInitializer().isPresent()
                        && n.getInitializer().get().isArrayCreationExpr()) {
                    if (n.getInitializer().get().asArrayCreationExpr().
                            getElementType().equals(PrimitiveType.intType())) {
                        changes.add(() -> n.getInitializer().get().
                                asArrayCreationExpr().setElementType(
                                bigIntegerType));
                    }
                }
            }
            if (n.getInitializer().isPresent()
                    && n.getInitializer().get().isArrayCreationExpr()) {
                for (int i = 0; i < n.getInitializer().get().
                        asArrayCreationExpr().getLevels().size(); i++) {
                    if (n.getInitializer().get().asArrayCreationExpr().
                            getLevels().get(i).getDimension().isPresent()) {
                        if (isUpdateIntsToBitInt(n.getInitializer().get().
                                asArrayCreationExpr().getLevels().get(i).
                                getDimension().get())) {
                            updateIntsToBigInt(n.getInitializer().get().
                                    asArrayCreationExpr().getLevels().get(i).
                                    getDimension().get());
                            int finalI = i;
                            changes.add(() -> n.getInitializer().get().
                                    asArrayCreationExpr().getLevels().
                                    get(finalI).setDimension(intValueMaking(
                                    n.clone().getInitializer().get().
                                            asArrayCreationExpr().
                                            getLevels().get(finalI).
                                            getDimension().get())));
                        }
                    }
                }
            }
        }

        private void usualVariablesMaking(final VariableDeclarator n) {
            if (!n.getRange().isPresent()) {
                throw new IllegalArgumentException();
            }
            if (variableDeclsToReplace.contains(n.getRange().get())) {
                if (n.getInitializer().isPresent()) {
                    updateIntsToBigInt(n.getInitializer().get());
                }
                changes.add(() -> n.setType(bigIntegerType));
            } else if (n.getInitializer().isPresent()) {
                if (isUpdateIntsToBitInt(n.getInitializer().get())) {
                    updateIntsToBigInt(n.getInitializer().get());
                    changes.add(() -> n.setInitializer(intValueMaking(
                            n.clone().getInitializer().get())));
                }
            }
        }

        @Override
        public void visit(
                final IfStmt n,
                final JavaParserFacade javaParserFacade) {
            super.visit(n, javaParserFacade);
            if (isUpdateIntsToBitInt(n.getCondition())) {
                updateIntsToBigInt(n.getCondition());
            }
        }

        @Override
        public void visit(
                final AssignExpr n,
                final JavaParserFacade javaParserFacade) {
            super.visit(n, javaParserFacade);
            if (n.getTarget().isArrayAccessExpr()) {
                arrayAssignMaking(n);
            } else if (n.getTarget().isNameExpr()) {
                usualAssignMaking(n);
            }
        }

        private void indexesChanging(final ArrayAccessExpr n) {
            if (isUpdateIntsToBitInt(n.getIndex())) {
                updateIntsToBigInt(n.getIndex());
                changes.add(() -> n.setIndex(intValueMaking(
                        n.clone().getIndex())));
            }
            if (n.getName().isArrayAccessExpr()) {
                indexesChanging(n.getName().asArrayAccessExpr());
            }
        }

        private Expression getLastArrayAccessNameOf(final ArrayAccessExpr n) {
            if (n.getName().isArrayAccessExpr()) {
                return getLastArrayAccessNameOf(n.getName().
                        asArrayAccessExpr());
            }
            return n.getName();
        }

        private void arrayAssignMaking(final AssignExpr n) {
            if (n.getTarget().isArrayAccessExpr()) {
                Expression nameN = getLastArrayAccessNameOf(n.getTarget().
                        asArrayAccessExpr());
                if (nameN.isNameExpr() && isVariableToReplace(
                        nameN.asNameExpr())) {
                    updateIntsToBigInt(n.getValue());
                    if (!n.getOperator().equals(AssignExpr.Operator.ASSIGN)) {
                        changes.add(() -> n.replace(new AssignExpr(
                                n.getTarget(), new MethodCallExpr(
                                n.getValue(), OPERATOR_OF_ASSIGN.get(
                                n.getOperator()),
                                new NodeList<>(n.getTarget())),
                                AssignExpr.Operator.ASSIGN)));
                    }
                } else if (isUpdateIntsToBitInt(n.getValue())) {
                    updateIntsToBigInt(n.getValue());
                    changes.add(() -> n.setValue(intValueMaking(
                            n.clone().getValue())));
                }
                indexesChanging(n.getTarget().asArrayAccessExpr());
            }
        }

        private void usualAssignMaking(final AssignExpr n) {
            if (isVariableToReplace(n.getTarget().asNameExpr())) {
                updateIntsToBigInt(n.getValue());
                if (!n.getOperator().equals(AssignExpr.Operator.ASSIGN)) {
                    changes.add(() -> n.replace(new AssignExpr(
                            n.getTarget(), new MethodCallExpr(
                            n.getValue(), OPERATOR_OF_ASSIGN.get(
                            n.getOperator()),
                            new NodeList<>(n.getTarget())),
                            AssignExpr.Operator.ASSIGN)));
                }
            } else if (isUpdateIntsToBitInt(n.getValue())) {
                updateIntsToBigInt(n.getValue());
                changes.add(() -> n.setValue(intValueMaking(
                        n.clone().getValue())));
            }
        }

        @Override
        public void visit(
                final ExpressionStmt n,
                final JavaParserFacade javaParserFacade) {
            super.visit(n, javaParserFacade);
            if (isUpdateIntsToBitInt(n.getExpression())) {
                updateIntsToBigInt(n.getExpression());
            }
        }

        @Override
        public void visit(
                final ForStmt n,
                final JavaParserFacade javaParserFacade) {
            super.visit(n, javaParserFacade);
            if (n.getCompare().isPresent()) {
                if (isUpdateIntsToBitInt(n.getCompare().get())) {
                    updateIntsToBigInt(n.getCompare().get());
                }
            }
            for (int i = 0; i < n.getUpdate().size(); i++) {
                if (isUpdateIntsToBitInt(n.getUpdate().get(i))) {
                    updateIntsToBigInt(n.getUpdate().get(i));
                }
            }
        }

        @Override
        public void visit(
                final WhileStmt n,
                final JavaParserFacade javaParserFacade) {
            super.visit(n, javaParserFacade);
            if (isUpdateIntsToBitInt(n.getCondition())) {
                updateIntsToBigInt(n.getCondition());
            }
        }

        @Override
        public void visit(
                final MethodCallExpr n,
                final JavaParserFacade javaParserFacade) {
            super.visit(n, javaParserFacade);
            ResolvedMethodDeclaration resolvedN = n.asMethodCallExpr().
                    resolve();
            if (resolvedN.getQualifiedName().
                    equals("java.lang.Integer.toString")) {
                if (isUpdateIntsToBitInt(n.asMethodCallExpr().getArgument(0))) {
                    updateIntsToBigInt(n.asMethodCallExpr().getArgument(0));
                    changes.add(() -> n.replace(new MethodCallExpr(
                            n.asMethodCallExpr().getArgument(0),
                            new SimpleName("toString"))));
                }
            }
        }
    }

    public class FindAndAlterVariableDeclarators
            extends VoidVisitorAdapter<JavaParserFacade> {

        @Override
        public void visit(
                final FieldDeclaration n,
                final JavaParserFacade javaParserFacade) {
            super.visit(n, javaParserFacade);
            enumerateVariables(n.getVariables());
        }

        @Override
        public void visit(
                final VariableDeclarationExpr n,
                final JavaParserFacade javaParserFacade) {
            super.visit(n, javaParserFacade);
            enumerateVariables(n.getVariables());
        }

        private void enumerateVariables(
                final NodeList<VariableDeclarator> nodeList) {
            boolean flag = false;
            for (VariableDeclarator declarator : nodeList) {
                if (declarator.getComment().isPresent()
                        && declarator.getComment().get().
                        getContent().toLowerCase().trim().
                        equals("biginteger")
                        && ifTypeToChange(declarator)) {
                    flag = true;
                    declarator.getComment().get().remove();
                }
                if (declarator.getInitializer().isPresent()
                        && declarator.getInitializer().get().
                        getComment().isPresent()
                        && declarator.getInitializer().get().
                        getComment().get().getContent().toLowerCase().trim().
                        equals("biginteger") && ifTypeToChange(declarator)) {
                    flag = true;
                    declarator.getInitializer().get().getComment().get().
                            remove();
                }
            }
            if (flag) {
                for (VariableDeclarator variableDeclarator : nodeList) {
                    if (!variableDeclarator.getRange().isPresent()) {
                        throw new IllegalArgumentException();
                    }
                    variableDeclsToReplace.add(variableDeclarator.getRange().
                            get());
                }
            }
        }

        private boolean ifTypeToChange(final VariableDeclarator declarator) {
            if (declarator.getType().equals(
                    PrimitiveType.intType())) {
                return true;
            }
            if (declarator.getType().isArrayType()) {
                return getLastArrayTypeOf(declarator.getType().
                        asArrayType()).getComponentType().equals(
                        PrimitiveType.intType());
            }
            return false;
        }
    }
}
