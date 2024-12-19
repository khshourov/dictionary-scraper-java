package io.github.khshourov.dictionaryscraper.models;

/**
 * Represents the International Phonetic Alphabet (IPA) information for a word's pronunciation.
 *
 * <p>This record encapsulates the following details about a word's pronunciation:
 *
 * <p>- `category`: parts of speech; can be empty. - `ipa`: The IPA representation of the word,
 * defining its pronunciation using the standardized phonetic notation. - `audio`: A URL or path to
 * an audio file demonstrating the word's pronunciation.
 */
public record IpaInfo(String category, String ipa, String audio) {}
