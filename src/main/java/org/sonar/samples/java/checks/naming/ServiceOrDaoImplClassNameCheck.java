package org.sonar.samples.java.checks.naming;

import java.util.ArrayList;
import java.util.List;

import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.samples.java.checks.PrinterVisitor;
import org.sonar.samples.java.checks.util.JenFireRuleUtil;

@Rule(key = "ServiceOrDaoImplClassNameCheck")
public class ServiceOrDaoImplClassNameCheck extends BaseTreeVisitor implements JavaFileScanner {
	private JavaFileScannerContext context;
	private static final String ISSUE_MSG = "Service和 DAO类的内部实现类用 Impl的后缀与接口区别";

	@Override
	public void scanFile(JavaFileScannerContext context) {
		this.context = context;
		scan(context.getTree());
		System.out.println(PrinterVisitor.print(context.getTree()));
	}

	@Override
	public void visitClass(ClassTree tree) {
		// 获得类名
		String className = tree.symbol().name();
		if (JenFireRuleUtil.isAbstractAndInterface(tree)) {
			// 获得接口名
			List<String> interfaceNames = interfacesNames(tree);
			if (!isServiceOrDaoImpl(interfaceNames, className)) {
				System.out.println("进来了");
				context.reportIssue(this, tree, ISSUE_MSG);
			}
		}
		super.visitClass(tree);
	}

	private List<String> interfacesNames(ClassTree tree) {
		List<Type> interfaces = tree.symbol().interfaces();
		List<String> interfacesNames = new ArrayList<>();
		if (!interfaces.isEmpty()) {
			for (Type type : interfaces) {
				interfacesNames.add(type.name());
			}
			return interfacesNames;
		}
		return null;
	}

	/**
	 * 判断类名和接口名是否符合规范
	 */
	private boolean isServiceOrDaoImpl(List<String> interfaceNames, String className) {
		for (String intname : interfaceNames) {
			if ((intname.endsWith("Service") || intname.endsWith("Dao")) && className.equals(intname + "Impl")) {
				return true;
			}
		}
		return false;
	}
}
