package io.github.khshourov.dictionaryscraper.interfaces;

import io.github.khshourov.dictionaryscraper.enums.ReadingPurpose;
import io.github.khshourov.dictionaryscraper.models.ReaderResponse;
import java.io.IOException;

/**
 * The {@code Reader} abstract class provides a blueprint for classes that are responsible for
 * reading and fetching data from sources, such as dictionaries or other data repositories. Derived
 * classes need to implement the {@code read} method to specify the behavior of fetching the desired
 * content.
 *
 * <p>Key Responsibilities: 1. Manage the base URI that serves as the starting point for resource
 * lookups. 2. Ensure the base URI is properly configured before usage. 3. Provide an abstract
 * method for reading content based on specified parameters.
 *
 * <p>Methods: - {@code getBaseUri()}: Retrieves the base URI associated with the reader. - {@code
 * read(String word, ReadingPurpose purpose)}: Abstract method to be implemented by subclasses for
 * fetching content from the source based on the specified word and purpose. - {@code
 * afterPropertiesSet()}: Ensures that the base URI is properly set and throws an exception if it is
 * null or empty.
 *
 * <p>Subclasses of this abstract class need to provide specific logic for fetching data from
 * various sources.
 */
public abstract class Reader {
  /** the base URI of the online dictionary. */
  protected String baseUri;

  /**
   * Creates a new instance of the {@code Reader} class with no base URI specified. This constructor
   * is typically used when the base URI will be set or defined later.
   *
   * <p>Subclasses of {@code Reader} can utilize this constructor to initialize the abstract reader
   * without providing immediate configuration details for the data source.
   */
  public Reader() {}

  /**
   * Initializes a new instance of the {@code Reader} class with the specified base URI.
   *
   * @param baseUri The base URI to be associated with this reader, typically representing the
   *     starting point for resource lookups.
   */
  public Reader(String baseUri) {
    this.baseUri = baseUri;
  }

  /**
   * Retrieves the base URI associated with the reader.
   *
   * @return the base URI
   */
  public String getBaseUri() {
    return this.baseUri;
  }

  /**
   * Reads data from the online dictionary for the specified word and purpose. Depending on the
   * given purpose, the method returns information related to the word's meaning or pronunciation.
   *
   * @param word The word to be looked up in the online dictionary.
   * @param purpose The purpose of the reading, which determines whether to retrieve meaning or
   *     pronunciation. It must be of type {@link ReadingPurpose}.
   * @return A {@link ReaderResponse} object containing the response URL and data fetched from the
   *     dictionary.
   * @throws IOException If an I/O error occurs during the HTTP request or response processing.
   */
  public abstract ReaderResponse read(String word, ReadingPurpose purpose) throws IOException;

  /**
   * Ensures that baseUri property has been set in the child class.
   *
   * @throws IllegalStateException If baseUri is null or empty.
   */
  public void afterPropertiesSet() {
    if (this.baseUri == null || this.baseUri.trim().isEmpty()) {
      throw new IllegalStateException("reader.baseUri can not be null or empty");
    }
  }
}
