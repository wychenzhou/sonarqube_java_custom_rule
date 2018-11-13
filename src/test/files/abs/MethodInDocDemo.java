package com.rule.test.enumtest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MethodInDocDemo {
	public void aa() {
		List<String> list = new ArrayList<>();
		list.add("张三");
		list.add("李四");
		list.add("王五");
		for (String str : list) {
			list.remove(str);
		}
	}
}
