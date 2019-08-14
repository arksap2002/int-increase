import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
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

    static class TransformVisitor
            extends VoidVisitorAdapter<JavaParserFacade> {
        @Override
        public void visit(
                final VariableDeclarator n,
                final JavaParserFacade javaParserFacade) {
            super.visit(n, javaParserFacade);
            if (n.getType().isPrimitiveType()) {
                if (n.getType().asPrimitiveType().equals(
                        PrimitiveType.intType())) {
                    if (n.getInitializer().get().isIntegerLiteralExpr()) {
                        int number = n.getInitializer().get().
                                asIntegerLiteralExpr().asInt();
                        boolean flag = false;
                        if (number == 0) {
                            flag = true;
                            n.setInitializer(new FieldAccessExpr(
                                    new NameExpr("BigInteger"), "ZERO"));
                        }
                        if (number == 1) {
                            flag = true;
                            n.setInitializer(new FieldAccessExpr(
                                    new NameExpr("BigInteger"), "ONE"));
                        }
                        if (number == 2) {
                            flag = true;
                            n.setInitializer(new FieldAccessExpr(
                                    new NameExpr("BigInteger"), "TWO"));
                        }
                        if (number == 10) {
                            flag = true;
                            n.setInitializer(new FieldAccessExpr(
                                    new NameExpr("BigInteger"), "TEN"));
                        }
                        if (!flag) {
                            MethodCallExpr methodCallExpr = new MethodCallExpr(
                                    "valueOf");
                            methodCallExpr.setArguments(new NodeList<>(
                                    new IntegerLiteralExpr(number)));
                            methodCallExpr.setScope(
                                    new NameExpr("BigInteger"));
                            n.setInitializer(methodCallExpr);
                        }
                    }
                    if (n.getInitializer().get().isUnaryExpr()) {
                        int number = (-1) * n.getInitializer().get().
                                asUnaryExpr().getExpression().
                                asIntegerLiteralExpr().asInt();
                        MethodCallExpr methodCallExpr = new MethodCallExpr(
                                "valueOf");
                        methodCallExpr.setArguments(new NodeList<>(
                                new IntegerLiteralExpr(number)));
                        methodCallExpr.setScope(new NameExpr("BigInteger"));
                        n.setInitializer(methodCallExpr);
                    }
                    ClassOrInterfaceType classOrInterfaceType =
                            new ClassOrInterfaceType(new ClassOrInterfaceType(
                                    new ClassOrInterfaceType("java"),
                                    "math"), "BigInteger");
                    n.setType(classOrInterfaceType);
                }
            }
        }
    }
}
