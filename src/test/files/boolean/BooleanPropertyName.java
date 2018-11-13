package org.sonar.samples.java.checks.naming;

public class BooleanPropertyName {

	private Boolean isMale; // Noncompliant

	private boolean isDog; // Noncompliant
}
