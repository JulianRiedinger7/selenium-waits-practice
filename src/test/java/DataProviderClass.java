import org.testng.annotations.DataProvider;

public class DataProviderClass {

    @DataProvider(name = "usernamesAndPasswords")
    public Object[][] getUsernames() {
        return new Object[][] {
                {"standard_user", "secret_sauce"},
                {"performance_glitch_user", "secret_sauce"},
                {"problem_user", "secret_sauce"},
                {"locked_out_user", "secret_sauce"}
        };
    }
}
