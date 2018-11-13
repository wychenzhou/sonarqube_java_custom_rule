package org.sonar.samples.java.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;
import org.sonar.samples.java.checks.oop.CollectionToArrayCheck;

public class CollectionToArrayCheckTest {
	@Test
	public void test() {
		JavaCheckVerifier.verify("src/test/files/staticdemo/CollectionToArrayDemo.java",
				new CollectionToArrayCheck());
	}
}
