package org.sonar.samples.java.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;
import org.sonar.samples.java.checks.oop.RegexPatternCompileCheck;

public class RegexPatternCompileCheckTest {
	@Test
	public void test() {
		JavaCheckVerifier.verify("src/test/files/regex/RegexPatternsNeedlesslyCheck.java", new RegexPatternCompileCheck());
	}
}
