package io.github.khshourov.dictionaryscraper.models;

import io.github.khshourov.dictionaryscraper.enums.Region;
import java.util.List;
import java.util.Map;

/**
 * Represents an entry in the dictionary, containing comprehensive details about a word.
 *
 * <p>This record is composed of three main attributes:
 *
 * <p>- `sourceLinks`: A collection of URL links or references from which the data about this entry
 * was obtained. - `ipaListings`: A mapping of regions (e.g., US, UK) to lists of IPA (International
 * Phonetic Alphabet) information describing the pronunciation of the word in those regions. Each
 * IPA entry may include phonetic representations, corresponding audio links, and related
 * categories. - `meanings`: A collection of meanings for the word, categorized by grammatical or
 * contextual usage. Each meaning may include specific definitions and relevant example usages.
 */
public record DictionaryEntry(
    List<String> sourceLinks, Map<Region, List<IpaInfo>> ipaListings, List<WordMeaning> meanings) {}
