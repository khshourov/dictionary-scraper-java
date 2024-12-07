package com.github.khshourov.dictionaryscraper.mocks;

import com.github.khshourov.dictionaryscraper.enums.ReadingPurpose;
import com.github.khshourov.dictionaryscraper.interfaces.Reader;
import com.github.khshourov.dictionaryscraper.models.ReaderResponse;
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
