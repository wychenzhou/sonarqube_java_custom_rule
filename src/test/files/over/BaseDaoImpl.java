package org.sonar.samples.java;

public class BaseDaoImpl extends BaseDao {
	
	public void aa() {} // Noncompliant

	public void bb() {} // Noncompliant

}

class BaseDao {
	public void aa() {};
	public void bb() {};
}
