package io.github.khshourov.dictionaryscraper.models;

import io.github.khshourov.dictionaryscraper.enums.Region;
import java.util.List;
import java.util.Map;

/**
 * Represents an entry in the dictionary for a specific word, containing its source links, phonetic
 * information, and meanings.
 *
 * @param sourceLinks Collection of links from which the word's dictionary data has been sourced.
 * @param ipaListings A mapping of regions (e.g., US, UK) to their respective lists of International
 *     Phonetic Alphabet (IPA) details. Each IPA entry provides category, pronunciation, and
 *     optional audio.
 * @param meanings A list of word meanings categorized based on grammatical or contextual usage,
 *     including examples.
 */
public record DictionaryEntry(
    List<String> sourceLinks, Map<Region, List<IpaInfo>> ipaListings, List<WordMeaning> meanings) {}
