package com.rule.test.enumtest;

public class MethodReturnWrapperDemo {
	public int getnum(int a, int b) { // Noncompliant
		Integer c = a + b;
		return c;
	}
	
	public Integer getnum(int a, int b) {
		Integer c = a + b;
		return c;
	}
	
	public void getnum(int a, int b) {
		Integer c = a + b;
	}
}
