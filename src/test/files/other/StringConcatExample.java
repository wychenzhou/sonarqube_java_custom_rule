package org.sonar.samples.java.checks.other;

public class StringConcatExample {
	public static void main(String[] args) {
		String arrayOfStrings[] = { "陈", "张", "李" };
		String str = "";
		for (int i = 0; i < arrayOfStrings.length; ++i) {
			str = str + arrayOfStrings[i];// Noncompliant
		}
	}
}
