package com.github.khshourov.dictionaryscraper.enums;

public enum Source {
  CAMBRIDGE("CAMBRIDGE");

  private final String value;

  Source(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
