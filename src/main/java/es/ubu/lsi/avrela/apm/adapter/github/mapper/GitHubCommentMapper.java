package es.ubu.lsi.avrela.apm.adapter.github.mapper;

import es.ubu.lsi.avrela.apm.adapter.github.model.GitHubComment;
import es.ubu.lsi.avrela.apm.model.Comment;
import java.util.ArrayList;
import java.util.List;

public class GitHubCommentMapper {

  public Comment toDomain(GitHubComment comment){
    Comment result = new Comment();
    result.setBody(comment.getBody());
    result.setAuthor(comment.getUser().getLogin());
    return result;
  }

  public List<Comment> toDomain(List<GitHubComment> comments){
    if (comments == null){
      return new ArrayList<>();
    }
    List<Comment> result = new ArrayList<>(comments.size());
    for(GitHubComment comment : comments){
      result.add(toDomain(comment));
    }
    return result;
  }

}
