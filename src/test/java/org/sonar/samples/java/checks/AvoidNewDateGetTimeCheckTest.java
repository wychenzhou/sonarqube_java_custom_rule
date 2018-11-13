package org.sonar.samples.java.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;
import org.sonar.samples.java.checks.naming.AbstractClassNameCheck;
import org.sonar.samples.java.checks.oop.AvoidNewDateGetTimeCheck;

public class AvoidNewDateGetTimeCheckTest {
	@Test
	public void test() {
		//JavaCheckVerifier.verify("src/test/files/other/PrintStackTraceCalledWithoutArgumentCheck.java", new AvoidNewDateGetTimeCheck());
	}
	
	@Test
	public void test1() {
		//JavaCheckVerifier.verify("src/test/files/other/GetTime.java", new AvoidNewDateGetTimeCheck());
	}
}
