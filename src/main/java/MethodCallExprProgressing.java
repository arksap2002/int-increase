import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.declarations.ResolvedMethodDeclaration;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;

import java.util.ArrayList;

class MethodCallExprProgressing
        extends VoidVisitorAdapter<JavaParserFacade> {

    private static ArrayList<ResolvedMethodDeclaration>
            resolvedMethodDeclarations = new ArrayList<>();
    private static ArrayList<MethodCallExpr>
            expressions = new ArrayList<>();

    @Override
    public void visit(
            final MethodCallExpr n,
            final JavaParserFacade javaParserFacade) {
        super.visit(n, javaParserFacade);
        ResolvedMethodDeclaration resolvedN = n.resolve();
        resolvedMethodDeclarations.add(resolvedN);
        expressions.add(n);
    }

    public static void main() {
        for (int i = 0; i < resolvedMethodDeclarations.size(); i++) {
            if (resolvedMethodDeclarations.get(i).getName().equals("nextInt")
                    && resolvedMethodDeclarations.get(i).getPackageName().
                    equals("java.util") && resolvedMethodDeclarations.get(i).
                    getClassName().equals("Scanner")) {
                expressions.get(i).setName(new SimpleName("nextBigInteger"));
            }
        }
    }
}
