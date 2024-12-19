package io.github.khshourov.dictionaryscraper.enums;

/**
 * Represents the purpose of reading associated with a dictionary entry or a word.
 *
 * <p>The {@code ReadingPurpose} enum is designed to categorize the intent behind reading words from
 * a dictionary, whether for pronunciation, meaning, or both.
 *
 * <p>Enum Constants: - {@code PRONUNCIATION}: Denotes that the purpose of reading is to understand
 * the pronunciation of the word. - {@code MEANING}: Indicates that the reading purpose is to learn
 * the meaning of the word. - {@code BOTH}: Represents the intent of learning both pronunciation and
 * meaning.
 *
 * <p>Features: - Each enum constant is associated with a specific string representation of the
 * reading purpose. - Provides a method to retrieve the string representation of the reading purpose
 * through the {@code getValue} method.
 */
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
