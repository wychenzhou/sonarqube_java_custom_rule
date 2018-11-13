package org.sonar.samples.java.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;
import org.sonar.samples.java.checks.concurrent.AvoidExecutorsCreatePoolCheck;

public class AvoidExecutorsCreatePoolCheckTest {
	@Test
	public void detected() {
		JavaCheckVerifier.verify("src/test/files/SimpleThreadPool.java", new AvoidExecutorsCreatePoolCheck());
	}
}
