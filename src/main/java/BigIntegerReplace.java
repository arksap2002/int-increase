import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.declarations.ResolvedMethodDeclaration;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import jdk.jshell.spi.ExecutionControl;

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

    class TransformVisitor
            extends VoidVisitorAdapter<JavaParserFacade> {
        @Override
        public void visit(
                final MethodCallExpr n,
                final JavaParserFacade javaParserFacade) {
            super.visit(n, javaParserFacade);
            ResolvedMethodDeclaration resolvedMethodDeclaration = n.resolve();
            if ((resolvedMethodDeclaration.getName().equals("nextInt"))) {
                if (resolvedMethodDeclaration.getPackageName().
                        equals("java.util") && resolvedMethodDeclaration.
                        getClassName().equals("Scanner")) {
                    n.setName(new SimpleName("nextBigInteger"));
                }
            }
        }

        @Override
        public void visit(
                final VariableDeclarator n,
                final JavaParserFacade javaParserFacade) {
            if (n.getType().equals(PrimitiveType.intType())) {
                super.visit(n, javaParserFacade);
                if (n.getInitializer().isPresent()) {
                    try {
                        changeInitializerOfVariableDeclarator(n.
                                getInitializer().get());
                    } catch (ExecutionControl.NotImplementedException e) {
                        e.printStackTrace();
                    }
                }
                n.setType(new ClassOrInterfaceType(new ClassOrInterfaceType(
                        new ClassOrInterfaceType("java"),
                        "math"), "BigInteger"));
            }
        }

        private void changeInitializerOfVariableDeclarator(
                final Expression n)
                throws ExecutionControl.NotImplementedException {
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
            } else if (n.isEnclosedExpr()) {
                changeInitializerOfVariableDeclarator(n.asEnclosedExpr().
                        getInner());
            } else {
                throw new ExecutionControl.NotImplementedException("Object "
                        + n.toString() + " is unhandled");
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
}
