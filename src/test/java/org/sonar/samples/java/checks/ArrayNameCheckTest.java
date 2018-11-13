package org.sonar.samples.java.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;
import org.sonar.samples.java.checks.naming.ArrayNameCheck;

public class ArrayNameCheckTest {
	@Test
	public void test() {
		JavaCheckVerifier.verify(
				"src/test/files/array/ArrayNameCheckMapper.java",
				new ArrayNameCheck());
	}

}
