package org.sonar.samples.java.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;
import org.sonar.samples.java.checks.exception.MethodReturnWrapperCheck;

public class MethodReturnWrapperCheckTest {
	@Test
	public void test() {
		JavaCheckVerifier.verify("src/test/files/exception/MethodReturnWrapperDemo.java",
				new MethodReturnWrapperCheck());
	}
}
