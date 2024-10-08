package Runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        tags = "smoke",
        features = "src/test/resources/features/",
        glue = {"StepDefinitions"},
        plugin = {"pretty", "html:target/cucumber.html", "html:target/cucumber.json"}

)
public class UserRunner {


}
