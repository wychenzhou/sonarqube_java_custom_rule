package org.sonar.samples.java.checks.flowcontrol;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.BlockTree;
import org.sonar.plugins.java.api.tree.ForEachStatement;
import org.sonar.plugins.java.api.tree.ForStatementTree;
import org.sonar.plugins.java.api.tree.IfStatementTree;
import org.sonar.plugins.java.api.tree.StatementTree;
import org.sonar.plugins.java.api.tree.SyntaxToken;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.WhileStatementTree;
import org.sonar.samples.java.checks.PrinterVisitor;

/**
 * 在if/else/for/while/do语句中,代码块应该写在花括号里,否则可能会影响代码执行
 * 
 * @author chenzhou
 *
 */
@Rule(key = "MultilineBlocksCurlyCheck")
public class MultilineBlocksCurlyCheck extends BaseTreeVisitor implements JavaFileScanner {

	private static final String IF_MESSAGE = "在if/else/for/while/do语句中,代码块应该写在花括号里,否则会影响代码执行";
	private JavaFileScannerContext context;

	@Override
	public void scanFile(final JavaFileScannerContext context) {
		this.context = context;
		scan(context.getTree());
	}

	@Override
	public void visitBlock(BlockTree tree) {
		super.visitBlock(tree);
		StatementTree previous = null;
		for (StatementTree current : tree.body()) {
			if (previous != null) {
				check(current, previous);
			}
			previous = current;
		}
	}

	private void check(StatementTree current, StatementTree previous) {
		StatementTree block = null;
		boolean condition = false;
		if (previous.is(Tree.Kind.FOR_EACH_STATEMENT)) {
			block = ((ForEachStatement) previous).statement();
		} else if (previous.is(Tree.Kind.FOR_STATEMENT)) {
			block = ((ForStatementTree) previous).statement();
		} else if (previous.is(Tree.Kind.WHILE_STATEMENT)) {
			block = ((WhileStatementTree) previous).statement();
		} else if (previous.is(Tree.Kind.IF_STATEMENT)) {
			block = getIfStatementLastBlock(previous);
			condition = true;
		}
		if (block != null && !block.is(Tree.Kind.BLOCK)) {
			SyntaxToken previousToken = block.firstToken();
			int previousColumn = previousToken.column();
			int previousLine = previousToken.line();
			SyntaxToken currentToken = current.firstToken();
			int currentColumn = currentToken.column();
			int currentLine = currentToken.line();
			if ((previousColumn == currentColumn && previousLine + 1 == currentLine)
					|| (previousLine == previous.firstToken().line()
							&& previous.firstToken().column() < currentColumn)) {
				context.reportIssue(this, current, IF_MESSAGE);
			}
		}
	}

	private static StatementTree getIfStatementLastBlock(StatementTree statementTree) {
		StatementTree block = statementTree;
		while (block.is(Tree.Kind.IF_STATEMENT)) {
			IfStatementTree ifStatementTree = (IfStatementTree) block;
			StatementTree elseStatement = ifStatementTree.elseStatement();
			block = elseStatement == null ? ifStatementTree.thenStatement() : elseStatement;
		}
		return block;
	}
}
