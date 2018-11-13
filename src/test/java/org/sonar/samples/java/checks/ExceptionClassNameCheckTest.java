package org.sonar.samples.java.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;
import org.sonar.samples.java.checks.naming.ExceptionClassNameCheck;

public class ExceptionClassNameCheckTest {
	@Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/exception/AgeOutOfBound.java", new ExceptionClassNameCheck());
    }


    @Test
    public void test3() {
        JavaCheckVerifier.verify("src/test/files/exception/AgeOutOfExceptionBound.java", new ExceptionClassNameCheck());
    }
}
