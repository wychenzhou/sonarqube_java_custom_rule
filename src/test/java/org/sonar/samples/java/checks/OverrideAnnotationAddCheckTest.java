package org.sonar.samples.java.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;
import org.sonar.samples.java.checks.oop.OverrideAnnotationAddCheck;

public class OverrideAnnotationAddCheckTest {
	@Test
	public void test() {
		JavaCheckVerifier.verify("src/test/files/over/BaseDaoImpl.java", new OverrideAnnotationAddCheck());
	}
}
