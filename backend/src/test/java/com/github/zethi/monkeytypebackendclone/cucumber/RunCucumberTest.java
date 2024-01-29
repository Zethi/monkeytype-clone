package com.github.zethi.monkeytypebackendclone.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "html:target/cucumber-report.html"},
        features = {"src/test/resources/features"},
        glue = "com.github.zethi.monkeytypebackendclone.cucumber.glue"
)
public final class RunCucumberTest {
}