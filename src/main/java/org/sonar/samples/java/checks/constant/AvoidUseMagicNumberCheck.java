package org.sonar.samples.java.checks.constant;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.sonar.check.Rule;
import org.sonar.java.model.declaration.MethodTreeImpl;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.tree.AnnotationTree;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.EnumConstantTree;
import org.sonar.plugins.java.api.tree.ExpressionTree;
import org.sonar.plugins.java.api.tree.LiteralTree;
import org.sonar.plugins.java.api.tree.MethodTree;
import org.sonar.plugins.java.api.tree.NewArrayTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;
import org.sonar.plugins.java.api.tree.VariableTree;

/**
 * 不允许魔法值出现在代码中
 *
 * @author chenzhou
 *
 */
@Rule(key = "AvoidUseMagicNumberCheck")
public class AvoidUseMagicNumberCheck extends BaseTreeVisitor implements JavaFileScanner {

	private static final String DEFAULT_AUTHORIZED_NUMBERS = "-1,0,1";
	public String authorizedNumbers = DEFAULT_AUTHORIZED_NUMBERS;
	private List<BigDecimal> authorizedNumbersList = null;
	private JavaFileScannerContext context;

	@Override
	public void scanFile(JavaFileScannerContext context) {
		this.context = context;
		this.authorizedNumbersList = new ArrayList<>();
		for (String s : authorizedNumbers.split(",")) {
			authorizedNumbersList.add(new BigDecimal(s.trim()));
		}
		scan(context.getTree());
	}

	@Override
	public void visitEnumConstant(EnumConstantTree tree) {
		scan(tree.initializer().classBody());
	}

	@Override
	public void visitLiteral(LiteralTree tree) {
		if (isNumberLiteral(tree)) {
			DecimalFormat decimalFormat = new DecimalFormat();
			decimalFormat.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.ENGLISH));
			decimalFormat.setParseBigDecimal(true);
			BigDecimal checked = null;
			try {
				checked = (BigDecimal) decimalFormat.parse(tree.value());
			} catch (ParseException e) {
				// noop case not encountered
			}
			if (checked != null && !isExcluded(checked)) {
				context.reportIssue(this, tree, "不允许任何魔法值:" + tree.value() + "直接出现在代码中");
			}
		}
	}

	private static boolean isNumberLiteral(LiteralTree tree) {
		return tree.is(Tree.Kind.DOUBLE_LITERAL, Tree.Kind.FLOAT_LITERAL, Tree.Kind.LONG_LITERAL,
				Tree.Kind.INT_LITERAL);
	}

	private boolean isExcluded(BigDecimal bigDecimal) {
		for (BigDecimal bd : this.authorizedNumbersList) {
			if (bigDecimal.compareTo(bd) == 0) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void visitAnnotation(AnnotationTree annotationTree) {
		// Ignore literals within annotation
	}

	@Override
	public void visitVariable(VariableTree tree) {
		ExpressionTree initializer = tree.initializer();
		boolean arrayNotInitialized = initializer != null && initializer.is(Kind.NEW_ARRAY)
				&& ((NewArrayTree) initializer).initializers().isEmpty();
		boolean isFinalOrNoSemantic = context.getSemanticModel() == null || tree.symbol().isFinal();
		if (arrayNotInitialized || !isFinalOrNoSemantic) {
			super.visitVariable(tree);
		}
	}

	@Override
	public void visitMethod(MethodTree tree) {
		if (!((MethodTreeImpl) tree).isHashCodeMethod()) {
			super.visitMethod(tree);
		}
	}

}
