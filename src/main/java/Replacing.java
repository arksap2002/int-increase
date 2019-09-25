import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import java.util.ArrayList;

class Replacing {

    private ArrayList<Runnable> changes = new ArrayList<>();

    static void doReplace(final CompilationUnit compilationUnit,
                          final ReflectionTypeSolver reflectionTypeSolver) {
        Replacing replacing = new Replacing();
        replacing.mainReplace(compilationUnit, reflectionTypeSolver);
    }

    private void mainReplace(final CompilationUnit compilationUnit,
                             final ReflectionTypeSolver reflectionTypeSolver) {
        compilationUnit.accept(new TransformVisitor(),
                JavaParserFacade.get(reflectionTypeSolver));
        for (Runnable change: changes) {
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

    private void makingAfter(final Expression n) {
        if (n.isIntegerLiteralExpr()) {
            changes.add(() -> n.replace(createIntegerLiteralExpr(
                    n.asIntegerLiteralExpr().asInt())));
        }
        if (n.isMethodCallExpr()) {
            if (n.asMethodCallExpr().resolve().getName().equals("nextInt")
                    && n.asMethodCallExpr().resolve().getPackageName().
                    equals("java.util") && n.asMethodCallExpr().resolve().
                    getClassName().equals("Scanner")) {
                if (n.asMethodCallExpr().getScope().isPresent()) {
                    changes.add(() -> n.asMethodCallExpr().setName(
                            new SimpleName("nextBigInteger")));
                }
            }
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
                if (n.getInitializer().isPresent()) {
                    makingAfter(n.getInitializer().get());
                }
                changes.add(() -> n.setType(new ClassOrInterfaceType(new ClassOrInterfaceType(
                        new ClassOrInterfaceType("java"),
                        "math"), "BigInteger")));

            }
        }
    }
}
