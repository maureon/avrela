Feature: Agile project management simulation case study evaluation

  Evaluation involves:
    - A case study
    - A simulation
    - A rubric

  Scenario: Successful evaluation
    Given a case study with repo owner "davidmigloz", name "go-bees" and time period 2017-01-25T00:00:00Z 2017-01-25T23:59:59Z
    And a simulation with repo owner "davidmigloz", name "go-bees", time period 2017-01-25T00:00:00Z 2017-01-25T23:59:59Z and 1 participant(s)
    And a rubric
      | Criteria                                         | 0  | 1           | 2    |
      | Teamwork                                         | 50 | None        | 100  |
      | TaskManagementToolLearning  - Description        | 0  | 100         | None |
      | TaskManagementToolLearning  - Organization       | 0  | 100         | None |
    When I apply the rubric
    Then rubric score should be
      | Criteria                                         | 0    | 1          | 2       |
      | Teamwork                                         | None | None       | X       |
      | TaskManagementToolLearning - Description         | None | X          | None    |
      | TaskManagementToolLearning - Organization        | None | X          | None    |