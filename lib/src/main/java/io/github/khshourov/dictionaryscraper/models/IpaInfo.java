package io.github.khshourov.dictionaryscraper.models;

/**
 * Represents the International Phonetic Alphabet (IPA) information for a word's pronunciation.
 *
 * @param category parts of speech; can be empty.
 * @param ipa The IPA representation of the word, defining its pronunciation using the standardized
 *     phonetic notation.
 * @param audio A URL or path to an audio file demonstrating the word's pronunciation.
 */
public record IpaInfo(String category, String ipa, String audio) {}
