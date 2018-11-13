package org.sonar.samples.java.checks;

import org.junit.Test;
import org.sonar.java.checks.verifier.JavaCheckVerifier;
import org.sonar.samples.java.checks.comment.EnumConstantsMustHaveCommentCheck;

public class EnumConstantsMustHaveCommentCheckTest {

	@Test
	public void test() {
		//JavaCheckVerifier.verify("src/test/files/enum/Season.java", new EnumConstantsMustHaveCommentCheck());
	}
}
