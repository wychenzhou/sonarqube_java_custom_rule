package org.sonar.samples.java;

import java.util.List;
import org.sonar.plugins.java.api.CheckRegistrar;
import org.sonar.plugins.java.api.JavaCheck;
import org.sonarsource.api.sonarlint.SonarLintSide;

/**
 * 提供将在源代码分析期间执行的“检查”（规则的实现）类。
 * 该类是批量扩展，通过实现{@link org.sonar.plugins.java.api.CheckRegistrar}接口。
 * 
 * @author chenzhou
 */
@SonarLintSide
public class MyJavaFileCheckRegistrar implements CheckRegistrar {

	/**
	 * 注册将在分析过程中用于实例化检查的类。
	 */
	@Override
	public void register(RegistrarContext registrarContext) {
		// 调用registerClassesForRepository将这些类与正确的存储库关键字相关联
		registrarContext.registerClassesForRepository(MyJavaRulesDefinition.REPOSITORY_KEY, checkClasses(),
				testCheckClasses());
	}

	/**
	 * 列出插件提供的所有主要检查
	 */
	public static List<Class<? extends JavaCheck>> checkClasses() {
		return RulesList.getJavaChecks();
	}

	/**
	 * 列出插件提供的所有测试检查
	 */
	public static List<Class<? extends JavaCheck>> testCheckClasses() {
		return RulesList.getJavaTestChecks();
	}
}
