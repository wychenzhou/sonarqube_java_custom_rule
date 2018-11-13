package org.sonar.samples.java.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;
import org.sonar.samples.java.checks.naming.LowerCameCaseVariableNameCheck;

public class LowerCameCaseVariableNameCheckTest {
	@Test
	public void test() {
		JavaCheckVerifier.verify(
				"src/test/files/lowerCameCase/LowerCameCaseMapper.java",
				new LowerCameCaseVariableNameCheck());

	}
}
