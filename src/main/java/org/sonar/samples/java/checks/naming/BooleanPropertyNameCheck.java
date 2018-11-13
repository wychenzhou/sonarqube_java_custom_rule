package org.sonar.samples.java.checks.naming;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.VariableTree;

/**
 *
 * POJO 类中布尔类型的变量，都不要加 is 前缀
 * 
 * @author chenzhou
 *
 */
@Rule(key = "BooleanPropertyNameCheck")
public class BooleanPropertyNameCheck extends BaseTreeVisitor implements JavaFileScanner {

	private JavaFileScannerContext context;
	private static final String ISSUE_MSG = "POJO 类中布尔类型的变量，都不要加 is 前缀";
	private static final String BOOLEAN = "boolean";

	@Override
	public void scanFile(JavaFileScannerContext context) {
		this.context = context;
		scan(context.getTree());
	}

	@Override
	public void visitVariable(VariableTree tree) {
		String variableName = tree.simpleName().name();
		boolean equalsBoolean = BOOLEAN.equalsIgnoreCase(tree.symbol().type().name());
		if (equalsBoolean && variableName.startsWith("is")) {
			context.reportIssue(this, tree, ISSUE_MSG);
		}
		super.visitVariable(tree);
	}
}
