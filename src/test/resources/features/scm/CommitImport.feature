Feature: Source control management commit import

  Scenario Outline: Import successful
    Given commits are imported from the repository owned by "<repoOwner>" named "<repoName>"
    When I import the commit "<sha>"
    Then total files changed should be <totalFilesChanged>
    And total additions should be <totalAdditions>
    And total deletions should be <totalDeletions>
    And commit author should be "<author>"
    And commit message should be "<message>"
    Examples:
      | repoOwner   | repoName | sha                                          | totalFilesChanged     | totalAdditions | totalDeletions | author         | message                                    |
      | davidmigloz | go-bees  | 434cd9935020fdcceb4c6fbb5b1f2c9fe4a10d87     | 6                     | 172            | 1              | David Miguel   | Add Firebase Analytics #196                |
      | davidmigloz | go-bees  | 043866c401c9b386e81ed12ae251e285e66c466b     | 4                     | 32             | 28             | David Miguel   | Update dependences #191                    |
      | davidmigloz | go-bees  | ba45e3bd0f9cecc6ae21ca94c565a5a24dcdda2f     | 1                     | 40             | 2              | David Miguel   | Improve versioning #201                    |
      | davidmigloz | go-bees  | 63c953f366823f944a9f9c5775b549f18076d055     | 4                     | 56             | 38             | David Miguel   | Continuous deployment to Google Play #200  |
      | davidmigloz | go-bees  | 42f7ad239c65d138844c69e0b1ef6533f498191d     | 2                     | 2              | 2              | David Miguel   | Update Crashlytics to 2.7.0 #203           |
      | davidmigloz | go-bees  | 2b54d064b390981e6270632886b2b019b2fc258b     | 2                     | 2              | 2              | David Miguel   | Update build tools to 26.0.2 #204          |
      | davidmigloz | go-bees  | 14a4462b4bc65d2e319c6a22a2aea3892e218748     | 1                     | 2              | 0              | David Miguel   | Bumped version number to 1.2               |
