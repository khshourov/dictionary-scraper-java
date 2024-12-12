package io.github.khshourov.dictionaryscraper.mocks;

import io.github.khshourov.dictionaryscraper.interfaces.Source;

public enum MockSource implements Source {
  MOCK_SOURCE_1,
  MOCK_SOURCE_2;

  @Override
  public String getValue() {
    return this.name();
  }
}
