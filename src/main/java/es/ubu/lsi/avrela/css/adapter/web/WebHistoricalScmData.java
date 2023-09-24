package es.ubu.lsi.avrela.css.adapter.web;

import es.ubu.lsi.avrela.scm.model.Commit;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebHistoricalScmData {

  private String repoOwner;

  private String repoName;

  private String branch;

  private ZonedDateTime startAt;

  private ZonedDateTime endAt;

  /*
   *  Commits ordered by date desc.
   */
  private List<Commit> commits;

  private String stringifyStartAt;

  private String stringifyEndAt;

  public void setStringifyStartAt(String stringStartAt){
    this.stringifyStartAt = stringStartAt;
    this.startAt = this.toZonedDateTime(stringStartAt).plusSeconds(1);
  }

  public void setStringifyEndAt(String stringEndAt){
    this.stringifyEndAt = stringEndAt;
    this.endAt = this.toZonedDateTime(stringEndAt).plusHours(23).plusMinutes(59).plusSeconds(59);
  }

  public void setStartAt(ZonedDateTime pStartAt){
    this.startAt = pStartAt;
    this.stringifyStartAt = this.toStringifyDate(pStartAt);
  }

  public void setEndAt(ZonedDateTime pEndAt){
    this.endAt = pEndAt;
    this.stringifyEndAt = this.toStringifyDate(pEndAt);
  }

  @SneakyThrows
  private ZonedDateTime toZonedDateTime(String string)  {
    Objects.requireNonNull(string, "date string should not be null");
    SimpleDateFormat iso8601DateFormat = new SimpleDateFormat("yyyy-MM-dd");
    long dateEpoch = (iso8601DateFormat.parse(string)).getTime();

    return Instant
        .ofEpochMilli(dateEpoch)
        .atZone(ZoneId.systemDefault());
  }

  private String toStringifyDate(ZonedDateTime zonedDateTime){
    Objects.requireNonNull(zonedDateTime, "date should not be null");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return zonedDateTime.format(formatter);
  }

}
