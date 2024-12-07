package com.github.khshourov.dictionaryscraper.enums;

import com.github.khshourov.dictionaryscraper.interfaces.Source;

public enum BaseSource implements Source {
  CAMBRIDGE;

  @Override
  public String getValue() {
    return this.name();
  }
}
