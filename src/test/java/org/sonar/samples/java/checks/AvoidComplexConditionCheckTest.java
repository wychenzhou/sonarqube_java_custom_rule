package org.sonar.samples.java.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;
import org.sonar.samples.java.checks.exception.AvoidReturnInFinallyCheck;
import org.sonar.samples.java.checks.flowcontrol.AvoidComplexConditionCheck;

public class AvoidComplexConditionCheckTest {
	@Test
	public void test() {
		//JavaCheckVerifier.verify("src/test/files/exception/IfStatementDemo.java", new AvoidComplexConditionCheck());
	}
}
