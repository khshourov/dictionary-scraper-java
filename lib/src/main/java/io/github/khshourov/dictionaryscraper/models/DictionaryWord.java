package io.github.khshourov.dictionaryscraper.models;

import com.google.gson.Gson;
import io.github.khshourov.dictionaryscraper.interfaces.Source;

public record DictionaryWord(Source source, String searchWord, DictionaryEntry entry) {
  public String toJson() {
    return (new Gson()).toJson(this);
  }
}
