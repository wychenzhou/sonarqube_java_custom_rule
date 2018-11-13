package com.rule.test.demo;

public class StringOrNotNullInsideEquailsDemo {
	private String secondary;
	private String primary;

	public void test(String color) {
		if (color != null) {
			color.equals(secondary);
		}
	}
}
