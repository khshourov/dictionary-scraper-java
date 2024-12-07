package com.github.khshourov.dictionaryscraper.models;

import com.github.khshourov.dictionaryscraper.interfaces.Source;
import com.google.gson.Gson;

public record DictionaryWord(Source source, String searchWord, DictionaryEntry entry) {
  public String toJson() {
    return (new Gson()).toJson(this);
  }
}
