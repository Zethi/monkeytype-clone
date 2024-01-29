package com.github.zethi.monkeytypebackendclone.cucumber.glue;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public final class StepDefinitions {

    @LocalServerPort
    private String port;

    private WebTestClient testClient;

    private WebTestClient.ResponseSpec lastResponse;

    @PostConstruct
    public void init() {
        this.testClient = WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @When("user GET endpoint {string}")
    public void callEndpoint(String path) {
        lastResponse = testClient.get().uri(path).exchange();
    }

    @When("user POST endpoint {string} with body {string}")
    public void postEndpoint(String path, String body) {
        lastResponse = testClient.post().uri(path).contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(body)).exchange();
    }

    @When("user PUT endpoint {string} with body {string}")
    public void putEndpoint(String path, String body) {
        lastResponse = testClient.put().uri(path).contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(body)).exchange();
    }

    @When("user DELETE endpoint {string}")
    public void updateEndpoint(String path) {
        lastResponse = testClient.delete().uri(path).exchange();
    }

    @When("user PATCH endpoint {string}")
    public void patchEndpoint(String path) {
        lastResponse = testClient.patch().uri(path).exchange();
    }

    @Then("response status code should be {int}")
    public void thenStatusCode(int expected) {
        lastResponse.expectStatus().isEqualTo(expected);
    }

    @Then("returned string should be {string}")
    public void thenStringIs(String expected) {
        lastResponse.expectBody().json(expected);
    }

}
