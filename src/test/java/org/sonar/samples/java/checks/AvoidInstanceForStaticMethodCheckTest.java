package org.sonar.samples.java.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;
import org.sonar.samples.java.checks.oop.AvoidInstanceForStaticMethodCheck;

public class AvoidInstanceForStaticMethodCheckTest {
	@Test
	public void detected() {
		JavaCheckVerifier.verify("src/test/files/staticdemo/StaticMethodInstanceDemo.java",
				new AvoidInstanceForStaticMethodCheck());
	}
}
