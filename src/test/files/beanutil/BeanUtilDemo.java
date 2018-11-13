package com.rule.test.util;

import org.apache.commons.beanutils.BeanUtils;

public class BeanUtilDemo{
	public void getcopyProperties(Object stuVo, Object stu) throws Exception {
		BeanUtils.copyProperties(stuVo, stu); // Noncompliant
	}
}
