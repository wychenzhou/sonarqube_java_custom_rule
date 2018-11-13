package org.sonar.samples.java.checks.naming;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.CompilationUnitTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.VariableTree;

/**
 * 所有编程相关的命名均不能以下划线或美元符号开始
 * 
 * @author chenzhou
 *
 */
@Rule(key = "UnderlineDollarNameCheck")
public class UnderlineDollarNameCheck extends BaseTreeVisitor implements JavaFileScanner {
	private static final String DOLLAR = "$";
	private static final String UNDERSCORE = "_";
	private static final String ISSUE_MSG = "所有编程相关的命名均不能以下划线或美元符号开始";
	private JavaFileScannerContext context;

	@Override
	public void scanFile(JavaFileScannerContext context) {
		this.context = context;
		scan(context.getTree());
	}

	@Override
	public void visitClass(ClassTree tree) {
		String className = tree.simpleName().name();
		if (className.startsWith(DOLLAR) || className.startsWith(UNDERSCORE)) {
			context.reportIssue(this, tree, ISSUE_MSG);
		}
		super.visitClass(tree);
	}

	@Override
	public void visitMethod(MethodTree tree) {
		String methodName = tree.simpleName().name();
		if (methodName.startsWith(DOLLAR) || methodName.startsWith(UNDERSCORE)) {
			context.reportIssue(this, tree, ISSUE_MSG);
		}
		super.visitMethod(tree);
	}

	@Override
	public void visitVariable(VariableTree tree) {
		String variableName = tree.simpleName().name();
		if (variableName.startsWith(DOLLAR) || variableName.startsWith(UNDERSCORE)) {
			context.reportIssue(this, tree, ISSUE_MSG);
		}
		super.visitVariable(tree);
	}

}
