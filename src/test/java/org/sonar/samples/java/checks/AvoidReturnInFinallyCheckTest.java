package org.sonar.samples.java.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;
import org.sonar.samples.java.checks.exception.AvoidReturnInFinallyCheck;

public class AvoidReturnInFinallyCheckTest {
	@Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/exception/FinallyReturnDemo.java", new AvoidReturnInFinallyCheck());
    }

}
