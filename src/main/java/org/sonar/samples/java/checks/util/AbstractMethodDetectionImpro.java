package org.sonar.samples.java.checks.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.java.matcher.MethodMatcher;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.NewClassTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.samples.java.checks.other.AvoidIBatisQueryListCheck;

import com.google.common.collect.ImmutableList;
/**
 * 
 * @author chenzhou
 *
 */
public abstract class AbstractMethodDetectionImpro extends IssuableSubscriptionVisitor {

	private List<MethodMatcher> matchers;
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMethodDetectionImpro.class);

	@Override
	public List<Tree.Kind> nodesToVisit() {
		return ImmutableList.of(Tree.Kind.METHOD_INVOCATION, Tree.Kind.NEW_CLASS);
	}

	@Override
	public void visitNode(Tree tree) {
		if (hasSemantic()) {
			for (MethodMatcher invocationMatcher : matchers()) {
				checkInvocation(tree, invocationMatcher);
			}
		}
	}

	private void checkInvocation(Tree tree, MethodMatcher invocationMatcher) {
		if (tree.is(Tree.Kind.METHOD_INVOCATION)) {
			MethodInvocationTree mit = (MethodInvocationTree) tree;
			if (invocationMatcher.matches(mit)) {
				onMethodInvocationFound(mit);
			}
		} else if (tree.is(Tree.Kind.NEW_CLASS)) {
			NewClassTree newClassTree = (NewClassTree) tree;
			if (invocationMatcher.matches(newClassTree)) {
				onConstructorFound(newClassTree);
			}
		}
	}

	protected abstract List<MethodMatcher> getMethodInvocationMatchers();

	protected void onMethodInvocationFound(MethodInvocationTree mit) {
		// Do nothing by default
	}

	protected void onConstructorFound(NewClassTree newClassTree) {
		// Do nothing by default
	}

	private List<MethodMatcher> matchers() {
		if (matchers == null) {
			matchers = getMethodInvocationMatchers();
		}
		return matchers;
	}
}
