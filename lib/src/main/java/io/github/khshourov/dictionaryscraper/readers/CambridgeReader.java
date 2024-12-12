package io.github.khshourov.dictionaryscraper.readers;

import io.github.khshourov.dictionaryscraper.enums.ReadingPurpose;
import io.github.khshourov.dictionaryscraper.interfaces.Reader;
import io.github.khshourov.dictionaryscraper.models.ReaderResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.zip.GZIPInputStream;

public class CambridgeReader extends Reader {

  public CambridgeReader(String baseUri) {
    this.baseUri = baseUri;
  }

  @Override
  public ReaderResponse read(String word, ReadingPurpose purpose) throws IOException {
    String section = purpose == ReadingPurpose.MEANING ? "dictionary" : "pronunciation";
    String url = String.format("%s/%s/english/%s", this.baseUri, section, word);

    try (HttpClient client =
        HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(60)).build()) {
      HttpRequest httpRequest =
          HttpRequest.newBuilder()
              .GET()
              .uri(URI.create(url))
              .header(
                  "User-Agent",
                  "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:131.0) Gecko/20100101 Firefox/131.0")
              .header(
                  "Accept",
                  "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/png,image/svg+xml,*/*;q=0.8")
              .header("Accept-Language", "en-US,en;q=0.5")
              .header("Accept-Encoding", "gzip, deflate, br, zstd")
              .header("Referer", "https://www.google.com/")
              .header("Upgrade-Insecure-Requests", "1")
              .header("Sec-Fetch-Dest", "document")
              .header("Sec-Fetch-Mode", "navigate")
              .header("Sec-Fetch-Site", "cross-site")
              .header("Sec-Fetch-User", "?1")
              .build();

      HttpResponse<InputStream> response =
          client.send(httpRequest, HttpResponse.BodyHandlers.ofInputStream());

      InputStream inputStream = response.body();
      String encoding = response.headers().firstValue("Content-Encoding").orElse("");

      if ("gzip".equals(encoding)) {
        inputStream = new GZIPInputStream(inputStream);
      }

      String responseBody = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

      return new ReaderResponse(url, responseBody);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new IOException(e);
    } catch (Exception e) {
      throw new IOException(e);
    }
  }
}
