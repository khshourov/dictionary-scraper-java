package io.github.khshourov.dictionaryscraper.models;

import com.google.gson.Gson;
import io.github.khshourov.dictionaryscraper.interfaces.Source;

/**
 * Represents a word retrieved from a dictionary source along with its corresponding dictionary
 * entry.
 *
 * @param source The source from which the dictionary word and its details are fetched.
 * @param searchWord The word that was searched in the dictionary.
 * @param entry The dictionary entry corresponding to the searched word, containing details such as
 *     pronunciations, meanings, and usage examples.
 */
public record DictionaryWord(Source source, String searchWord, DictionaryEntry entry) {
  /**
   * Converts the DictionaryWord record into a JSON.
   *
   * @return JSON string representing the DictionaryWord
   */
  public String toJson() {
    return (new Gson()).toJson(this);
  }
}
