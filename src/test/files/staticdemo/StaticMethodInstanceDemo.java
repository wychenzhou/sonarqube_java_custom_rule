package com.rule.test.demo;

public class StaticMethodInstanceDemo {
	public void test() {
		BeanUtil bu = new BeanUtil();
		bu.aa(); // Noncompliant
		bu.bb();
	}
}

class BeanUtil {
	public static void aa() {
	}

	public void bb() {
	}
}
