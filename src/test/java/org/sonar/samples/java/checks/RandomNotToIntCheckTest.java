package org.sonar.samples.java.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;
import org.sonar.samples.java.checks.oop.RandomNotToIntCheck;

public class RandomNotToIntCheckTest {
	@Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/RandomFloatToIntCheck.java", new RandomNotToIntCheck());
    }

}
