package org.sonar.samples.java.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;
import org.sonar.samples.java.checks.naming.AbstractClassNameCheck;
import org.sonar.samples.java.checks.oop.AviodUseDeprecatedCheck;

public class AviodUseDeprecatedCheckTest {
	@Test
	public void test() {
		//JavaCheckVerifier.verify("src/test/files/deprecated/DepImpl.java", new AviodUseDeprecatedCheck());
	}
}
