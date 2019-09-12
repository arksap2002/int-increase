import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.declarations.ResolvedMethodDeclaration;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;

import com.github.javaparser.symbolsolver.javaparsermodel.declarations.JavaParserSymbolDeclaration;

class TransformVisitor
        extends VoidVisitorAdapter<JavaParserFacade> {
    @Override
    public void visit(
            final MethodCallExpr n,
            final JavaParserFacade javaParserFacade) {
        super.visit(n, javaParserFacade);
        if (n.getScope().isPresent() && n.getScope().get().isNameExpr()
                && n.getScope().get().asNameExpr().getName().toString().
                equals("Integer")
                && n.getName().toString().equals("toString")) {
            integerToStringChanging(n, javaParserFacade);
        } else {
            ResolvedMethodDeclaration resolvedN = n.resolve();
            if (resolvedN.getName().equals("nextInt") && resolvedN.
                    getPackageName().equals("java.util") && resolvedN.
                    getClassName().equals("Scanner")) {
                n.setName(new SimpleName("nextBigInteger"));
            }
        }
    }

    private void integerToStringChanging(final MethodCallExpr n,
                                         final JavaParserFacade
                                                 javaParserFacade) {
        if (n.getArgument(0).isNameExpr()) {
            if (n.getArgument(0).asNameExpr().
                    resolve() instanceof JavaParserSymbolDeclaration) {
                visit((VariableDeclarator)
                        ((JavaParserSymbolDeclaration)
                                (n.getArgument(0).asNameExpr().
                                        resolve())).
                                getWrappedNode(), javaParserFacade);
            }
            n.replace(new MethodCallExpr(n.getArgument(0).asNameExpr(),
                    "toString"));
        } else {
            changeInitializerOfVariableDeclarator(n.getArgument(0),
                    javaParserFacade);
            n.replace(new MethodCallExpr(n.getArgument(0), "toString"));
        }
    }

    @Override
    public void visit(
            final VariableDeclarator n,
            final JavaParserFacade javaParserFacade) {
        super.visit(n, javaParserFacade);
        if (n.getType().equals(PrimitiveType.intType())) {
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
        } else if (n.isMethodCallExpr()) {
            visit(n.asMethodCallExpr(), javaParserFacade);
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
}
