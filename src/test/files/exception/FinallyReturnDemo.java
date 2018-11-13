package com.rule.test.enumtest;

public class FinallyReturnDemo {
	public int chu(int a) {
		if("ddddaa".endsWith("aa") && "chen".equals("chen") || a>10) {
			System.out.println("哈哈");
		}
		
		try { 
			int b = 10 / a;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return 10;
		} finally { 
			return 0;// Noncompliant
		}
	}
	
	
}
