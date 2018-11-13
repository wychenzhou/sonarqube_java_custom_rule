package org.sonar.samples.java.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;
import org.sonar.samples.java.checks.concurrent.CreateThreadShouldPoolCheck;

public class CreateThreadShouldPoolCheckTest {
	
	
	@Test
	public void test() {
		JavaCheckVerifier.verify("src/test/files/test/CreateThreadDemo.java", new CreateThreadShouldPoolCheck());
	}
}
