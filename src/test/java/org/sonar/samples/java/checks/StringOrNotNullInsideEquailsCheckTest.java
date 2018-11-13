package org.sonar.samples.java.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;
import org.sonar.samples.java.checks.oop.AvoidInstanceForStaticMethodCheck;
import org.sonar.samples.java.checks.oop.StringOrNotNullInsideEquailsCheck;

public class StringOrNotNullInsideEquailsCheckTest {
	@Test
	public void detected() {
//		JavaCheckVerifier.verify("src/test/files/cons/StringOrNotNullInsideEquailsDemo.java",
//				new StringOrNotNullInsideEquailsCheck());
	}
}
