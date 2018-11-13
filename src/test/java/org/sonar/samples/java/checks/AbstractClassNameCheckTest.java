package org.sonar.samples.java.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;
import org.sonar.samples.java.checks.naming.AbstractClassNameCheck;

public class AbstractClassNameCheckTest {
	
    @Test
    public void test() {
        JavaCheckVerifier.verify("src/test/files/abs/HiClass.java", new AbstractClassNameCheck());
    }

    @Test
    public void test1() {
        JavaCheckVerifier.verify("src/test/files/abs/MyNameIsAbstract.java", new AbstractClassNameCheck());
    }

}
