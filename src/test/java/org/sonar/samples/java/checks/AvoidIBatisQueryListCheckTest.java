package org.sonar.samples.java.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;
import org.sonar.samples.java.checks.other.AvoidIBatisQueryListCheck;

public class AvoidIBatisQueryListCheckTest {
	@Test
	public void test() {
		JavaCheckVerifier.verify("src/test/files/beanutil/QueryDemo.java", new AvoidIBatisQueryListCheck());
	}
}
