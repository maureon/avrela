Feature: Import issue data

  Issue data should be imported, aggregated data should be checked.

  Scenario Outline: Issue import successful
    Given the repository owned by "<repoOwner>" named "<repoName>"
    When I import the issue with id "<issueId>"
    Then issue has comments check should be "<hasComments>"
    And issue total comments should be <totalComments>
    And issue has image check should be "<hasImage>"
    And issue has link check should be "<hasLink>"
    And issue is labeled check should be "<isLabeled>"
    And issue has label with value "<labelValue>"
    And issue total referenced commits should be <totalCommits>
    Examples:
      | repoOwner   | repoName  | issueId | hasComments | totalComments | hasImage | hasLink  | isLabeled | labelValue     | totalCommits |
      | davidmigloz | go-bees   | 159     | true        | 1             | false    | true     | true      | documentation  | 0            |
      | davidmigloz | go-bees   | 176     | true        | 1             | false    | true     | true      | testing        | 0            |
      | davidmigloz | go-bees   | 164     | true        | 1             | false    | false    | true      | bug            | 8            |
