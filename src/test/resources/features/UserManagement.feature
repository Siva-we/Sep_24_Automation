Feature: Validate user management

  @smoke @user @admin
  Scenario Outline: Validate New Admin User
    Given user launch application
    When Login to application as an admin
    When Login to application as an admin "<username>" and "<password>"
    And Select Admin from left menu
#    And Click on ADD button
#    And Fill all mandatory fields
#    And Submit the form
#    Then validate success message
#    And verify record in the list

  Examples:
    |username|password|
    |Admin   |admin123|
    |Admin2  |admin123|
    |Admin3 |admin123|