package org.sonar.samples.java.checks.constant;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.LiteralTree;
import org.sonar.samples.java.checks.PrinterVisitor;

/**
 * long 或者 Long 初始赋值时，使用大写的 L ，不能是小写的 l ，小写容易跟数字 1 混淆，造成误解
 * 
 * @author chenzhou
 */
@Rule(key = "UpperEllRuleCheck")
public class UpperEllRuleCheck extends BaseTreeVisitor implements JavaFileScanner {

	private JavaFileScannerContext context;
	private static final String UPPERELL_L = "l";
	private static final String ISSUE_MSG = "long 或者 Long 初始赋值时，使用大写的 L,不能是小写的 l,小写容易跟数字 1 混淆，造成误解";

	@Override
	public void scanFile(JavaFileScannerContext context) {
		this.context = context;
		scan(context.getTree());
	}

	@Override
	public void visitLiteral(LiteralTree tree) {
		if (tree.value() != null && tree.value().endsWith(UPPERELL_L)) {
			context.reportIssue(this, tree, ISSUE_MSG);
		}
		super.visitLiteral(tree);
	}
}
