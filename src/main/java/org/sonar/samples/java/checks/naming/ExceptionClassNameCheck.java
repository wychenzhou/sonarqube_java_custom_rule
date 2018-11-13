package org.sonar.samples.java.checks.naming;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ClassTree;

/**
 * 异常类命名使用Exception结尾
 * 
 * @author chenzhou
 *
 */
@Rule(key = "ExceptionClassNameCheck")
public class ExceptionClassNameCheck extends BaseTreeVisitor implements JavaFileScanner {
	private JavaFileScannerContext context;
	private static final String EXCEPTION_END_SUFFIX = "Exception";
	private static final String ISSUE_MSG = "异常类命名使用Exception结尾";

	public void scanFile(JavaFileScannerContext context) {
		this.context = context;
		scan(context.getTree());
	}

	@Override
	public void visitClass(ClassTree tree) {
		String className = tree.simpleName().name();
		String checkName = "Exception";
		if (tree.superClass() != null) {
			String superClassName = tree.superClass().symbolType().name();
			if (checkName.equals(superClassName)) {
				if (!(className != null && className.endsWith(EXCEPTION_END_SUFFIX))) {
					context.reportIssue(this, tree, ISSUE_MSG);
				}
			}
		}
		super.visitClass(tree);
	}
}
