import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

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
                final VariableDeclarator n,
                final JavaParserFacade javaParserFacade) {
            if (n.getType().equals(PrimitiveType.intType())) {
                super.visit(n, javaParserFacade);
                if (n.getInitializer().isPresent()) {
                    try {
                        changeInitializerOfVariableDeclarator(n.
                                getInitializer().get());
                    } catch (UnhandledObjectException e) {
                        e.printStackTrace();
                    }
                }
                n.setType(new ClassOrInterfaceType(new ClassOrInterfaceType(
                        new ClassOrInterfaceType("java"),
                        "math"), "BigInteger"));
            }
        }

        private void changeInitializerOfVariableDeclarator(
                final Expression n) throws UnhandledObjectException {

            if (n.isIntegerLiteralExpr()) {
                n.replace(createIntegerLiteralExpr(n.
                        asIntegerLiteralExpr().asInt()));
            } else if (n.isBinaryExpr()) {
                changeInitializerOfVariableDeclarator(n.asBinaryExpr().
                        getLeft());
                changeInitializerOfVariableDeclarator(n.asBinaryExpr().
                        getRight());
            } else {
                throw new UnhandledObjectException("Object " + n.toString()
                        + " is unhandled");
            }
        }

        private Expression createIntegerLiteralExpr(
                final int number) {
            if (number == 0) {
                return new FieldAccessExpr(
                        new NameExpr("BigInteger"), "ZERO");
            } else if (number == 1) {
                return new FieldAccessExpr(
                        new NameExpr("BigInteger"), "ONE");
            } else if (number == 2) {
                return new FieldAccessExpr(
                        new NameExpr("BigInteger"), "TWO");
            } else if (number == /*CHECKSTYLE:OFF*/10/*CHECKSTYLE:ON*/) {
                return new FieldAccessExpr(
                        new NameExpr("BigInteger"), "TEN");
            } else {
                return new MethodCallExpr(
                        new NameExpr("BigInteger"), "valueOf",
                        new NodeList<>(new IntegerLiteralExpr(number)));
            }
        }
    }

    public class UnhandledObjectException extends Exception {

        public UnhandledObjectException(final String message) {
            super(message);
        }
    }
}
