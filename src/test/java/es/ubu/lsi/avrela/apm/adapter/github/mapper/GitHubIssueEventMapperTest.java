package es.ubu.lsi.avrela.apm.adapter.github.mapper;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import es.ubu.lsi.avrela.apm.adapter.github.model.GitHubIssueEvent;
import es.ubu.lsi.avrela.apm.adapter.github.model.GitHubIssueEventType;
import es.ubu.lsi.avrela.apm.adapter.github.model.GitHubUser;
import es.ubu.lsi.avrela.apm.model.IssueEvent;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class GitHubIssueEventMapperTest {

  private GitHubIssueEventMapper mapper = new GitHubIssueEventMapper();

  @Nested
  @DisplayName("Given a none null GitHub issue event")
  public class  NoneNullGitHubIssueEvent {

    @Nested
    @DisplayName("When I map the GitHub issue event to domain entity")
    class MapNoneNullGitHubIssueEvent{

      @Test
      @DisplayName("Then domain entity should contain the information needed")
      void shouldReturnDomainEntity(){
        GitHubIssueEvent event = getGitHubIssueEvent();

        IssueEvent result = mapper.toDomain(event);

        assertNotNull(result);
        assertAll("Verify mapping result",
            ()-> assertEquals(event.getId().toString(), result.getId()),
            ()-> assertEquals(event.getBody(), result.getBody()),
            ()-> assertEquals(event.getType().name(), result.getEventType().name()),
            ()-> assertEquals(event.getCreatedAt(), result.getCreatedAt()),
            ()-> assertEquals(event.getCreatedAt(), result.getCreatedAt())
        );
      }
    }

}

  private GitHubIssueEvent getGitHubIssueEvent() {
    return GitHubIssueEvent.builder()
        .id(1L)
        .body("Esto es un ejemplo de http:// con enlace")
        .commitId("SADAFDFEfasFASFASFS")
        .type(GitHubIssueEventType.CLOSED)
        .createdAt(ZonedDateTime.now())
        .actor(new GitHubUser("user"))
        .build();
  }
  }