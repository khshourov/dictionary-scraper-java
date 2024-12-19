package io.github.khshourov.dictionaryscraper.enums;

import io.github.khshourov.dictionaryscraper.interfaces.Source;

/**
 * Enum representing the base source used in the dictionary scraper framework.
 *
 * <p>The {@code BaseSource} enum provides a predefined set of online dictionary source identifiers.
 * Each source is uniquely represented by its identifier, which correlates to an external dictionary
 * source, enabling data retrieval for corresponding operations within the framework.
 *
 * <p>This enum implements the {@link Source} interface, ensuring that it provides the required
 * method for retrieving the unique source identifier.
 *
 * <p>Enum Constants: - {@code CAMBRIDGE}: Represents the Cambridge dictionary as a source.
 *
 * <p>Methods: - {@code getValue}: Retrieves the unique identifier of this source, which is the name
 * of the enum constant.
 */
@SuppressWarnings("java:S6548")
public enum BaseSource implements Source {
  CAMBRIDGE;

  @Override
  public String getValue() {
    return this.name();
  }
}
