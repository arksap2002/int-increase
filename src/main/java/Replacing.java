import com.github.javaparser.Range;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
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
import com.github.javaparser.ast.expr.ArrayCreationExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.ArrayType;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.declarations.ResolvedMethodDeclaration;
import com.github.javaparser.resolution.types.ResolvedPrimitiveType;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;
import com.github.javaparser.symbolsolver.javaparsermodel.declarations.JavaParserFieldDeclaration;
import com.github.javaparser.symbolsolver.javaparsermodel.declarations.JavaParserMethodDeclaration;
import com.github.javaparser.symbolsolver.javaparsermodel.declarations.JavaParserParameterDeclaration;
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

    private final HashSet<Range> variablesToReplace = new HashSet<>();

    private final HashSet<Range> methodDeclarationInCode = new HashSet<>();

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
            } else if (resolvedN instanceof JavaParserMethodDeclaration
                    && ((JavaParserMethodDeclaration) (resolvedN)).
                    getWrappedNode().getRange().isPresent()
                    && methodDeclarationInCode.contains(((
                    JavaParserMethodDeclaration) (resolvedN)).
                    getWrappedNode().getRange().get())) {
                MethodDeclaration methodDeclaration = (
                        (JavaParserMethodDeclaration) (resolvedN)).
                        getWrappedNode();
                if (n.asMethodCallExpr().getArguments().size()
                        != methodDeclaration.getParameters().size()) {
                    throw new IllegalArgumentException();
                }
                if (!methodDeclaration.getRange().isPresent()) {
                    throw new IllegalArgumentException();
                }
                if (!variablesToReplace.contains(methodDeclaration.getRange().
                        get())) {
                    changes.add(() -> n.replace(bigIntFromInt(n.
                            asMethodCallExpr().getArguments())));
                }
                for (int i = 0; i < methodDeclaration.getParameters().size();
                     i++) {
                    if (!methodDeclaration.getParameter(i).getRange().
                            isPresent()) {
                        throw new IllegalArgumentException();
                    }
                    if (variablesToReplace.contains(methodDeclaration.
                            getParameter(i).getRange().get())) {
                        updateIntsToBigInt(
                                n.asMethodCallExpr().getArgument(i));
                    } else if (isUpdateIntsToBitInt(
                            n.asMethodCallExpr().getArgument(i))) {
                        updateIntsToBigInt(
                                n.asMethodCallExpr().getArgument(i));
                        int finalI = i;
                        changes.add(() -> n.asMethodCallExpr().
                                getArgument(finalI).replace(intValueMaking(
                                        n.clone().asMethodCallExpr().
                                                getArgument(finalI))));
                    }
                }
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
//                TODO is Array -> exception
                changes.add(() -> n.replace(bigIntFromInt(
                        new NodeList<>(n.clone()))));
            }
            return;
        }
        if (n.isArrayAccessExpr()) {
            if (!isVariableToReplace(getNameOfArray(
                    n.asArrayAccessExpr().getName()))) {
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

    private NameExpr getNameOfArray(Expression n) {
        if (n.isArrayAccessExpr()) {
            return getNameOfArray(n.asArrayAccessExpr().getName());
        }
        if (n.isNameExpr()) {
            return n.asNameExpr();
        }
        throw new IllegalArgumentException();
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
        if (n.resolve() instanceof JavaParserFieldDeclaration) {
            VariableDeclarator variableDeclarator =
                    ((JavaParserFieldDeclaration) n.resolve()).
                    getVariableDeclarator();
            if (!variableDeclarator.getRange().isPresent()) {
                throw new IllegalArgumentException();
            }
            return variablesToReplace.contains(variableDeclarator.
                    getRange().get());
        } else if (n.resolve() instanceof JavaParserSymbolDeclaration) {
            VariableDeclarator variableDeclarator = (VariableDeclarator)
                    ((JavaParserSymbolDeclaration) (n.resolve())).
                            getWrappedNode();
            if (!variableDeclarator.getRange().isPresent()) {
                throw new IllegalArgumentException();
            }
            return variablesToReplace.contains(variableDeclarator.
                    getRange().get());
        } else if (n.resolve() instanceof JavaParserParameterDeclaration) {
            Parameter parameter = ((JavaParserParameterDeclaration) n.resolve()).
                    getWrappedNode();
            if (!parameter.getRange().isPresent()) {
                throw new IllegalArgumentException();
            }
            return variablesToReplace.contains(parameter.
                    getRange().get());
        } else {
            return false;
        }
    }

    private ArrayType getLastArrayTypeOf(final ArrayType typeN) {
        if (!typeN.getComponentType().isArrayType()) {
            return typeN;
        }
        return getLastArrayTypeOf(typeN.getComponentType().asArrayType());
    }

    private void setLastArrayTypeOf(final ArrayType typeN) {
        if (!typeN.getComponentType().isArrayType()) {
            changes.add(() -> typeN.setComponentType(bigIntegerType));
            return;
        }
        setLastArrayTypeOf(typeN.getComponentType().asArrayType());
    }

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
            } else if (resolvedN instanceof JavaParserMethodDeclaration
                    && ((JavaParserMethodDeclaration) (resolvedN)).
                    getWrappedNode().getRange().isPresent()
                    && methodDeclarationInCode.contains(((
                    JavaParserMethodDeclaration) (resolvedN)).
                    getWrappedNode().getRange().get())) {
                MethodDeclaration methodDeclaration = (
                        (JavaParserMethodDeclaration) (resolvedN)).
                        getWrappedNode();
                if (!methodDeclaration.getRange().isPresent()) {
                    throw new IllegalArgumentException();
                }
                if (n.asMethodCallExpr().getArguments().size()
                        != methodDeclaration.getParameters().size()) {
                    throw new IllegalArgumentException();
                }
                boolean flag = variablesToReplace.contains(
                        methodDeclaration.getRange().get());
                for (int i = 0; i < methodDeclaration.getParameters().size();
                     i++) {
                    if (!methodDeclaration.getParameter(i).getRange().
                            isPresent()) {
                        throw new IllegalArgumentException();
                    }
                    flag = flag || isUpdateIntsToBitInt(
                            n.asMethodCallExpr().getArgument(i));
                }
                return flag;
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
        if (n.isArrayAccessExpr()) {
            return isVariableToReplace(getNameOfArray(
                    n.asArrayAccessExpr().getName()));
        }
        return false;
    }

    public class TransformVisitor
            extends VoidVisitorAdapter<JavaParserFacade> {

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
            if (variablesToReplace.contains(n.getRange().get())) {
                if (n.getType().isArrayType()) {
                    changes.add(() -> getLastArrayTypeOf(n.getType().
                            asArrayType()).setComponentType(bigIntegerType));
                } else {
                    throw new IllegalArgumentException();
                }
                if (n.getInitializer().isPresent()
                        && n.getInitializer().get().isArrayCreationExpr()) {
                    if (n.getInitializer().get().asArrayCreationExpr().
                            getElementType().equals(PrimitiveType.intType())) {
                        changes.add(() -> n.getInitializer().get().
                                asArrayCreationExpr().setElementType(
                                bigIntegerType));
                    } else {
                        throw new IllegalArgumentException();
                    }
                } else if (n.getInitializer().isPresent()
                        && n.getInitializer().get().isArrayInitializerExpr()) {
                    arrayInitializerExprToBigIntMaking(n.getInitializer().get().
                            asArrayInitializerExpr());
                } else {
                    throw new IllegalArgumentException();
                }
            } else if (n.getInitializer().isPresent()
                    && n.getInitializer().get().isArrayInitializerExpr()) {
                updateArrayInitializerExprValues(n.getInitializer().get().
                        asArrayInitializerExpr());
            }  // do nothing

            if (n.getInitializer().isPresent()
                    && n.getInitializer().get().isArrayCreationExpr()) {
                updateArrayCreationExprLevels(n.getInitializer().get().
                        asArrayCreationExpr());
            }  // do nothing

        }

        private void updateArrayCreationExprLevels(final ArrayCreationExpr n) {
            for (int i = 0; i < n.getLevels().size(); i++) {
                if (n.getLevels().get(i).getDimension().isPresent()) {
                    if (isUpdateIntsToBitInt(n.getLevels().get(i).
                            getDimension().get())) {
                        updateIntsToBigInt(n.getLevels().get(i).
                                getDimension().get());
                        int finalI = i;
                        changes.add(() -> n.getLevels().
                                get(finalI).setDimension(intValueMaking(
                                n.clone().getLevels().get(finalI).
                                        getDimension().get())));
                    }
                }
            }
        }

        private void updateArrayInitializerExprValues(final Expression n) {
            if (!n.isArrayInitializerExpr()) {
                if (isUpdateIntsToBitInt(n)) {
                    changes.add(() -> n.replace(intValueMaking(n.clone())));
                }
                return;
            }
            for (int i = 0; i < n.asArrayInitializerExpr().
                    getValues().size(); i++) {
                updateArrayInitializerExprValues(n.asArrayInitializerExpr().
                        getValues().get(i));
            }
        }

        private void arrayInitializerExprToBigIntMaking(final Expression n) {
            if (!n.isArrayInitializerExpr()) {
                updateIntsToBigInt(n);
                return;
            }
            for (int i = 0; i < n.asArrayInitializerExpr().
                    getValues().size(); i++) {
                arrayInitializerExprToBigIntMaking(n.asArrayInitializerExpr().
                        getValues().get(i));
            }
        }

        private void usualVariablesMaking(final VariableDeclarator n) {
            if (!n.getRange().isPresent()) {
                throw new IllegalArgumentException();
            }
            if (variablesToReplace.contains(n.getRange().get())) {
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

        private void updateIndexes(final ArrayAccessExpr n) {
            if (isUpdateIntsToBitInt(n.getIndex())) {
                updateIntsToBigInt(n.getIndex());
                changes.add(() -> n.setIndex(intValueMaking(
                        n.clone().getIndex())));
            }
            if (n.getName().isArrayAccessExpr()) {
                updateIndexes(n.getName().asArrayAccessExpr());
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
                updateIndexes(n.getTarget().asArrayAccessExpr());
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
                if (isUpdateIntsToBitInt(n.asMethodCallExpr().
                        getArgument(0))) {
                    updateIntsToBigInt(n.asMethodCallExpr().getArgument(0));
                    changes.add(() -> n.replace(new MethodCallExpr(
                            n.asMethodCallExpr().getArgument(0),
                            new SimpleName("toString"))));
                }
            }
        }

        @Override
        public void visit(
                final MethodDeclaration n,
                final JavaParserFacade javaParserFacade) {
            super.visit(n, javaParserFacade);
            if (n.getRange().isPresent()
                    && variablesToReplace.contains(n.getRange().get())) {
                changes.add(() -> n.setType(bigIntegerType));
                if (n.getBody().isPresent()) {
                    for (Statement statement : n.getBody().get().
                            getStatements()) {
//                      TODO Add useful code here
                        if (statement.isReturnStmt()) {
                            if (statement.asReturnStmt().getExpression().
                                    isPresent()) {
                                updateIntsToBigInt(statement.asReturnStmt().
                                        getExpression().get());
                            } // do nothing
                        }
                    }
                }
            }
        }

        @Override
        public void visit(
                final Parameter n,
                final JavaParserFacade javaParserFacade) {
            super.visit(n, javaParserFacade);
            if (!n.getRange().isPresent()) {
                throw new IllegalArgumentException();
            }
            if (variablesToReplace.contains(n.getRange().get())) {
                if (n.getType().equals(
                        PrimitiveType.intType())) {
                    changes.add(() -> n.setType(bigIntegerType));
                }
                if (n.getType().isArrayType()) {
//                    changes.add(() -> setLastArrayTypeOf(n.clone().getType().
//                            asArrayType()));
//                    or
//                    changes.add(() -> getLastArrayTypeOf(n.clone().getType().
//                            asArrayType()).setComponentType(bigIntegerType));
                }
            }
        }
    }

    public class FindAndAlterVariableDeclarators
            extends VoidVisitorAdapter<JavaParserFacade> {

        @Override
        public void visit(
                final MethodDeclaration n,
                final JavaParserFacade javaParserFacade) {
            super.visit(n, javaParserFacade);
            if (!n.getRange().isPresent()) {
                throw new IllegalArgumentException();
            }
            methodDeclarationInCode.add(n.getRange().get());
            if (n.getType().equals(PrimitiveType.intType())
                    && n.getName().getComment().isPresent()
                    && n.getName().getComment().get().
                    getContent().toLowerCase().trim().
                    equals("biginteger")) {
                variablesToReplace.add(n.getRange().get());
            }
            for (Parameter parameter : n.getParameters()) {
                boolean flag = false;
                if (parameter.getComment().isPresent()
                        && parameter.getComment().get().
                        getContent().toLowerCase().trim().
                        equals("biginteger")
                        && ifTypeOfToChange(parameter.getType())) {
                    flag = true;
                    parameter.getComment().get().remove();
                }
                if (parameter.getName().getComment().isPresent()
                        && parameter.getName().getComment().get().getContent().
                        toLowerCase().trim().equals("biginteger")
                        && ifTypeOfToChange(parameter.getType())) {
                    flag = true;
                    parameter.getName().getComment().get().remove();
                }
                if (flag) {
                    if (!parameter.getRange().isPresent()) {
                        throw new IllegalArgumentException();
                    }
                    variablesToReplace.add(parameter.getRange().
                            get());
                }
            }
        }

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
                        && ifTypeOfToChange(declarator.getType())) {
                    flag = true;
                    declarator.getComment().get().remove();
                }
                if (declarator.getInitializer().isPresent()
                        && declarator.getInitializer().get().
                        getComment().isPresent()
                        && declarator.getInitializer().get().
                        getComment().get().getContent().toLowerCase().trim().
                        equals("biginteger") && ifTypeOfToChange(
                        declarator.getType())) {
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
                    variablesToReplace.add(variableDeclarator.getRange().
                            get());
                }
            }
        }

        private boolean ifTypeOfToChange(
                final Type declarator) {
            if (declarator.equals(PrimitiveType.intType())) {
                return true;
            }
            if (declarator.isArrayType()) {
                return getLastArrayTypeOf(declarator.asArrayType()).
                        getComponentType().equals(
                        PrimitiveType.intType());
            }
            return false;
        }
    }
}
