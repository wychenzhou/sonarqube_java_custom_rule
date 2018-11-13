package org.sonar.samples.java;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class SubListDemo {

	public void testDemo() {
		List list = new ArrayList();
		list.add("哈哈");
		list.add("嘿嘿嘿");
		list.add("张三");
		list.add("李四");
		list.add("王五");
		List userList = (ArrayList) list.subList(1, 3);  // Noncompliant 
	}
}
