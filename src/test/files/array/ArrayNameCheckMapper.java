
public class ArrayNameCheckMapper {
	private String zzzz;

    private String errorTemp[];// Noncompliant

    private String[] rightTemp;

    public void getTemp(String noTemp[]){// Noncompliant
        int intHi[];// Noncompliant
        int[] intNo;
    }
}
