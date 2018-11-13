package org.sonar.samples.java.checks.exception;

import java.util.Arrays;
import java.util.List;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.BlockTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.ReturnStatementTree;
import org.sonar.plugins.java.api.tree.StatementTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.samples.java.checks.PrinterVisitor;

/**
 * 返回类型为基本数据类型，return包装数据类型的对象时，自动拆箱有可能产生NPE
 * 
 * @author chenzhou
 * 
 */
@Rule(key = "MethodReturnWrapperCheck")
public class MethodReturnWrapperCheck extends BaseTreeVisitor implements JavaFileScanner {
	private static final String ISSUE_MSG = "返回类型为基本数据类型，return包装数据类型的对象时，自动拆箱有可能产生NPE";
	private JavaFileScannerContext context;

	@Override
	public void scanFile(JavaFileScannerContext context) {
		this.context = context;
		scan(context.getTree());
	}

	@Override
	public void visitMethod(MethodTree tree) {
		if (!tree.symbol().isAbstract()) {
			String methodReturnType = tree.returnType().symbolType().name();
			boolean isMethodReturn = compareReturn(methodReturnType, getReturnType(tree));
			if (methodReturnType != null && !isMethodReturn) {
				context.reportIssue(this, tree, ISSUE_MSG);
			}
		}
		super.visitMethod(tree);
	}

	/*
	 * 得到return的数据类型
	 */
	private String getReturnType(MethodTree tree) {
		BlockTree blockTree = tree.block();
		for (StatementTree st : blockTree.body()) {
			if (st.is(Tree.Kind.RETURN_STATEMENT)) {
				ReturnStatementTree rts = (ReturnStatementTree) st;
				return rts.expression().symbolType().name();
			}
		}
		return null;
	}

	/*
	 * 判断方法返回与return是否一致
	 */
	private boolean compareReturn(String methodReturnType, String returnType) {
		String[] wrapperArray = { "Byte", "Boolean", "Short", "Character", "Integer", "Long", "Float", "Double" };
		String[] baseArray = { "byte", "short", "int", "long", "float", "double", "boolean", "char" };
		List<String> wrapperList = Arrays.asList(wrapperArray);
		List<String> baseList = Arrays.asList(baseArray);
		if (baseList.contains(methodReturnType) && wrapperList.contains(returnType)) {
			return false;
		}
		return true;
	}
}
