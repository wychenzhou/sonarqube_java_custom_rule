package org.sonar.samples.java.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;
import org.sonar.samples.java.checks.constant.UpperEllRuleCheck;

public class UpperEllRuleCheckTest {
	
	@Test
	public void test01() {
		JavaCheckVerifier.verify("src/test/files/cons/UpperEllMapper.java", new UpperEllRuleCheck());
	}
}
