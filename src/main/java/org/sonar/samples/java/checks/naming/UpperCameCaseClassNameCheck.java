package org.sonar.samples.java.checks.naming;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ClassTree;

/**
 * 类名使用UpperCamelCase风格，必须遵从驼峰形式， 但以下情形例外： （领域模型的相关命名）DO / BO / DTO / VO / DAO
 * 
 * @author chenzhou
 * 
 */
@Rule(key = "UpperCameCaseClassNameCheck")
public class UpperCameCaseClassNameCheck extends BaseTreeVisitor implements JavaFileScanner {
	private JavaFileScannerContext context;
	private static final String ISSUE_MSG = "类命名驼峰检查 类名使用UpperCamelCase风格，必须遵从驼峰形式，领域模型的相关命名除外";
	private static final Pattern PATTERN = Pattern
			.compile("^I?([A-Z][a-z0-9]+)+(([A-Z])|(DO|DTO|VO|DAO|BO|DAOImpl|YunOS|AO|PO))?$");

	public void scanFile(JavaFileScannerContext context) {
		this.context = context;
		scan(context.getTree());
	}

	/**
	 * 判断类名是否以驼峰命名
	 */
	@Override
	public void visitClass(ClassTree tree) {
		String className = tree.symbol().name();
		boolean flag = false;
		// 例外情况(领域模型 的相关命名)DO / BO / DTO / VO
		List<String> otherList = new ArrayList<>();
		otherList.add("DO");
		otherList.add("BO");
		otherList.add("DTO");
		otherList.add("VO");
		for (String otherName : otherList) {
			if (otherName.contains(className)) {
				flag = true;
			}
		}
		// 如果类名与以上情况不相同
		if (!flag && !PATTERN.matcher(className).matches()) {
			context.reportIssue(this, tree, ISSUE_MSG);
		}
		super.visitClass(tree);
	}
}
