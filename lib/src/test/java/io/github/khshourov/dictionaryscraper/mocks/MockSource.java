package io.github.khshourov.dictionaryscraper.mocks;

import io.github.khshourov.dictionaryscraper.interfaces.Source;

/**
 * MockSource is an enumeration that represents mock dictionary sources. It implements the Source
 * interface, providing a mechanism to obtain the name of the enumerated source as its value.
 *
 * <p>This enum is primarily used for testing purposes, enabling the registration and management of
 * mock sources in the context of a dictionary scraper.
 *
 * <p>Enum Constants: - MOCK_SOURCE_1: Represents the first mock source. - MOCK_SOURCE_2: Represents
 * the second mock source.
 *
 * <p>Methods: - getValue: Returns the name of the enumerated constant, as defined in the Source
 * interface.
 */
public enum MockSource implements Source {
  MOCK_SOURCE_1,
  MOCK_SOURCE_2;

  /**
   * Retrieves the name of the enumerated constant.
   *
   * @return the name of the current instance of the enumeration as a {@code String}
   */
  @Override
  public String getValue() {
    return this.name();
  }
}
