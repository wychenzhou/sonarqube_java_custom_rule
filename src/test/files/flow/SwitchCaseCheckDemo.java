package com.rule.test.flow;

public class SwitchCaseCheckDemo {

	public void aa(String name) {
		switch (name) {
		case "张三":
			System.out.println("张三");
			break;
		case "李四":
			System.out.println("李四");
		default:
			System.out.println("王五");
			break;
		}
	}
}
