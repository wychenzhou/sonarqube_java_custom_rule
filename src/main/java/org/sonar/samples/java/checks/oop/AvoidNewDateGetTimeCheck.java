package org.sonar.samples.java.checks.oop;

import java.util.Deque;
import java.util.LinkedList;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.samples.java.checks.PrinterVisitor;

/**
 * 获取当前毫秒数：System.currentTimeMillis(); 而不是new Date().getTime()
 * 
 * @author chenzhou
 *
 */
@Rule(key = "AvoidNewDateGetTimeCheck")
public class AvoidNewDateGetTimeCheck extends BaseTreeVisitor implements JavaFileScanner {

	private JavaFileScannerContext context;
	private static final String ISSUE_MSG = "获取当前毫秒数：System.currentTimeMillis(); 而不是new Date().getTime()";
	private final Deque<Symbol.TypeSymbol> enclosingClass = new LinkedList<>();

	@Override
	public void scanFile(JavaFileScannerContext context) {
		this.context = context;
		scan(context.getTree());
	}

	@Override
	public void visitClass(ClassTree tree) {
		Symbol.TypeSymbol enclosingSymbol = tree.symbol();
		enclosingClass.push(enclosingSymbol);
		super.visitClass(tree);
		enclosingClass.pop();
	}

	@Override
	public void visitMethodInvocation(MethodInvocationTree tree) {
		super.visitMethodInvocation(tree);
		if (tree.methodSelect().is(Tree.Kind.MEMBER_SELECT)) {
			MemberSelectExpressionTree memberSelectExpressionTree = (MemberSelectExpressionTree) tree.methodSelect();
			IdentifierTree identifierTree = memberSelectExpressionTree.identifier();
			if (!enclosingClassExtendsDate() && "getTime".equals(identifierTree.name())
					&& calledOnTypeInheritedFromDate(memberSelectExpressionTree.expression())
					&& tree.arguments().isEmpty()) {
				context.reportIssue(this, identifierTree, ISSUE_MSG);
			}
		}
	}

	private boolean enclosingClassExtendsDate() {
		return enclosingClass.peek() != null && enclosingClass.peek().type().isSubtypeOf("java.util.Date");
	}

	private static boolean calledOnTypeInheritedFromDate(ExpressionTree tree) {
		return tree.symbolType().isSubtypeOf("java.util.Date");
	}

}
