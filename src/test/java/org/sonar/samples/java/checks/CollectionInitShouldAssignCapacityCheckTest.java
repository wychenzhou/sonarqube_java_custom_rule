package org.sonar.samples.java.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;
import org.sonar.samples.java.checks.set.CollectionInitShouldAssignCapacityCheck;

public class CollectionInitShouldAssignCapacityCheckTest {
	@Test
	public void test01() {
		JavaCheckVerifier.verify("src/test/files/set/CollectionDemo.java", new CollectionInitShouldAssignCapacityCheck());
	}
}
