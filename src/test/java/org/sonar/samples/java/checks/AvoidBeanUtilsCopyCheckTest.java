package org.sonar.samples.java.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;
import org.sonar.samples.java.checks.other.AvoidBeanUtilsCopyCheck;

public class AvoidBeanUtilsCopyCheckTest {
	@Test
	public void test() {
		JavaCheckVerifier.verify("src/test/files/beanutil/BeanUtilDemo.java", new AvoidBeanUtilsCopyCheck());
	}
}
