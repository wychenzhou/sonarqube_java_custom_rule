package org.sonar.samples.java.checks.concurrent;

import java.util.List;

import org.sonar.check.Rule;
import org.sonar.java.matcher.MethodMatcher;
import org.sonar.java.matcher.NameCriteria;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.samples.java.checks.util.AbstractMethodDetectionImpro;
import org.sonar.samples.java.checks.util.JenFireRuleUtil;
import com.google.common.collect.ImmutableList;

@Rule(key = "CreateThreadShouldPoolCheck")
public class CreateThreadShouldPoolCheck extends AbstractMethodDetectionImpro {

	private static final String ISSUE_MSG = "线程资源必须通过线程池提供，不允许在应用中自行显式创建线程";

	@Override
	protected List<MethodMatcher> getMethodInvocationMatchers() {
		return ImmutableList.of(MethodMatcher.create().typeDefinition("java.lang.Thread").name(NameCriteria.is("start"))
				.withoutParameter());
	}

	@Override
	protected void onMethodInvocationFound(MethodInvocationTree mit) {
		IdentifierTree identifierTree = JenFireRuleUtil.methodName(mit);
		if (isThreadOwnerType(mit) && "start".equals(identifierTree.name())) {
			context.reportIssue(this, mit, ISSUE_MSG);
		}
		super.onMethodInvocationFound(mit);
	}

	private static boolean isThreadOwnerType(MethodInvocationTree mit) {
		return mit.symbol().owner().type().isSubtypeOf("java.lang.Thread");
	}
}
