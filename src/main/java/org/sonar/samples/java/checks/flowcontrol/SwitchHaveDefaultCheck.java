package org.sonar.samples.java.checks.flowcontrol;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.sonar.check.Rule;
import org.sonar.java.ast.api.JavaKeyword;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.tree.CaseLabelTree;
import org.sonar.plugins.java.api.tree.SwitchStatementTree;
import org.sonar.plugins.java.api.tree.Tree;

@Rule(key = "SwitchHaveDefaultCheck")
public class SwitchHaveDefaultCheck extends IssuableSubscriptionVisitor {

	@Override
	public List<Tree.Kind> nodesToVisit() {
		return Collections.singletonList(Tree.Kind.SWITCH_STATEMENT);
	}

	@Override
	public void visitNode(Tree tree) {
		if (!hasSemantic()) {
			return;
		}
		SwitchStatementTree switchStatementTree = (SwitchStatementTree) tree;
		// 没有匹配的default元素
		if (getDefaultLabel(switchStatementTree)) {
			if (!isSwitchOnEnum(switchStatementTree)) {
				reportIssue(switchStatementTree.switchKeyword(), "在此switch块中添加default");
			} else if (missingCasesOfEnum(switchStatementTree)) {
				reportIssue(switchStatementTree.switchKeyword(), "通过添加枚举常量或向此switch块添加default来完善此switch");
			}
		}
	}

	private static boolean getDefaultLabel(SwitchStatementTree switchStatementTree) {
		// 检查是否没有匹配所有元素,没有匹配返回true
		return allLabels(switchStatementTree).noneMatch(SwitchHaveDefaultCheck::isDefault);
	}

	private static boolean isDefault(CaseLabelTree caseLabelTree) {
		return JavaKeyword.DEFAULT.getValue().equals(caseLabelTree.caseOrDefaultKeyword().text());
	}

	private static boolean isSwitchOnEnum(SwitchStatementTree switchStatementTree) {
		return switchStatementTree.expression().symbolType().symbol().isEnum();
	}

	private static boolean missingCasesOfEnum(SwitchStatementTree switchStatementTree) {
		return numberConstants(switchStatementTree) > allLabels(switchStatementTree).count();
	}

	/**
	 * 获取switch块中所有case的流
	 * 
	 * @param switchStatementTree
	 * @return
	 */
	private static Stream<CaseLabelTree> allLabels(SwitchStatementTree switchStatementTree) {
		return switchStatementTree.cases().stream().flatMap(caseGroup -> caseGroup.labels().stream());
	}
	
	private static long numberConstants(SwitchStatementTree switchStatementTree) {
		return switchStatementTree.expression().symbolType().symbol().memberSymbols().stream()
				.filter(Symbol::isVariableSymbol).filter(Symbol::isEnum).count();
	}

}
