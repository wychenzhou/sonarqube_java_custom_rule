package org.sonar.samples.java.checks.set;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.NewClassTree;

/**
 * 集合初始化时，指定集合初始值大小
 * 
 * @author chenzhou
 *
 */
@Rule(key = "CollectionInitShouldAssignCapacityCheck")
public class CollectionInitShouldAssignCapacityCheck extends BaseTreeVisitor implements JavaFileScanner {
	private static final String ISSUE_MSG = "集合初始化时，指定集合初始值大小";
	private JavaFileScannerContext context;

	@Override
	public void scanFile(JavaFileScannerContext context) {
		this.context = context;
		scan(context.getTree());
	}

	@Override
	public void visitNewClass(NewClassTree tree) {
		if (isCollectionOrMap(tree) && tree.arguments().size() == 0) {
			context.reportIssue(this, tree, ISSUE_MSG);
		}
		super.visitNewClass(tree);
	}

	/**
	 * 类型属于Collection Or Map?
	 * 
	 * @param tree
	 * @return
	 */
	private boolean isCollectionOrMap(NewClassTree tree) {
		return (tree.symbolType().isSubtypeOf("java.util.Collection")
				|| tree.symbolType().isSubtypeOf("java.util.Map"));
	}
}
