package com.github.khshourov.dictionaryscraper.interfaces;

import com.github.khshourov.dictionaryscraper.enums.ReadingPurpose;
import com.github.khshourov.dictionaryscraper.models.ReaderResponse;

public interface Reader {
  ReaderResponse read(String word, ReadingPurpose purpose);
}
