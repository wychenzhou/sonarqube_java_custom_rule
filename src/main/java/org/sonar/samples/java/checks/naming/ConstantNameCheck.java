package org.sonar.samples.java.checks.naming;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ExpressionStatementTree;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.VariableTree;
import org.sonar.samples.java.checks.PrinterVisitor;

/**
 * 常量命名应该全部大写，单词间用下划线隔开，力求语义表达完整清楚
 * 
 * @author chenzhou
 *
 */
@Rule(key = "ConstantNameCheck")
public class ConstantNameCheck extends BaseTreeVisitor implements JavaFileScanner {
	private static final String ISSUE_MSG = "常量命名应该全部大写，单词间用下划线隔开，力求语义表达完整清楚";
	private JavaFileScannerContext context;

	@Override
	public void scanFile(JavaFileScannerContext context) {
		this.context = context;
		scan(context.getTree());
		System.out.println(PrinterVisitor.print(context.getTree()));
	}

	/**
	 * 1.检查是否有常量 2.检查常量的命名是否符合规范(变量名称是否有static final)
	 * 
	 */
	@Override
	public void visitVariable(VariableTree tree) {
		String varibaleName = tree.simpleName().name();
		String varibaleNameUpperCase = varibaleName.toUpperCase();
		if (tree.symbol().isFinal()) {
			if (varibaleName.equals(varibaleNameUpperCase)) {
				if (varibaleName.length() > 15 && !varibaleName.contains("_")) {
					context.reportIssue(this, tree, ISSUE_MSG);
				}
			} else {
				context.reportIssue(this, tree, ISSUE_MSG);
			}
		}
		super.visitVariable(tree);
	}
}