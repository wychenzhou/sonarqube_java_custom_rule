package org.sonar.samples.java.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;
import org.sonar.samples.java.checks.set.SubListNotCastArrayListCheck;

public class SubListNotCastArrayListCheckTest {
	@Test
	public void detected() {
		JavaCheckVerifier.verify("src/test/files/SubListDemo.java", new SubListNotCastArrayListCheck());
	}
}
