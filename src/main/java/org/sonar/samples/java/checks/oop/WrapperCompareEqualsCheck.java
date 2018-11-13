package org.sonar.samples.java.checks.oop;

import java.util.ArrayList;
import java.util.List;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.BinaryExpressionTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.samples.java.checks.PrinterVisitor;

/**
 * 所有的包装类对象之间值的比较，全部使用equals方法比较
 * 
 * @author chenzhou
 *
 */
@Rule(key = "WrapperCompareEqualsCheck")
public class WrapperCompareEqualsCheck extends BaseTreeVisitor implements JavaFileScanner {
	private static final String ISSUE_MSG = "所有的包装类对象之间值的比较，全部使用equals方法比较";
	private JavaFileScannerContext context;

	@Override
	public void scanFile(JavaFileScannerContext context) {
		this.context = context;
		scan(context.getTree());
	}

	@Override
	public void visitBinaryExpression(BinaryExpressionTree tree) {
		if (tree.is(Tree.Kind.EQUAL_TO)) {
			Type leftOpType = tree.leftOperand().symbolType();
			Type rightOpType = tree.rightOperand().symbolType();
			if (isWrapperAndSameType(leftOpType, rightOpType)) {
				context.reportIssue(this, tree, ISSUE_MSG);
			}
		}
		super.visitBinaryExpression(tree);
	}

	private boolean isWrapperAndSameType(Type leftOpType, Type rightOpType) {
		String leftQualifiedName = leftOpType.fullyQualifiedName();
		String rightQualifiedName = rightOpType.fullyQualifiedName();
		if (leftQualifiedName.equals(rightQualifiedName)) {
			if (isWrapper(leftQualifiedName, rightQualifiedName)) {
				return true;
			}
		}
		return false;
	}

	private boolean isWrapper(String leftQualifiedName, String rightQualifiedName) {
		List<String> wrapperList = new ArrayList<>();
		wrapperList.add("java.lang.Byte");
		wrapperList.add("java.lang.Boolean");
		wrapperList.add("java.lang.Short");
		wrapperList.add("java.lang.Character");
		wrapperList.add("java.lang.Integer");
		wrapperList.add("java.lang.Long");
		wrapperList.add("java.lang.Float");
		wrapperList.add("java.lang.Double");
		if (wrapperList.contains(leftQualifiedName) && wrapperList.contains(rightQualifiedName)) {
			return true;
		}
		return false;
	}
}
