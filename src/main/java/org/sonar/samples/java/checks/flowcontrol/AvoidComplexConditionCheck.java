package org.sonar.samples.java.checks.flowcontrol;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import com.google.common.collect.ImmutableList;

/**
 * 不要在条件判断中执行复杂的语句
 * 
 * @author chenzhou
 *
 */
@Rule(key = "AvoidComplexConditionCheck")
public class AvoidComplexConditionCheck extends IssuableSubscriptionVisitor {
	private static final String ISSUE_MSG = "不要在条件判断中执行复杂的语句，将复杂逻辑判断的结果赋值给一个有意义的布尔变量，以提高可读性";
	private static final int DEFAULT_MAX = 3;

	private final Deque<Integer> count = new LinkedList<>();
	private final Deque<Integer> level = new LinkedList<>();

	@Override
	public void scanFile(JavaFileScannerContext context) {
		count.clear();
		level.clear();
		level.push(0);
		count.push(0);
		super.scanFile(context);
	}

	@Override
	public List<Kind> nodesToVisit() {
		// TODO Auto-generated method stub
		return ImmutableList.<Tree.Kind>builder().add(Tree.Kind.CONDITIONAL_AND).add(Tree.Kind.CONDITIONAL_OR)
				.add(Tree.Kind.CONDITIONAL_EXPRESSION).build();
	}

	@Override
	public void visitNode(Tree tree) {
		if (tree.is(Tree.Kind.CONDITIONAL_OR) || tree.is(Tree.Kind.CONDITIONAL_AND)
				|| tree.is(Tree.Kind.CONDITIONAL_EXPRESSION)) {
			count.push(count.pop() + 1);
		}
		level.push(level.pop() + 1);
	}

	@Override
	public void leaveNode(Tree tree) {
		int currentLevel = level.peek();
		if (currentLevel == 1) {
			int opCount = count.pop();
			if (opCount > DEFAULT_MAX) {
				context.reportIssue(this, tree, ISSUE_MSG);
			}
			count.push(0);
		}
		level.push(level.pop() - 1);
	}
}
