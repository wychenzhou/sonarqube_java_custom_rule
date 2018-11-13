package org.sonar.samples.java.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;
import org.sonar.samples.java.checks.flowcontrol.SwitchCaseEndWithBreakCheck;
import org.sonar.samples.java.checks.other.AvoidIBatisQueryListCheck;

public class SwitchCaseEndWithBreakCheckTest {
	@Test
	public void test() {
		//JavaCheckVerifier.verify("src/test/files/flow/SwitchCaseWithoutBreakCheck.java", new SwitchCaseEndWithBreakCheck());
	}
}
