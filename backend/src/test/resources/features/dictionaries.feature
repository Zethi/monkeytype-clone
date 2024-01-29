Feature: Dictionaries
  Scenario: Create example dictionary
    Given user POST endpoint "/api/dictionary" with body "{\"name\":\"example\",\"words\":[\"one\",\"two\",\"three\",\"fourth\",\"five\",\"six\",\"seven\",\"eight\",\"nine\",\"ten\"]}"
    Then response status code should be 201

  Scenario: Get existent dictionary
    Given user GET endpoint "/api/dictionary/example"
    Then response status code should be 200
    And returned string should be "{\"name\":\"example\",\"words\":[\"one\",\"two\",\"three\",\"fourth\",\"five\",\"six\",\"seven\",\"eight\",\"nine\",\"ten\"]}"

  Scenario: Get not existent dictionary
    Given user GET endpoint "/api/dictionary/test-not-existent"
    Then response status code should be 404

  Scenario: Update dictionary
    Given user PUT endpoint "/api/dictionary/example" with body "{\"name\":\"example\",\"words\":[\"one\",\"two\",\"three\"]}"
    Then response status code should be 200

  Scenario: Validate if successfully update
    Given user GET endpoint "/api/dictionary/example"
    Then response status code should be 200
    And returned string should be "{\"name\":\"example\",\"words\":[\"one\",\"two\",\"three\"]}"

  Scenario: Delete dictionary
    Given user DELETE endpoint "/api/dictionary/example"
    Then response status code should be 200
