package org.sonar.samples.java.checks.oop;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.semantic.Symbol.MethodSymbol;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.MethodTree;

/**
 * 所有的覆写方法，必须加@Override注解
 * 
 * @author chenzhou
 *
 */
@Rule(key = "OverrideAnnotationAddCheck")
public class OverrideAnnotationAddCheck extends BaseTreeVisitor implements JavaFileScanner {

	private JavaFileScannerContext context;
	private static final String ISSUE_MSG = "所有的覆写方法，必须加@Override注解";

	@Override
	public void scanFile(JavaFileScannerContext context) {
		this.context = context;
		scan(context.getTree());
	}

	@Override
	public void visitMethod(MethodTree tree) {
		MethodSymbol overriddenSymbol = tree.symbol().overriddenSymbol();
		if (overriddenSymbol == null || overriddenSymbol.isUnknown()) {
			super.visitMethod(tree);
		} else {
			if (!overriddenSymbol.isAbstract() && !overriddenSymbol.owner().type().is("java.lang.Object")
					&& !tree.symbol().metadata().isAnnotatedWith("java.lang.Override")) {
				context.reportIssue(this, tree, ISSUE_MSG);
			}
		}
		super.visitMethod(tree);
	}
}
