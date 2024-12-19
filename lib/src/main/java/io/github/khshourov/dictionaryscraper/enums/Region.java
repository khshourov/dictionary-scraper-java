package io.github.khshourov.dictionaryscraper.enums;

/**
 * Represents the regions for which dictionary data, such as IPA (International Phonetic Alphabet),
 * pronunciation, and other relevant information, can be categorized.
 *
 * <p>The {@code Region} enum is primarily used in the context of extracting and identifying
 * regional differences in phonetic pronunciations or other word-related metadata specific to a
 * region. This aids in distinguishing variations like American (US) and British (UK) English.
 *
 * <p>Enum Constants: - {@code US}: Represents the United States region. - {@code UK}: Represents
 * the United Kingdom region.
 *
 * <p>Typical Usage: The {@code Region} enum is used in dictionary scraper frameworks to map and
 * associate IPA listings or other region-specific data for a given word as part of the scraping
 * process.
 */
public enum Region {
  US,
  UK
}
