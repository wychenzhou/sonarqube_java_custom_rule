public class UnderlineDollarRuleMapper {
    private String _Hi;// Noncompliant
    private String $Hi;// Noncompliant

    public void test(String _Hi){}// Noncompliant

    public void $test(){}  // Noncompliant
}
