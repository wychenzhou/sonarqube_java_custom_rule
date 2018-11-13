package org.sonar.samples.java.checks.concurrent;

import java.util.ArrayList;
import java.util.List;

import org.sonar.check.Rule;
import org.sonar.java.matcher.MethodMatcher;
import org.sonar.java.matcher.NameCriteria;
import org.sonar.java.matcher.TypeCriteria;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.samples.java.checks.util.AbstractMethodDetectionImpro;

import com.google.common.collect.ImmutableList;

/**
 * 线程池不允许使用Executors去创建,而是通过ThreadPoolExecutor的方式
 * 
 * @author chenzhou
 *
 */
@Rule(key = "AvoidExecutorsCreatePoolCheck")
public class AvoidExecutorsCreatePoolCheck extends AbstractMethodDetectionImpro {
	private static final String ISSUE_MSG = "线程池不允许使用Executors去创建,而是通过ThreadPoolExecutor的方式";

	@Override
	protected List<MethodMatcher> getMethodInvocationMatchers() {
		return ImmutableList.of(
				MethodMatcher.create().typeDefinition("java.util.concurrent.Executors")
						.name(NameCriteria.startsWith("newFixedThreadPool")).addParameter("int"),
				MethodMatcher.create().typeDefinition("java.util.concurrent.Executors")
						.name(NameCriteria.startsWith("newWorkStealingPool")).addParameter("int"),
				MethodMatcher.create().typeDefinition("java.util.concurrent.Executors")
						.name(NameCriteria.startsWith("newWorkStealingPool")).withoutParameter(),
				MethodMatcher.create().typeDefinition("java.util.concurrent.Executors")
						.name(NameCriteria.startsWith("newFixedThreadPool")).addParameter("int")
						.addParameter(TypeCriteria.anyType()),
				MethodMatcher.create().typeDefinition("java.util.concurrent.Executors")
						.name(NameCriteria.startsWith("newCachedThreadPool")).withoutParameter(),
				MethodMatcher.create().typeDefinition("java.util.concurrent.Executors")
						.name(NameCriteria.startsWith("newCachedThreadPool")).addParameter(TypeCriteria.anyType()),
				MethodMatcher.create().typeDefinition("java.util.concurrent.Executors")
						.name(NameCriteria.startsWith("newScheduledThreadPool")).addParameter("int"),
				MethodMatcher.create().typeDefinition("java.util.concurrent.Executors")
						.name(NameCriteria.startsWith("newScheduledThreadPool")).addParameter("int")
						.addParameter(TypeCriteria.anyType()));
	}

	@Override
	protected void onMethodInvocationFound(MethodInvocationTree mit) {
		IdentifierTree identifierTree = methodName(mit);
		if (isExecutorsMethod(identifierTree) && isBeanUtilsOwnerType(mit)) {
			context.reportIssue(this, identifierTree, ISSUE_MSG);
		}
		super.onMethodInvocationFound(mit);
	}

	/**
	 * 获取IdentifierTree
	 * 
	 * @param mit
	 * @return
	 */
	private static IdentifierTree methodName(MethodInvocationTree mit) {
		ExpressionTree methodSelect = mit.methodSelect();
		IdentifierTree id;
		if (methodSelect.is(Tree.Kind.IDENTIFIER)) {
			id = (IdentifierTree) methodSelect;
		} else {
			id = ((MemberSelectExpressionTree) methodSelect).identifier();
		}
		return id;
	}

	/**
	 * 判断方法是否属于java.util.concurrent.Executors
	 * 
	 * @param mit
	 * @return
	 */
	private static boolean isBeanUtilsOwnerType(MethodInvocationTree mit) {
		return mit.symbol().owner().type().isSubtypeOf("java.util.concurrent.Executors");
	}
	
	/**
	 * 判断方法是否属于其中
	 * 
	 * @param identifierTree
	 * @return
	 */
	private boolean isExecutorsMethod(IdentifierTree identifierTree) {
		List<String> methodList = new ArrayList<String>();
		methodList.add("newFixedThreadPool");
		methodList.add("newWorkStealingPool");
		methodList.add("newFixedThreadPool");
		methodList.add("newFixedThreadPool");
		methodList.add("newCachedThreadPool");
		methodList.add("newScheduledThreadPool");
		if (methodList.contains(identifierTree.name())) {
			return true;
		}
		return false;
	}
}
