package org.sonar.samples.java.checks.util;

import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;

public class JenFireRuleUtil {

	public static IdentifierTree methodName(MethodInvocationTree mit) {
		ExpressionTree methodSelect = mit.methodSelect();
		IdentifierTree identifierTree;
		if (methodSelect.is(Tree.Kind.IDENTIFIER)) {
			identifierTree = (IdentifierTree) methodSelect;
		} else {
			identifierTree = ((MemberSelectExpressionTree) methodSelect).identifier();
		}
		return identifierTree;
	}

	public static boolean isAbstractAndInterface(ClassTree tree) {
		return !tree.symbol().isAbstract() && !tree.symbol().isInterface();
	}
}
