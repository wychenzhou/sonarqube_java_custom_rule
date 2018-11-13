package org.sonar.samples.java.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;
import org.sonar.samples.java.checks.naming.ConstantNameCheck;

public class ConstantNameCheckTest {
    @Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/cons/ConstantNameMapper.java", new ConstantNameCheck());
    }
}

