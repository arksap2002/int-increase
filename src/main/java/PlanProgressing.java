import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.declarations.ResolvedMethodDeclaration;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

import java.util.ArrayList;

public class PlanProgressing {

    private ArrayList<ResolvedMethodDeclaration>
            resolvedMethodDeclarations = new ArrayList<>();
    private ArrayList<MethodCallExpr>
            expressions = new ArrayList<>();

    public void doReplace(CompilationUnit compilationUnit,
                          ReflectionTypeSolver reflectionTypeSolver) {
        compilationUnit.accept(new MakingPlanVisitor(),
                JavaParserFacade.get(reflectionTypeSolver));
        for (int i = 0; i < resolvedMethodDeclarations.size(); i++) {
            if (resolvedMethodDeclarations.get(i).getName().equals("nextInt")
                    && resolvedMethodDeclarations.get(i).getPackageName().
                    equals("java.util") && resolvedMethodDeclarations.get(i).
                    getClassName().equals("Scanner")) {
                expressions.get(i).setName(new SimpleName("nextBigInteger"));
            }
        }
    }

    class MakingPlanVisitor
            extends VoidVisitorAdapter<JavaParserFacade> {

        @Override
        public void visit(
                final MethodCallExpr n,
                final JavaParserFacade javaParserFacade) {
            super.visit(n, javaParserFacade);
            ResolvedMethodDeclaration resolvedN = n.resolve();
            resolvedMethodDeclarations.add(resolvedN);
            expressions.add(n);
        }
    }
}