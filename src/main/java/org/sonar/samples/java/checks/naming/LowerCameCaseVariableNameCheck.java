package org.sonar.samples.java.checks.naming;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.StatementTree;
import org.sonar.plugins.java.api.tree.VariableTree;

/**
 * 
 * 方法名、参数名、成员变量、局部变量都统一使用lowerCamelCase，必须遵从驼峰形式
 * 
 * @author chenzhou
 * 
 */
@Rule(key = "LowerCameCaseVariableNameCheck")
public class LowerCameCaseVariableNameCheck extends BaseTreeVisitor implements JavaFileScanner {
	private static final Logger LOGGER = LoggerFactory.getLogger(LowerCameCaseVariableNameCheck.class);

	private JavaFileScannerContext context;
	private static final String ISSUE_MSG = "方法名、参数名、成员变量、局部变量都统一使用lowerCamelCase，必须遵从驼峰形式";
	private static final Pattern PATTERN = Pattern.compile("^[a-z|$][a-z0-9]*([A-Z][a-z0-9]*)*(DO|DTO|VO|DAO)?$");

	public void scanFile(JavaFileScannerContext context) {
		this.context = context;
		scan(context.getTree());
	}

	/**
	 * 方法,方法内部变量驼峰检查、构造函数/static/final 变量除外
	 */
	@Override
	public void visitMethod(MethodTree tree) {
		// 判断是否为构造函数，构造函数返回类型为null
		if (tree.symbol().returnType() != null) {
			String methodName = tree.simpleName().name();
			// 方法名格式不符合规则
			if (!PATTERN.matcher(methodName).matches()) {
				LOGGER.info("方法名:" + methodName + "不符合规范");
				context.reportIssue(this, tree, ISSUE_MSG);
			}
		}
		super.visitMethod(tree);
	}

	/**
	 * 判断变量命名是否符合规范(方法参数变量和成员变量)
	 */
	@Override
	public void visitVariable(VariableTree tree) {
		// static\finall命名除外
		boolean isNotCheck = tree.symbol().isFinal() || tree.symbol().isStatic();
		if (!isNotCheck) {
			String variableName = tree.simpleName().name();
			if (!PATTERN.matcher(variableName).matches()) {
				LOGGER.info("变量名:" + variableName + "不符合规范");
				context.reportIssue(this, tree, ISSUE_MSG);
			}
		}
		super.visitVariable(tree);
	}
}
