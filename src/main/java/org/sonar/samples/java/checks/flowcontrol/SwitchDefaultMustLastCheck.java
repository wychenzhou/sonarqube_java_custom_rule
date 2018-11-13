package org.sonar.samples.java.checks.flowcontrol;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.sonar.check.Rule;
import org.sonar.java.ast.api.JavaKeyword;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.CaseLabelTree;
import org.sonar.plugins.java.api.tree.SwitchStatementTree;
import org.sonar.plugins.java.api.tree.Tree;

/**
 * 在一个switch块内，default语句必须放在最后，即使它什么代码也没有
 * 
 * @author chenzhou
 *
 */
@Rule(key = "SwitchDefaultMustLastCheck")
public class SwitchDefaultMustLastCheck extends IssuableSubscriptionVisitor {

	@Override
	public List<Tree.Kind> nodesToVisit() {
		return Collections.singletonList(Tree.Kind.SWITCH_STATEMENT);
	}

	@Override
	public void visitNode(Tree tree) {
		SwitchStatementTree switchStatementTree = (SwitchStatementTree) tree;
		// 如果值存在则使用该值调用 consumer , 否则不做任何事情。
		getDefaultLabelAtWrongPosition(switchStatementTree)
				.ifPresent(defaultLabel -> reportIssue(defaultLabel, "将此default放置与switch块的最后"));
	}

	private static Optional<CaseLabelTree> getDefaultLabelAtWrongPosition(SwitchStatementTree switchStatementTree) {
		for (int i = 0; i < switchStatementTree.cases().size(); i++) {
			List<CaseLabelTree> labels = switchStatementTree.cases().get(i).labels();
			for (int j = 0; j < labels.size(); j++) {
				CaseLabelTree label = labels.get(j);
				boolean defaultExists = isDefault(label);
				if (defaultExists && ((j != labels.size() - 1) || (i == switchStatementTree.cases().size() - 1))) {
					return Optional.empty();
				} else if (defaultExists) {
					return Optional.of(label);
				}
			}
		}
		return Optional.empty();
	}

	private static boolean isDefault(CaseLabelTree caseLabelTree) {
		return JavaKeyword.DEFAULT.getValue().equals(caseLabelTree.caseOrDefaultKeyword().text());
	}
}
