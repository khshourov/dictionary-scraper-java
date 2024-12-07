package com.github.khshourov.dictionaryscraper.enums;

public enum Region {
  US("us"),
  UK("uk");

  private String value;

  Region(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }

  public static Region fromValue(String value) {
    for (Region region : Region.values()) {
      if (region.getValue().equals(value)) {
        return region;
      }
    }

    throw new IllegalArgumentException("No enum constant for value: " + value);
  }
}
