package io.palsson.exercises;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Helper {
  // A resource is returned as a URL, which we can transform to a URI, which can then be transformed
  // to a Path.
  public static List<String> getBookWords(String bookFile) {
    try {
      URI uri = Helper.class.getResource(String.format("/%s", bookFile)).toURI();
      byte[] blob = Files.readAllBytes(Paths.get(uri));
      String content = new String(blob, StandardCharsets.UTF_8);
      return Arrays.asList(content.split("[\\P{L}]+"));
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
}
