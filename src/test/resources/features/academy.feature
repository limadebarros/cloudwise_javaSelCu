Feature: Academy tab

  Scenario: Verify that the page header matches the title of the course on the Academy page
    Given I am at the Cloudwise website
    When I go to the Academy tab
    Then I verify that the page header matches the title of the course on the Academy page

  Scenario: Print total number of courses grouped by category
    Given I am at the Cloudwise website
    When I go to the Academy tab
    Then I print the total number of courses grouped by category