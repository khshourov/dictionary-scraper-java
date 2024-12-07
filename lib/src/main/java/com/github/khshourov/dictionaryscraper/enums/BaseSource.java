package com.github.khshourov.dictionaryscraper.enums;

import com.github.khshourov.dictionaryscraper.interfaces.Source;

public enum BaseSource implements Source {
  CAMBRIDGE("CAMBRIDGE");

  private final String value;

  BaseSource(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
