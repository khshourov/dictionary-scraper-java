package io.github.khshourov.dictionaryscraper.mocks;

import io.github.khshourov.dictionaryscraper.enums.ReadingPurpose;
import io.github.khshourov.dictionaryscraper.interfaces.Reader;
import io.github.khshourov.dictionaryscraper.models.ReaderResponse;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * MockCambridgeReader is a mock implementation of the {@code Reader} abstract class, primarily
 * created for testing purposes. It reads locally stored HTML files representing dictionary entries
 * for given words and reading purposes.
 *
 * <p>Constructor: - {@code MockCambridgeReader(String baseUri)}: Initializes the reader with the
 * specified base URI which serves as the base path for generating resource links.
 *
 * <p>Method Behavior: - {@code read(String word, ReadingPurpose purpose)}: Attempts to read a
 * locally stored HTML file corresponding to the requested word and reading purpose. The method
 * retrieves the file based on a predefined directory structure and returns a {@code ReaderResponse}
 * containing the resource link and file content. If an error occurs during resource retrieval
 * (e.g., missing file, invalid URI), it handles the exception and may return null.
 *
 * <p>Usage Scenario: This class allows for testing dictionary scrapers or other components without
 * relying on external resources by simulating dictionary data from local files.
 */
public class MockCambridgeReader extends Reader {

  /**
   * Sets the baseUri which serves as the base path for generating resource links.
   *
   * @param baseUri the baseUri of the generated resource links
   */
  public MockCambridgeReader(String baseUri) {
    this.baseUri = baseUri;
  }

  /**
   * Reads a locally stored HTML file corresponding to the specified word and reading purpose. The
   * method returns a {@code ReaderResponse}, which contains the resource link and file content. If
   * the resource cannot be located or an error occurs during reading, the method returns {@code
   * null}.
   *
   * @param word the target word to be read from the resource.
   * @param purpose the purpose of reading, indicating the type of data required (e.g.,
   *     pronunciation, meaning, or both).
   * @return a {@code ReaderResponse} object containing the resource link and the content of the
   *     file, or {@code null} if an error occurs.
   * @throws IOException if an I/O error occurs during file reading.
   */
  @Override
  public ReaderResponse read(String word, ReadingPurpose purpose) throws IOException {
    try {
      Path path =
          new File(
                  Objects.requireNonNull(
                          MockCambridgeReader.class
                              .getClassLoader()
                              .getResource(
                                  String.format(
                                      "io/github/khshourov/dictionaryscraper/cambridge/%s/%s.html",
                                      purpose.getValue(), word)))
                      .toURI())
              .toPath();
      return new ReaderResponse(
          String.format("%s/%s/%s", this.baseUri, purpose.getValue(), word),
          Files.readString(path));
    } catch (URISyntaxException e) {
      return null;
    }
  }
}
