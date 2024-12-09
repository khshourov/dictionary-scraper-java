package com.github.khshourov.dictionaryscraper.enums;

import com.github.khshourov.dictionaryscraper.interfaces.Source;

@SuppressWarnings("java:S6548")
public enum BaseSource implements Source {
  CAMBRIDGE;

  @Override
  public String getValue() {
    return this.name();
  }
}
