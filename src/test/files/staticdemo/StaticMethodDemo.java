package com.rule.test.enumtest;

public class StaticMethodDemo {
	private Utils util = new Utils();
	
	public void test() {
		Utils.getUserName();
		util.getUserName();
		util.getPwd();
	}
}

class Utils {
	public static void getUserName() {

	}

	public void getPwd() {

	}
}
