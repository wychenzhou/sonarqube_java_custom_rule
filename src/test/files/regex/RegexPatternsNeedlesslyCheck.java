import java.util.regex.Pattern;

class A {

  private static final String STRCONST = "constant";
  private final String finalField = "";
  private static String staticNotFinal = "";

  private static final Pattern myRegex = Pattern.compile("myRegex"); // Compliant
  private static final Pattern myRegex1 = ((Pattern.compile("myRegex"))); // Compliant
  private static final Pattern myRegex2;
  private final Pattern myRegex3 = Pattern.compile("myRegex"); // Noncompliant
  private static Pattern myRegex4 = Pattern.compile("myRegex"); // Noncompliant


  static {
    myRegex2 = ((Pattern.compile("myRegex"))); // Compliant
  }

  void foo(String param) {
    Pattern regex = Pattern.compile("regex1"); // Noncompliant {{使用 "static final"重构此代码}}
    Pattern.compile(STRCONST); // Noncompliant
    Pattern.compile(param);
    Pattern.compile(finalField); // Compliant
    Pattern.compile(staticNotFinal); // Compliant
    Pattern.compile(A.STRCONST); // Noncompliant
    Pattern.compile(this.finalField); // Compliant
    Pattern.compile(A.staticNotFinal); // Compliant
    Pattern regex3 = Pattern.compile(param); // Compliant
    Pattern regex4 = Pattern.compile(param.toString()); // Compliant

  }
}
