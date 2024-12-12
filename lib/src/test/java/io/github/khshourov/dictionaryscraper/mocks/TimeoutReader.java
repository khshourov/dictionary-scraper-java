package io.github.khshourov.dictionaryscraper.mocks;

import io.github.khshourov.dictionaryscraper.enums.ReadingPurpose;
import io.github.khshourov.dictionaryscraper.interfaces.Reader;
import io.github.khshourov.dictionaryscraper.models.ReaderResponse;
import java.io.IOException;

public class TimeoutReader extends Reader {
  public TimeoutReader(String baseUri) {
    this.baseUri = baseUri;
  }

  @Override
  public ReaderResponse read(String word, ReadingPurpose purpose) throws IOException {
    throw new IOException("Read timeout");
  }
}