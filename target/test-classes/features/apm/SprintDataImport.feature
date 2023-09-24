Feature: Import sprint data

  Sprint data should be imported when input data is valid.
  Uses https://github.com/davidmigloz/go-bees as example data.

  Scenario Outline: Sprint import successful
    Given a repository owned by "<repoOwner>" named "<repoName>"
    And the sprint dates <sprintStartAt> and <sprintEndAt>
    When I import the sprint issues
    Then total issues should be <issues>
    And total issues labeled as documentation should be <documentation>
    And total issues labeled as feature should be <feature>
    And total issues labeled as testing should be <testing>
    And total issues labeled as bug should be <bug>
    And total issues with comments should be <withComments>
    And total issues with task list should be <withTaskList>
    And total closed issues should be <closed>
    Examples:
      | repoOwner   | repoName | sprintStartAt        | sprintEndAt          | issues      | documentation | feature | testing | bug | withComments | withTaskList | closed |
      | davidmigloz | go-bees  | 2017-01-25T00:00:00Z | 2017-01-25T23:59:59Z | 17          | 7             | 4       | 2       | 4   | 2            | 0            | 17     |
      | davidmigloz | go-bees  | 2017-02-01T07:00:00Z | 2017-02-02T08:00:00Z | 9           | 2             | 1       | 1       | 5   | 3            | 1            | 9      |
      | davidmigloz | go-bees  | 2017-02-07T07:00:00Z | 2017-02-08T08:00:00Z | 7           | 4             | 0       | 1       | 2   | 1            | 2            | 7      |
      | davidmigloz | go-bees  | 2017-02-15T07:00:00Z | 2017-02-16T08:00:00Z | 5           | 4             | 1       | 0       | 0   | 3            | 1            | 5      |



