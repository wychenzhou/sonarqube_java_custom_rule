public class LowerCameCaseMapper {
    private String ID;// Noncompliant
    private String UserName;// Noncompliant
    private String password;// Compliant

    private static final String NO_BB_CC_DD_CC_SSS = "2212313213";


    public void GetPassword(){} // Noncompliant

    public void getUser(String UserInfo) {// Noncompliant

    }
}
