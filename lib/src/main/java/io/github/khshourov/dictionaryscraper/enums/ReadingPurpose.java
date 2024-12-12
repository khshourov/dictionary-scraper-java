package io.github.khshourov.dictionaryscraper.enums;

public enum ReadingPurpose {
  PRONUNCIATION("pronunciation"),
  MEANING("meaning"),
  BOTH("both");

  private String value;

  ReadingPurpose(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }
}
