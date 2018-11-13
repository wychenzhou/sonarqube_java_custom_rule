package org.sonar.samples.java.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;
import org.sonar.samples.java.checks.naming.BooleanPropertyNameCheck;

public class BooleanPropertyNameCheckTest {
	
	@Test
	public void test01() {
		JavaCheckVerifier.verify("src/test/files/boolean/BooleanPropertyName.java", new BooleanPropertyNameCheck());
	}
}
