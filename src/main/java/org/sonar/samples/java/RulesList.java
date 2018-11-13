package org.sonar.samples.java;

import com.google.common.collect.ImmutableList;

import java.util.List;

import org.sonar.plugins.java.api.JavaCheck;
import org.sonar.samples.java.checks.comment.AbsAndInterMethodMustDocCheck;
import org.sonar.samples.java.checks.comment.ClassMethodVarDocCheck;
import org.sonar.samples.java.checks.comment.ClassMustHaveAuthorCheck;
import org.sonar.samples.java.checks.comment.EnumConstantsMustHaveCommentCheck;
import org.sonar.samples.java.checks.concurrent.AvoidExecutorsCreatePoolCheck;
import org.sonar.samples.java.checks.concurrent.CreateThreadShouldPoolCheck;
import org.sonar.samples.java.checks.constant.AvoidUseMagicNumberCheck;
import org.sonar.samples.java.checks.constant.UpperEllRuleCheck;
import org.sonar.samples.java.checks.exception.AvoidReturnInFinallyCheck;
import org.sonar.samples.java.checks.exception.MethodReturnWrapperCheck;
import org.sonar.samples.java.checks.flowcontrol.AvoidComplexConditionCheck;
import org.sonar.samples.java.checks.flowcontrol.MultilineBlocksCurlyCheck;
import org.sonar.samples.java.checks.flowcontrol.SwitchCaseEndWithBreakCheck;
import org.sonar.samples.java.checks.flowcontrol.SwitchDefaultMustLastCheck;
import org.sonar.samples.java.checks.flowcontrol.SwitchHaveDefaultCheck;
import org.sonar.samples.java.checks.naming.AbstractClassNameCheck;
import org.sonar.samples.java.checks.naming.ArrayNameCheck;
import org.sonar.samples.java.checks.naming.BooleanPropertyNameCheck;
import org.sonar.samples.java.checks.naming.ConstantNameCheck;
import org.sonar.samples.java.checks.naming.ExceptionClassNameCheck;
import org.sonar.samples.java.checks.naming.LowerCameCaseVariableNameCheck;
import org.sonar.samples.java.checks.naming.PackageNameCheck;
import org.sonar.samples.java.checks.naming.ServiceOrDaoImplClassNameCheck;
import org.sonar.samples.java.checks.naming.TestClassNameCheck;
import org.sonar.samples.java.checks.naming.UnderlineDollarNameCheck;
import org.sonar.samples.java.checks.naming.UpperCameCaseClassNameCheck;
import org.sonar.samples.java.checks.oop.AviodUseDeprecatedCheck;
import org.sonar.samples.java.checks.oop.AvoidInstanceForStaticMethodCheck;
import org.sonar.samples.java.checks.oop.AvoidNewDateGetTimeCheck;
import org.sonar.samples.java.checks.oop.CollectionToArrayCheck;
import org.sonar.samples.java.checks.oop.OverrideAnnotationAddCheck;
import org.sonar.samples.java.checks.oop.RandomNotToIntCheck;
import org.sonar.samples.java.checks.oop.RegexPatternCompileCheck;
import org.sonar.samples.java.checks.oop.StringOrNotNullInsideEquailsCheck;
import org.sonar.samples.java.checks.oop.WrapperCompareEqualsCheck;
import org.sonar.samples.java.checks.other.AvoidBeanUtilsCopyCheck;
import org.sonar.samples.java.checks.other.AvoidIBatisQueryListCheck;
import org.sonar.samples.java.checks.set.CollectionInitShouldAssignCapacityCheck;
import org.sonar.samples.java.checks.set.SubListNotCastArrayListCheck;

/**
 * 用于规则扩展
 *
 * @author chenzhou
 */
public final class RulesList {

    private RulesList() {
    }

    public static List<Class> getChecks() {
        return ImmutableList.<Class>builder().addAll(getJavaChecks()).addAll(getJavaTestChecks()).build();
    }

    public static List<Class<? extends JavaCheck>> getJavaChecks() {
        return ImmutableList.<Class<? extends JavaCheck>>builder().add(AbstractClassNameCheck.class)
                .add(ArrayNameCheck.class).add(BooleanPropertyNameCheck.class).add(ConstantNameCheck.class)
                .add(ExceptionClassNameCheck.class).add(LowerCameCaseVariableNameCheck.class)
                .add(PackageNameCheck.class).add(TestClassNameCheck.class).add(UnderlineDollarNameCheck.class)
                .add(UpperCameCaseClassNameCheck.class).add(AviodUseDeprecatedCheck.class).add(UpperEllRuleCheck.class)
                .add(OverrideAnnotationAddCheck.class).add(AvoidUseMagicNumberCheck.class)
                .add(ClassMustHaveAuthorCheck.class).add(ClassMethodVarDocCheck.class)
                .add(EnumConstantsMustHaveCommentCheck.class).add(AvoidNewDateGetTimeCheck.class)
                .add(AvoidBeanUtilsCopyCheck.class).add(AvoidIBatisQueryListCheck.class)
                .add(RandomNotToIntCheck.class).add(AvoidExecutorsCreatePoolCheck.class)
                .add(SubListNotCastArrayListCheck.class).add(CollectionInitShouldAssignCapacityCheck.class)
                .add(CollectionToArrayCheck.class).add(AvoidReturnInFinallyCheck.class)
                .add(AvoidComplexConditionCheck.class).add(WrapperCompareEqualsCheck.class)
                .add(MethodReturnWrapperCheck.class).add(MultilineBlocksCurlyCheck.class)
                .add(AbsAndInterMethodMustDocCheck.class).add(RegexPatternCompileCheck.class)
                .add(CreateThreadShouldPoolCheck.class).add(ServiceOrDaoImplClassNameCheck.class)
                .add(AvoidInstanceForStaticMethodCheck.class)
                .add(StringOrNotNullInsideEquailsCheck.class).add(SwitchHaveDefaultCheck.class)
                .add(SwitchCaseEndWithBreakCheck.class).add(SwitchDefaultMustLastCheck.class).build();
    }

    public static List<Class<? extends JavaCheck>> getJavaTestChecks() {
        return ImmutableList.<Class<? extends JavaCheck>>builder().build();
    }
}
