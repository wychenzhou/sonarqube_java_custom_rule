package org.sonar.samples.java.checks.oop;

import java.util.List;

import javax.swing.plaf.synth.SynthSpinnerUI;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.IdentifierTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.VariableTree;
import org.sonar.samples.java.checks.PrinterVisitor;

import com.google.common.collect.ImmutableList;

/**
 * 不能使用过时的类或方法
 * 
 * @author chenzhou
 *
 */
@Rule(key = "AviodUseDeprecatedCheck")
public class AviodUseDeprecatedCheck extends IssuableSubscriptionVisitor {

	private static final String ISSUE_MSG = "不能使用过时的类或方法";
	private int nestedDeprecationLevel = 0;

	@Override
	public void scanFile(JavaFileScannerContext context) {
		if (context.getSemanticModel() == null) {
			return;
		}
		super.scanFile(context);
		nestedDeprecationLevel = 0;
	}

	@Override
	public List<Tree.Kind> nodesToVisit() {
		return ImmutableList.of(Tree.Kind.IDENTIFIER, Tree.Kind.CLASS, Tree.Kind.ENUM, Tree.Kind.INTERFACE,
				Tree.Kind.ANNOTATION_TYPE, Tree.Kind.METHOD, Tree.Kind.CONSTRUCTOR);
	}

	@Override
	public void visitNode(Tree tree) {
		if (nestedDeprecationLevel == 0) {
			if (tree.is(Tree.Kind.IDENTIFIER)) {
				checkIdentifierIssue((IdentifierTree) tree);
			} else if (tree.is(Tree.Kind.METHOD, Tree.Kind.CONSTRUCTOR)) {
				checkMethodIssue((MethodTree) tree);
			}
		}
		if (isDeprecatedMethod(tree) || isDeprecatedClassTree(tree)) {
			nestedDeprecationLevel++;
		}
	}

	@Override
	public void leaveNode(Tree tree) {
		if (isDeprecatedMethod(tree) || isDeprecatedClassTree(tree)) {
			nestedDeprecationLevel--;
		}
	}

	private void checkIdentifierIssue(IdentifierTree identifierTree) {
		if (isSimpleNameOfVariableTreeOrVariableIsDeprecated(identifierTree)) {
			return;
		}
		Symbol symbol = identifierTree.symbol();
		if (isDeprecated(symbol)) {
			String name;
			if (isConstructor(symbol)) {
				name = symbol.owner().name();
			} else {
				name = symbol.name();
			}
			reportIssue(identifierTree, "不能使用过时的类或方法: \"" + name + "\";");
		}
	}

	private static boolean isSimpleNameOfVariableTreeOrVariableIsDeprecated(IdentifierTree identifierTree) {
		Tree parent = identifierTree.parent();
		return parent.is(Tree.Kind.VARIABLE) && (identifierTree.equals(((VariableTree) parent).simpleName())
				|| ((VariableTree) parent).symbol().isDeprecated());
	}

	private void checkMethodIssue(MethodTree methodTree) {
		if (!methodTree.symbol().isDeprecated() && isOverridingDeprecatedConcreteMethod(methodTree.symbol())) {
			reportIssue(methodTree.simpleName(), ISSUE_MSG);
		}
	}

	private static boolean isDeprecated(Symbol symbol) {
		return symbol.isDeprecated() || (isConstructor(symbol) && symbol.owner().isDeprecated())
				|| isDeprecatedEnumConstant(symbol);
	}

	private static boolean isDeprecatedEnumConstant(Symbol symbol) {
		return symbol.isVariableSymbol() && symbol.isEnum() && symbol.type().symbol().isDeprecated();
	}

	private static boolean isConstructor(Symbol symbol) {
		return symbol.isMethodSymbol() && "<init>".equals(symbol.name());
	}

	private static boolean isOverridingDeprecatedConcreteMethod(Symbol.MethodSymbol symbol) {
		Symbol.MethodSymbol overriddenMethod = symbol.overriddenSymbol();
		while (overriddenMethod != null && !overriddenMethod.isUnknown()) {
			if (overriddenMethod.isAbstract()) {
				return false;
			}
			if (overriddenMethod.isDeprecated()) {
				return true;
			}
			overriddenMethod = overriddenMethod.overriddenSymbol();
		}
		return false;
	}

	private static boolean isDeprecatedMethod(Tree tree) {
		return tree.is(Tree.Kind.METHOD, Tree.Kind.CONSTRUCTOR) && ((MethodTree) tree).symbol().isDeprecated();
	}

	private static boolean isDeprecatedClassTree(Tree tree) {
		return tree.is(Tree.Kind.CLASS, Tree.Kind.ENUM, Tree.Kind.INTERFACE, Tree.Kind.ANNOTATION_TYPE)
				&& ((ClassTree) tree).symbol().isDeprecated();
	}
}
