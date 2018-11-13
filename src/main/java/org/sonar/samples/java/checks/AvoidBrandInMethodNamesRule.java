package org.sonar.samples.java.checks;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.MethodTree;

/**
 * 一个简单例子
 * 
 * @author chenzhou
 */
@Rule(key = "AvoidBrandInMethodNames")
public class AvoidBrandInMethodNamesRule extends BaseTreeVisitor implements JavaFileScanner {

	private JavaFileScannerContext context;

	protected static final String COMPANY_NAME = "MyCompany";

	@Override
	public void scanFile(JavaFileScannerContext context) {
		this.context = context;
		scan(context.getTree());
	}

	@Override
	public void visitMethod(MethodTree tree) {
		if (tree.simpleName().name().toUpperCase().contains(COMPANY_NAME.toUpperCase())) {
			context.reportIssue(this, tree, "Avoid using Brand in method name");
		}
		super.visitMethod(tree);
	}
}
