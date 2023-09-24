package es.ubu.lsi.avrela.apm.adapter.github;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.ZonedDateTime;

public class ZonedDateTimeTypeAdapter extends TypeAdapter<ZonedDateTime> {

  @Override
  public void write(JsonWriter out, ZonedDateTime value) throws IOException {
    throw new UnsupportedOperationException();
  }

  @Override
  public ZonedDateTime read(JsonReader in) throws IOException {
    if (in.peek() == JsonToken.NULL) {
      in.nextNull();
      return null;
    }
    return ZonedDateTime.parse(in.nextString());
  }

}
