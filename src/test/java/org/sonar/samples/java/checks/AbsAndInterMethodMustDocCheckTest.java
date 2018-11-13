package org.sonar.samples.java.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;
import org.sonar.samples.java.checks.comment.AbsAndInterMethodMustDocCheck;

public class AbsAndInterMethodMustDocCheckTest {
	
	@Test
	public void test() {
		//JavaCheckVerifier.verify("src/test/files/abs/AbstractJocDemo.java", new AbsAndInterMethodMustDocCheck());
	}
}
