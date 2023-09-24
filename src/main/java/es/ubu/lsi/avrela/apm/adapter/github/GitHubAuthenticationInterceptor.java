package es.ubu.lsi.avrela.apm.adapter.github;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;

/** Provides REST API authentication support */
@RequiredArgsConstructor
public class GitHubAuthenticationInterceptor implements RequestInterceptor {

  private final String token;

  @Override
  public void apply(RequestTemplate requestTemplate) {
    requestTemplate.header("Authorization","Bearer "+token);
  }
}
