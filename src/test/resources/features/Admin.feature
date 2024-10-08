Feature: Validate Admin functionality

  @Smoke
  Scenario: veirfy new user mapping
    Given user launch application
    When Login to application as an admin
    When Login to application as an admin "username" and "password"
    And Select Admin from left menu

  @regression
  Scenario: veirfy new user update mapping
    Given user launch application
    When Login to application as an admin
    When Login to application as an admin "username" and "password"
    And Select Admin from left menu