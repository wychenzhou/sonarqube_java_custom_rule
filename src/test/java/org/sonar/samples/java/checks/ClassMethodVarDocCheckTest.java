package org.sonar.samples.java.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;
import org.sonar.samples.java.checks.comment.ClassMethodVarDocCheck;
import org.sonar.samples.java.checks.comment.ClassMustHaveAuthorCheck;

public class ClassMethodVarDocCheckTest {
	
	@Test
	public void test() {
		//JavaCheckVerifier.verify("src/test/files/author/AuthorDocExample2.java", new ClassMethodVarDocCheck());
	}
}
