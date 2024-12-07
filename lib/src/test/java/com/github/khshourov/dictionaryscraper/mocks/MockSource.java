package com.github.khshourov.dictionaryscraper.mocks;

import com.github.khshourov.dictionaryscraper.interfaces.Source;

public enum MockSource implements Source {
  MOCK_SOURCE_1("MockSource1"),
  MOCK_SOURCE_2("MockSource2");

  private final String value;

  MockSource(String value) {
    this.value = value;
  }

  @Override
  public String getValue() {
    return this.value;
  }
}
