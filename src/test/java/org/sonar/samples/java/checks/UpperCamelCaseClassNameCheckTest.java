package org.sonar.samples.java.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;
import org.sonar.samples.java.checks.naming.UpperCameCaseClassNameCheck;

public class UpperCamelCaseClassNameCheckTest {
	@Test
    public void test1() {
        JavaCheckVerifier.verify("src/test/files/upperCameCase/macroPolo.java", new UpperCameCaseClassNameCheck());
    }

   
}
