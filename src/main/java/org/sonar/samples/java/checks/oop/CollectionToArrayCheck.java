package org.sonar.samples.java.checks.oop;

import java.util.List;

import org.sonar.check.Rule;
import org.sonar.java.matcher.MethodMatcher;
import org.sonar.java.matcher.TypeCriteria;
import org.sonar.java.resolve.JavaType;
import org.sonar.plugins.java.api.semantic.Type;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.TypeCastTree;
import org.sonar.samples.java.checks.util.AbstractMethodDetectionImpro;

import com.google.common.collect.ImmutableList;

/**
 * 使用集合转数组的方法，必须使用集合的toArray(T[] array)，传入的是类型完全一样的数组
 * 
 * @author chenzhou
 *
 */
@Rule(key = "CollectionToArrayCheck")
public class CollectionToArrayCheck extends AbstractMethodDetectionImpro {
	private static final String ISSUE_MSG = "使用集合转数组的方法,必须使用集合的toArray(T[] array),传入的是类型完全一样的数组";

	private static final MethodMatcher COLLECTION_TO_ARRAY = MethodMatcher.create()
			.typeDefinition(TypeCriteria.subtypeOf("java.util.Collection")).name("toArray").withoutParameter();

	@Override
	protected List<MethodMatcher> getMethodInvocationMatchers() {
		return ImmutableList.of(COLLECTION_TO_ARRAY);
	}

	@Override
	protected void onMethodInvocationFound(MethodInvocationTree mit) {
		Tree parent = mit.parent();
		if (parent.is(Tree.Kind.TYPE_CAST)) {
			checkCast(((TypeCastTree) parent).symbolType(), mit);
		}
	}

	private void checkCast(Type type, MethodInvocationTree mit) {
		if (type.isArray() && !type.is("java.lang.Object[]")) {
			Type elementType = ((Type.ArrayType) type).elementType();
			if (!((JavaType) elementType).isTagged(JavaType.TYPEVAR)) {
				reportIssue(mit, ISSUE_MSG);
			}
		}
	}

}
