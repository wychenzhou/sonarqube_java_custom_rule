package org.sonar.samples.java.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;
import org.sonar.samples.java.checks.naming.AbstractClassNameCheck;
import org.sonar.samples.java.checks.naming.PackageNameCheck;

public class PackageNameCheckTest {
	@Test
	public void test() {
		JavaCheckVerifier.verify("src/test/files/package/Package.java", new PackageNameCheck());
	}
}
