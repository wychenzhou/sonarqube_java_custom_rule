import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConstantNameMapper {

	private final String aaa = "chenzhou";// Noncompliant

	private static final String HELLOWORLDMYNAMEISLILEI = "Hello WOLD MY NAME IS LI LEI"; // Noncompliant

	private static final String Hello_WORLD = "Hello WOLD"; // Noncompliant
}
