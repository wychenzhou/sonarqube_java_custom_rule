package org.sonar.samples.java.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;
import org.sonar.samples.java.checks.naming.TestClassNameCheck;

public class TestClassNameCheckTest {
	@Test
	public void test1() {
		JavaCheckVerifier.verify("src/test/files/naming/Demo.java", new TestClassNameCheck());
	}
}
