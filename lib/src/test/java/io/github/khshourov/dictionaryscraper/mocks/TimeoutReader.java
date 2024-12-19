package io.github.khshourov.dictionaryscraper.mocks;

import io.github.khshourov.dictionaryscraper.enums.ReadingPurpose;
import io.github.khshourov.dictionaryscraper.interfaces.Reader;
import io.github.khshourov.dictionaryscraper.models.ReaderResponse;
import java.io.IOException;

/**
 * A mock implementation of the {@code Reader} class designed to simulate a timeout scenario while
 * interacting with a resource. This is used for testing purposes, where the reader always throws an
 * {@code IOException} to indicate a read timeout.
 *
 * <p>The {@code TimeoutReader} extends the {@code Reader} abstract class and overrides the {@code
 * read} method with behavior specific to timeout simulation.
 *
 * <p>Constructor: - {@code TimeoutReader(String baseUri)}: Initializes the reader with a specified
 * base URI setting, which can later be accessed using the base class methods.
 *
 * <p>Method Behavior: - {@code read}: This method always throws an {@code IOException} with the
 * message "Read timeout" regardless of the input parameters, simulating a read operation timing
 * out.
 */
public class TimeoutReader extends Reader {
  /**
   * Sets the baseUri. Note that, provided baseUri needs not to confirm any valid uri. This is just
   * to maintain consistency across all implementations of Reader class.
   *
   * @param baseUri any string
   */
  public TimeoutReader(String baseUri) {
    this.baseUri = baseUri;
  }

  /**
   * Simulates a read operation for the specified word and reading purpose. This implementation
   * always throws an {@code IOException} to represent a read timeout scenario during interaction
   * with a resource.
   *
   * @param word the target word to be read.
   * @param purpose the purpose of reading, specifying the type of data to retrieve (e.g.,
   *     pronunciation, meaning, or both).
   * @return never returns a value as it always throws an {@code IOException}.
   * @throws IOException always thrown to simulate a read timeout scenario.
   */
  @Override
  public ReaderResponse read(String word, ReadingPurpose purpose) throws IOException {
    throw new IOException("Read timeout");
  }
}
