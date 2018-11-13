package org.sonar.samples.java.checks.constant;

public class MegicExample {
	public static void main(String[] args) {
		doSomething();
	}

	public static void doSomething() {
		int a = 9;
		if (a > 7) {
			System.out.println("哈哈哈");
		}
		for (int i = 0; i < 4; i++) { // Noncompliant
			System.out.println("i:" + i);
		}
	}
}
