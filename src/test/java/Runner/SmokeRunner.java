package Runner;

import io.cucumber.java.AfterStep;
import io.cucumber.java.BeforeStep;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/",
        glue = {"StepDefinitions"},
        plugin = {"pretty", "html:target/cucumber.html"}
)
public class SmokeRunner {

    @Before
    public void before(){

    }

    @After
    public void after(){

    }

    @BeforeStep
    public void beforeStep(){

    }

    @AfterStep
    public void afterStep(){

    }
}
