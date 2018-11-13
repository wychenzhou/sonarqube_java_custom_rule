package org.sonar.samples.java.checks.flowcontrol;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import org.sonar.check.Rule;
import org.sonar.java.ast.visitors.SubscriptionVisitor;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.BlockTree;
import org.sonar.plugins.java.api.tree.CaseGroupTree;
import org.sonar.plugins.java.api.tree.CaseLabelTree;
import org.sonar.plugins.java.api.tree.IfStatementTree;
import org.sonar.plugins.java.api.tree.StatementTree;
import org.sonar.plugins.java.api.tree.SwitchStatementTree;
import org.sonar.plugins.java.api.tree.SyntaxTrivia;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.TryStatementTree;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

@Rule(key = "SwitchCaseEndWithBreakCheck")
public class SwitchCaseEndWithBreakCheck extends BaseTreeVisitor implements JavaFileScanner {
	private JavaFileScannerContext context;

	@Override
	public void scanFile(JavaFileScannerContext context) {
		this.context = context;
		scan(context.getTree());
	}

	@Override
	public void visitSwitchStatement(SwitchStatementTree switchStatement) {
		switchStatement.cases().stream()
				// 不考虑没有或只有一个case的情况
				.limit(Math.max(0, switchStatement.cases().size() - 1)).forEach(caseGroup -> {
					// 块中的最后一个元素
					CaseLabelTree caseLabel = caseGroup.labels().get(caseGroup.labels().size() - 1);
					if (Lists.reverse(caseGroup.body()).stream()
							.noneMatch(SwitchCaseEndWithBreakCheck::isUnconditionalExit)
							&& !intentionalFallThrough(switchStatement, caseGroup)) {
						context.reportIssue(this, caseLabel,
								"在一个switch块内，每个case要么通过break/return等来终止,要么通过fall-through注释说明");
					}
				});
		super.visitSwitchStatement(switchStatement);
	}

	private static boolean intentionalFallThrough(SwitchStatementTree switchStatement, CaseGroupTree caseGroup) {
		FallThroughCommentVisitor visitor = new FallThroughCommentVisitor();
		// 当comment是case的最后一个元素时，检查下一个case的第一个标记
		// 将它连接到下一个组。
		CaseGroupTree nextCaseGroup = switchStatement.cases().get(switchStatement.cases().indexOf(caseGroup) + 1);
		List<Tree> treesToScan = ImmutableList.<Tree>builder().addAll(caseGroup.body()).add(nextCaseGroup.firstToken())
				.build();
		visitor.scan(treesToScan);
		return visitor.hasComment;
	}

	private static class FallThroughCommentVisitor extends SubscriptionVisitor {

		private static final Pattern FALL_THROUGH_PATTERN = Pattern.compile("falls?\\-?thro?u[gh]?",
				Pattern.CASE_INSENSITIVE);
		boolean hasComment = false;

		@Override
		public List<Tree.Kind> nodesToVisit() {
			return Collections.singletonList(Tree.Kind.TRIVIA);
		}

		@Override
		public void visitTrivia(SyntaxTrivia syntaxTrivia) {
			if (!hasComment && FALL_THROUGH_PATTERN.matcher(syntaxTrivia.comment()).find()) {
				hasComment = true;
			}
		}

		private void scan(List<Tree> trees) {
			for (Tree tree : trees) {
				if (hasComment) {
					return;
				}
				scanTree(tree);
			}
		}
	}

	private static boolean isUnconditionalExit(Tree syntaxNode) {
		switch (syntaxNode.kind()) {
		case BREAK_STATEMENT:
		case THROW_STATEMENT:
		case RETURN_STATEMENT:
		case CONTINUE_STATEMENT:
			return true;
		case BLOCK:
			return ((BlockTree) syntaxNode).body().stream().anyMatch(SwitchCaseEndWithBreakCheck::isUnconditionalExit);
		case TRY_STATEMENT:
			return isUnconditionalExitInTryCatchStatement((TryStatementTree) syntaxNode);
		case IF_STATEMENT:
			return isUnconditionalExitInIfStatement((IfStatementTree) syntaxNode);
		default:
			return false;
		}
	}

	private static boolean isUnconditionalExitInTryCatchStatement(TryStatementTree tryStatement) {
		return isUnconditionalExit(tryStatement.block())
				&& tryStatement.catches().stream().allMatch(catchTree -> isUnconditionalExit(catchTree.block()));
	}

	private static boolean isUnconditionalExitInIfStatement(IfStatementTree ifStatement) {
		if (!isUnconditionalExit(ifStatement.thenStatement())) {
			return false;
		}

		StatementTree elseStatement = ifStatement.elseStatement();
		if (elseStatement == null) {
			return false;
		}

		return isUnconditionalExit(elseStatement);
	}
}
