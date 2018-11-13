package org.sonar.samples.java.checks.comment;

import java.util.regex.Pattern;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.SyntaxTrivia;
import org.sonar.samples.java.checks.PrinterVisitor;

@Rule(key = "AbsAndInterMethodMustDocCheck")
public class AbsAndInterMethodMustDocCheck extends BaseTreeVisitor implements JavaFileScanner {

	private JavaFileScannerContext context;
	private static final String ISSUE_MSG = "所有的抽象方法必须要用javadoc注释、除了返回值、参数、异常说明外，还必须指出该方法实现什么功能";
	private static final String ISSUE_MSG_2 = "请正确使用javadoc注释";
	private static final Pattern EMPTY_CONTENT_PATTERN = Pattern.compile("[/*\\n\\r\\s]+(@.*)?", Pattern.DOTALL);
	private static final Pattern PARAM_CONTENT_PATTERN = Pattern.compile(".*@param\\s+.*", Pattern.DOTALL);
	private static final Pattern RETURN_CONTENT_PATTERN = Pattern.compile(".*@return\\s+.*", Pattern.DOTALL);
	private static final Pattern THROWS_CONTENT_PATTERN = Pattern.compile(".*@throws\\s+.*", Pattern.DOTALL);

	@Override
	public void scanFile(JavaFileScannerContext context) {
		this.context = context;
		scan(context.getTree());
	}

	@Override
	public void visitMethod(MethodTree tree) {
		if (!tree.symbol().isAbstract()) {
			super.visitMethod(tree);
		}
		String javaDoc = getMethodJavaDoc(tree).trim();
		int paramCount = tree.parameters().size();
		// 有抛出异常
		boolean isThrows = !tree.symbol().thrownTypes().isEmpty();
		// 有返回值
		boolean isReturn = (!"void".equals(tree.symbol().returnType().name()));

		if (javaDoc != null) {
			NoMethodDesc(tree, javaDoc);
			NoMethodParam(tree, paramCount, javaDoc);
			NoMethodThrows(tree, isThrows, javaDoc);
			NoMethodReturn(tree, isReturn, javaDoc);
		}
		super.visitMethod(tree);
	}

	private String getMethodJavaDoc(MethodTree tree) {
		String javadoc = null;
		if (!tree.modifiers().firstToken().trivias().isEmpty()) {
			for (SyntaxTrivia trivia : tree.modifiers().firstToken().trivias()) {
				javadoc = trivia.comment();
				return javadoc;
			}
		}
		return javadoc;
	}

	private boolean isNoMethodDesp(String javaDoc) {
		// 返回true代表没有方法描述
		return EMPTY_CONTENT_PATTERN.matcher(javaDoc).matches();
	}

	private void NoMethodDesc(MethodTree tree, String javaDoc) {
		// System.out.println("方法:" + tree.simpleName() + "是否有方法描述？" +
		// !isNoMethodDesp(javaDoc));
		if (isNoMethodDesp(javaDoc)) {
			context.reportIssue(this, tree, ISSUE_MSG);
		}
	}

	private void NoMethodParam(MethodTree tree, int paramCount, String javaDoc) {
		// System.out.println("方法:" + tree.simpleName() + "，参数有" + paramCount +
		// "个，是否有@param?"+ PARAM_CONTENT_PATTERN.matcher(javaDoc).matches());
		if (paramCount != 0 && !PARAM_CONTENT_PATTERN.matcher(javaDoc).matches()) {
			context.reportIssue(this, tree, ISSUE_MSG);
		}
		if (paramCount == 0 && PARAM_CONTENT_PATTERN.matcher(javaDoc).matches()) {
			context.reportIssue(this, tree, ISSUE_MSG_2);
		}
	}

	private void NoMethodThrows(MethodTree tree, boolean isThrows, String javaDoc) {
		// System.out.println("方法:" + tree.simpleName() + "有抛出异常?" + isThrows +
		// "，是否有@throws?"+ THROWS_CONTENT_PATTERN.matcher(javaDoc).matches());
		if (isThrows && !THROWS_CONTENT_PATTERN.matcher(javaDoc).matches()) {
			context.reportIssue(this, tree, ISSUE_MSG);
		}

		if (!isThrows && THROWS_CONTENT_PATTERN.matcher(javaDoc).matches()) {
			context.reportIssue(this, tree, ISSUE_MSG_2);

		}
	}

	private void NoMethodReturn(MethodTree tree, boolean isReturn, String javaDoc) {
		// System.out.println("方法:" + tree.simpleName() + "有无return返回值?" + isReturn +
		// "，是否有@return?"+ RETURN_CONTENT_PATTERN.matcher(javaDoc).matches());
		if (isReturn && !RETURN_CONTENT_PATTERN.matcher(javaDoc).matches()) {
			context.reportIssue(this, tree, ISSUE_MSG);
		}
		if (!isReturn && RETURN_CONTENT_PATTERN.matcher(javaDoc).matches()) {
			context.reportIssue(this, tree, ISSUE_MSG_2);
		}
	}
}
