package org.sonar.samples.java.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;
import org.sonar.samples.java.checks.naming.UnderlineDollarNameCheck;

public class UnderlineDollarNameCheckTest {
    @Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/under/UnderlineDollarRuleMapper.java", new UnderlineDollarNameCheck());
    }
}
