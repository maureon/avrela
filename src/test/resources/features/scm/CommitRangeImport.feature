Feature: SCM commit range import

  Scenario: Commit range import by date range successful
    Given the commit range belongs to the repository owned by "davidmigloz" named "go-bees"
    And branch is "master"
    And range is 2017-10-15T00:00:00Z 2021-03-12T23:59:59Z
    When I import the commit
    Then the following commits should be present in same order
      | CommitSHA                                 |
      | 8289ea5ef2b644a551fda4c3b034f9ef9fcd0209  |
      | a5aaf9016611f9913c21d1f906d7c75df580529a  |
      | 14a4462b4bc65d2e319c6a22a2aea3892e218748  |
      | 2b54d064b390981e6270632886b2b019b2fc258b  |
      | 42f7ad239c65d138844c69e0b1ef6533f498191d  |