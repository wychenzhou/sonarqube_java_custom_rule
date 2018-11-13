package org.sonar.samples.java.checks;

import java.util.Date;

public class GetTime{
	public static void main(String[] args) {
		long date = new Date().getTime(); // Noncompliant
		System.out.println("当前毫秒数:" + date);
		
		System.out.println("2.当前毫秒数:" + System.currentTimeMillis()); 
	}
}
