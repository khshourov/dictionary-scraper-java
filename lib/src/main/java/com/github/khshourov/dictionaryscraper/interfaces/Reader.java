package com.github.khshourov.dictionaryscraper.interfaces;

import com.github.khshourov.dictionaryscraper.enums.ReadingPurpose;
import com.github.khshourov.dictionaryscraper.models.ReaderResponse;
import java.io.IOException;

public abstract class Reader {
  protected String baseUri;

  public String getBaseUri() {
    return this.baseUri;
  }

  public abstract ReaderResponse read(String word, ReadingPurpose purpose) throws IOException;

  public void afterPropertiesSet() {
    if (this.baseUri == null || this.baseUri.trim().isEmpty()) {
      throw new IllegalStateException("reader.baseUri can not be null or empty");
    }
  }
}
