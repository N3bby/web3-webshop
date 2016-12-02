package view;

public class TestIdGenerator {

    public static final String ID_PREFIX = "_test_";
    public static final String ID_PREFIX_ESCAPED = "\\_test\\_";

    public static String generateTestId(String component) {

        String testId = ID_PREFIX;
        testId += component + "_";
        testId += Math.round(Math.random() * 100000);

        return testId;

    }

}
