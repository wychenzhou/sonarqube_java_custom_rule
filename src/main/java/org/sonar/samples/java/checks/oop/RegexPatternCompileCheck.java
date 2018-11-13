package org.sonar.samples.java.checks.oop;

import java.util.List;
import org.sonar.check.Rule;
import org.sonar.java.matcher.MethodMatcher;
import org.sonar.java.model.ExpressionUtils;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.tree.AssignmentExpressionTree;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MemberSelectExpressionTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.VariableTree;
import org.sonar.samples.java.checks.util.AbstractMethodDetectionImpro;
import com.google.common.collect.ImmutableList;

@Rule(key = "RegexPatternCompileCheck")
public class RegexPatternCompileCheck extends AbstractMethodDetectionImpro {

	private static final String STRING = "java.lang.String";
	private static final String PATTERN = "java.util.regex.Pattern";
	private static final String ISSUE_MSG = "使用 \"static final\"重构此代码";

	@Override
	protected List<MethodMatcher> getMethodInvocationMatchers() {
		return ImmutableList.of(MethodMatcher.create().typeDefinition(PATTERN).name("compile").addParameter(STRING));
	}

	@Override
	protected void onMethodInvocationFound(MethodInvocationTree mit) {
		ExpressionTree firstArgument = ExpressionUtils.skipParentheses(mit.arguments().get(0));
		// System.out.println("compile方法参数类型: " + firstArgument.symbolType().name());
		if (!storedInStaticFinal(mit) && (firstArgument.is(Tree.Kind.STRING_LITERAL) || isConstant(firstArgument))) {
			reportIssue(methodName(mit), mit.arguments(), ISSUE_MSG);
		}
	}

	/**
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

	private static boolean storedInStaticFinal(MethodInvocationTree mit) {
		Tree tree = mit.parent();
		// System.out.println("当前树："+mit.symbolType()+"当前的父树:" + tree.kind());
		while (!tree.is(Tree.Kind.VARIABLE, Tree.Kind.CLASS, Tree.Kind.ASSIGNMENT, Tree.Kind.METHOD)) {
			tree = tree.parent();
			// System.out.println("不是VARIABLE|CLASS|ASSIGNMENT|METHOD,当前树父树的父树" +
			// tree.kind());
		}
		if (tree.is(Tree.Kind.CLASS, Tree.Kind.METHOD)) {
			return false;
		} else {

			return isConstant(tree);
		}
	}

	private static boolean isConstant(Tree tree) {
		Symbol symbol = null;
		switch (tree.kind()) {
		case IDENTIFIER:
			symbol = ((IdentifierTree) tree).symbol();
			break;
		case MEMBER_SELECT:
			symbol = (((MemberSelectExpressionTree) tree).identifier()).symbol();
			break;
		case VARIABLE:
			symbol = ((VariableTree) tree).symbol();
			break;
		case ASSIGNMENT:
			return isConstant(((AssignmentExpressionTree) tree).variable());
		default:
			break;
		}
		return symbol != null && symbol.isFinal() && symbol.isStatic();
	}
}