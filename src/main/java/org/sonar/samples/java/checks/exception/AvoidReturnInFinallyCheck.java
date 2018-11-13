package org.sonar.samples.java.checks.exception;

import java.util.List;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.BlockTree;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.IfStatementTree;
import org.sonar.plugins.java.api.tree.StatementTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.TryStatementTree;
import org.sonar.samples.java.checks.PrinterVisitor;

/**
 * 不能在finally块中使用return
 * 
 * @author chenzhou
 *
 */
@Rule(key = "AvoidReturnInFinallyCheck")
public class AvoidReturnInFinallyCheck extends BaseTreeVisitor implements JavaFileScanner {
	private static final String ISSUE_MSG = "不能在finally块中使用return,finally块中的return返回后方法结束执行,不会再执行try块中的return语句";
	private JavaFileScannerContext context;

	@Override
	public void scanFile(JavaFileScannerContext context) {
		this.context = context;
		scan(context.getTree());
	}

	@Override
	public void visitTryStatement(TryStatementTree tree) {
		if (tree.finallyBlock() != null) {
			hasReturnStatement(tree);
		}
		super.visitTryStatement(tree);
	}

	private void hasReturnStatement(TryStatementTree tree) {
		BlockTree finallyBlock = tree.finallyBlock();
		List<StatementTree> state = finallyBlock.body();
		for (StatementTree statementTree : state) {
			if (statementTree.is(Tree.Kind.RETURN_STATEMENT)) {
				context.reportIssue(this, statementTree, ISSUE_MSG);
			}
		}
	}
}
