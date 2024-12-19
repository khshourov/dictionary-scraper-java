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
  /**
   * Denotes that the purpose of reading is to understand the pronunciation of a word. Represents
   * the specific {@code ReadingPurpose} that focuses on retrieving or studying the phonetic
   * representation of a dictionary entry.
   */
  PRONUNCIATION("pronunciation"),
  /**
   * Indicates that the reading purpose is to understand or learn the meaning of a word. Represents
   * the specific {@code ReadingPurpose} that focuses on retrieving or studying the definition or
   * context associated with a dictionary entry.
   */
  MEANING("meaning"),
  /**
   * Represents the intent of learning both pronunciation and meaning of a word. This {@code
   * ReadingPurpose} combines the purposes of understanding how a word is pronounced and its
   * definition or context.
   */
  BOTH("both");

  private String value;

  ReadingPurpose(String value) {
    this.value = value;
  }

  /**
   * Retrieves the string representation of the reading purpose or value associated with the
   * enumeration constant.
   *
   * @return the string value corresponding to the reading purpose of the enumeration constant
   */
  public String getValue() {
    return this.value;
  }
}
