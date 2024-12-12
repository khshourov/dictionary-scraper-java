package io.github.khshourov.dictionaryscraper.mocks;

import io.github.khshourov.dictionaryscraper.enums.ReadingPurpose;
import io.github.khshourov.dictionaryscraper.interfaces.Reader;
import io.github.khshourov.dictionaryscraper.models.ReaderResponse;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class MockCambridgeReader extends Reader {

  public MockCambridgeReader(String baseUri) {
    this.baseUri = baseUri;
  }

  @Override
  public ReaderResponse read(String word, ReadingPurpose purpose) throws IOException {
    try {
      Path path =
          new File(
                  Objects.requireNonNull(
                          MockCambridgeReader.class
                              .getClassLoader()
                              .getResource(
                                  String.format(
                                      "io/github/khshourov/dictionaryscraper/cambridge/%s/%s.html",
                                      purpose.getValue(), word)))
                      .toURI())
              .toPath();
      return new ReaderResponse(
          String.format("%s/%s/%s", this.baseUri, purpose.getValue(), word),
          Files.readString(path));
    } catch (URISyntaxException e) {
      return null;
    }
  }
}
