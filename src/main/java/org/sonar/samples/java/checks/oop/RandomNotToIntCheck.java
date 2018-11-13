package org.sonar.samples.java.checks.oop;

import java.util.List;

import org.sonar.check.Rule;
import org.sonar.java.matcher.MethodMatcher;
import org.sonar.java.matcher.MethodMatcherCollection;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.NewClassTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.TypeCastTree;

import com.google.common.collect.ImmutableList;

/**
 * 
 * @author chenzhou
 *
 */
@Rule(key = "RandomNotToIntCheck")
public class RandomNotToIntCheck extends IssuableSubscriptionVisitor {

	private final MethodMatcherCollection methodMatchers = MethodMatcherCollection.create(
			MethodMatcher.create().typeDefinition("java.util.Random").name("nextDouble").withoutParameter(),
			MethodMatcher.create().typeDefinition("java.util.Random").name("nextFloat").withoutParameter(),
			MethodMatcher.create().typeDefinition("java.lang.Math").name("random").withoutParameter());

	@Override
	public List<Tree.Kind> nodesToVisit() {
		return ImmutableList.of(Tree.Kind.TYPE_CAST);
	}

	@Override
	public void visitNode(Tree tree) {
		TypeCastTree castTree = (TypeCastTree) tree;
		if (castTree.type().symbolType().is("int")) {
			castTree.expression().accept(new RandomDoubleVisitor());
		}
	}

	private class RandomDoubleVisitor extends BaseTreeVisitor {
		@Override
		public void visitMethodInvocation(MethodInvocationTree tree) {
			if (methodMatchers.anyMatch(tree)) {
				reportIssue(tree.methodSelect(),
						"Math.random()返回是double类型，注意取值的范围[0,1)，如果想获取整数类型的随机数，不要将x放大10的若干倍然后取整，直接使用Random对象的nextInt或者nextLong方法");
			}
			super.visitMethodInvocation(tree);
		}

		@Override
		public void visitNewClass(NewClassTree tree) {
			scan(tree.enclosingExpression());
			scan(tree.identifier());
			scan(tree.typeArguments());
			scan(tree.arguments());
		}
	}
}
