
public class A {
	void nonCompliant() {
		if (condition) {
			firstActionInBlock();
			secondAction(); // Noncompliant
		}
		thirdAction();

		if (condition) {
			firstActionInBlock();
		}
		
		String str = null;
		for (int i = 0; i < array.length; i++)
			str = array[i];
		doTheThing(str); // Noncompliant

		while (true)
			firstActionInBlock();
		secondAction(); // Noncompliant

		int[] test = new int[] { 1, 2 };
		for (int intValue : test)
			firstActionInBlock();
		// comment
		// bla bla bla
		secondAction(); // Compliant because vertical whitespace
	}
}
