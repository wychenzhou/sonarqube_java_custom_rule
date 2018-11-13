package org.sonar.samples.java.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;
import org.sonar.samples.java.checks.naming.ServiceOrDaoImplClassNameCheck;

public class ServiceOrDaoImplClassNameCheckTest {
	@Test
	public void test3() {
		JavaCheckVerifier.verify("src/test/files/test/ServiceOrDaoImplDemo.java", new ServiceOrDaoImplClassNameCheck());
	}
}
