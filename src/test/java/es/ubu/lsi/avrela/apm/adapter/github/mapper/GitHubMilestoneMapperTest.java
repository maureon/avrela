package es.ubu.lsi.avrela.apm.adapter.github.mapper;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import es.ubu.lsi.avrela.apm.adapter.github.model.GitHubIssue;
import es.ubu.lsi.avrela.apm.adapter.github.model.GitHubItemState;
import es.ubu.lsi.avrela.apm.adapter.github.model.GitHubMilestone;
import es.ubu.lsi.avrela.apm.model.Sprint;
import java.time.ZonedDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class GitHubMilestoneMapperTest {

  private GitHubMilestoneMapper gitHubMilestoneMapper = null;

  @BeforeEach
  public void init(){
    GitHubIssueMapper gitHubIssueMapper = new GitHubIssueMapper(
        new GitHubCommentMapper(),
        new GitHubLabelMapper(),
        new GitHubIssueEventMapper());
    gitHubMilestoneMapper = new GitHubMilestoneMapper(gitHubIssueMapper);
  }


  @Nested
  @DisplayName("Given a null Milestone")
  public class NullMilestone{

    @Nested
    @DisplayName("When I map the GitHub milestone to domain entity")
    public class MapNullMilestone{

      @Test
      @DisplayName("The  result should be null")
      void shouldReturnNull(){
        GitHubMilestone milestone = null;
        List<GitHubIssue> issues = null;

        Sprint result = gitHubMilestoneMapper.toDomain(milestone);

        assertNull(result);
      }

    }

  }

  @Nested
  @DisplayName("Given a none  null Milestone")
  public class NoneNullMilestone{

    @Nested
    @DisplayName("When I map the GitHub milestone to domain entity")
    public class MapNoneNullMilestone{

      @Test
      @DisplayName("The  result should be not null")
      void shouldReturnNull(){
        GitHubMilestone milestone = GitHubMilestone.builder()
            .number(1)
            .title("Milestone title")
            .description("Milestone description")
            .state(GitHubItemState.OPEN)
            .dueOn(ZonedDateTime.now())
            .createdAt(ZonedDateTime.now())
            .updatedAt(ZonedDateTime.now())
            .closedAt(ZonedDateTime.now())
            .build();



        List<GitHubIssue> issues = null;

        Sprint result = gitHubMilestoneMapper.toDomain(milestone);

        assertNotNull(result);
        assertAll("Verify mapping result",
            ()-> assertEquals(milestone.getNumber().toString(), result.getId()),
            ()-> assertEquals(milestone.getTitle(), result.getTitle()),
            ()-> assertEquals(milestone.getDescription(), result.getDescription()),
            ()-> assertEquals(milestone.getState().name(), result.getState().name()),
            ()-> assertEquals(milestone.getDueOn(), result.getDueOn())
            );
      }

    }

  }

}
