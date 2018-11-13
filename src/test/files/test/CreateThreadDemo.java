package com.rule.test.enumtest;

public class CreateThreadDemo {
	public void aa() {
		new Thread().start(); // Noncompliant
		
		
		new Thread(new Runnable() { // Noncompliant
			@Override
			public void run() {
				
			}
		}).start(); 
	}
}
