package com.rule.test.enumtest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CollectionDemo {
	{
		List list02 = new LinkedList<>(); // Noncompliant

		Set set = new HashSet<>();// Noncompliant

	}

	public void test() {
		List list = new ArrayList(); // Noncompliant
		Map map = new HashMap<>(); // Noncompliant

		List list01 = new ArrayList<>(20); //Compliant
		Map map01 = new HashMap<>(15); //Compliant

		StringBuffer st = new StringBuffer("i am chen zhou!'");
	}
}
