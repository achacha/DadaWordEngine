package org.achacha.dada.engine.hyphen;

import com.google.common.base.Preconditions;
import org.achacha.dada.engine.base.FileHelper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Hyphenation algorithm based on C/C++ project at https://github.com/hunspell/hyphen
 * Algorithms were re-written in java
 *
 * Data from: https://raw.githubusercontent.com/hyphenation/tex-hyphen/master/hyph-utf8/tex/generic/hyph-utf8/patterns/txt/hyph-en-us.pat.txt
 */
public class HyphenData {
    private static final Logger LOGGER = LogManager.getLogger(HyphenData.class);

    private static final char DELIMITER = '.';
    private static final int MIN_HYPHENATE_SIZE = 2;  // Any word less than this is not hyphenated

    /**
     * Map starting letter bucket to replacement map
     */
    private Map<String,String> lookupFromStart;
    private List<Map<String,String>> lookupByLetter;

    /**
     * Exceptions
     * Map of word to hyphenated form
     */
    private Map<String,String> exceptions = new HashMap<>();

    /**
     * Load hyphen data by base path
     * Will load .patterns.txt and .exceptions.txt
     * .patterns.txt is the TeX pattern file for the algorithm
     * .exceptions.txt is a a=b style file with exceptions to the algorithm
     * @param basePath String to load from
     */
    public HyphenData(String basePath) {
        // Allocate lookup maps
        lookupFromStart = new HashMap<>();

        lookupByLetter = new ArrayList<>(26);
        for (int i=0; i<26; ++i)
            lookupByLetter.add(new HashMap<>());

        loadPatterns(basePath+".patterns.txt");
        loadExceptions(basePath+".exceptions.txt");
    }

    private void loadPatterns(String path) {
        try (InputStream is = FileHelper.getStreamFromPath(path)) {
            if (is != null) {
                LineIterator it = IOUtils.lineIterator(is, Charset.defaultCharset());
                while (it.hasNext()) {
                    String value = it.nextLine();
                    if (value.startsWith("#")) {
                        LOGGER.debug("Skipping comment: {}", value);
                        continue;
                    }

                    final Map<String,String> bucket;
                    char first = value.charAt(0);
                    String key = value.replaceAll("[\\d]*", "");
                    if (first == DELIMITER) {
                        bucket = lookupFromStart;
                        key = key.substring(1);
                    }
                    else if (Character.isDigit(first)) {
                        int pos = 0;
                        while (Character.isDigit(first)) {
                            first = value.charAt(++pos);
                        }
                        bucket = lookupByLetter.get(first - 'a');
                    }
                    else if (Character.isLowerCase(first)) {
                        bucket = lookupByLetter.get(first - 'a');
                    }
                    else {
                        throw new RuntimeException("Invalid data detected `{}`, must be _, digit or lowercase letter: "+value);
                    }

                    // The key is just the letter set that we start comparing the word with
                    bucket.put(key, fixValue(value));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read hyphen data file: "+path, e);
        }
    }

    private void loadExceptions(String path) {
        try (InputStream is = FileHelper.getStreamFromPath(path)) {
            if (is != null) {
                LineIterator it = IOUtils.lineIterator(is, Charset.defaultCharset());
                while (it.hasNext()) {
                    String value = it.nextLine();
                    if (value.startsWith("#")) {
                        LOGGER.debug("Skipping comment: {}", value);
                        continue;
                    }

                    String[] pair = value.split("=");
                    exceptions.put(StringUtils.trim(pair[0]), StringUtils.trim(pair[1]));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read hyphen data file: "+path, e);
        }
    }

    /**
     * Make value easy to handle during processing, every 2 letters should have a number between them (padded with 0)
     * 1. Remove leading _
     * 2. Add 0 between consecutive letters if there is no number there already
     *
     * @param value Input value
     * @return Fixed value
     */
    String fixValue(String value) {
        StringBuilder buffer = new StringBuilder(value.length() * 2);

        // Skip over leading .
        int i = value.charAt(0) == DELIMITER ? 1 : 0;
        char lastChar = value.charAt(i++);
        while (i < value.length()) {
            char currentChar = value.charAt(i);
            if (Character.isLowerCase(currentChar) && Character.isLowerCase(lastChar)) {
                // Need to insert a 0 between 2 letters
                buffer.append(lastChar);
                buffer.append('0');
            }
            else if (Character.isDigit(currentChar) && Character.isLowerCase(lastChar)) {
                buffer.append(lastChar);
            }
            else if (Character.isLowerCase(currentChar) && Character.isDigit(lastChar)) {
                buffer.append(lastChar);
            }
            lastChar = currentChar;
            ++i;
        }
        if (lastChar != '\0')
            buffer.append(lastChar);

        return buffer.toString();
    }

    /**
     * Based on implementation of Knuth's hyphenation algorithm
     *
     * Given a word setup the processing array
     * "abc" becomes ['a', '0', 'b', '0', 'c', 0] before processing
     *
     * @param wordToProcess String word, not empty, in lower case, any non-lower case or unrecognized letters will be treated as whitespace and ignored
     * @return Syllable count
     */
    public int countSyllables(String wordToProcess) {
        if (wordToProcess.isEmpty())
            return 0;
        if (wordToProcess.length() == 1)
            return 1;   // One letter word defaults to 1 syllable

        // Check exceptions first
        if (exceptions.containsKey(wordToProcess)) {
            return StringUtils.countMatches(exceptions.get(wordToProcess), '-') + 1;
        }

        // Process word
        int syllables = 1;
        char[] proc = processHypens(wordToProcess);
        for (int i=0; i<proc.length; ++i) {
            char c = proc[i];
            if (c=='1' || c=='3' || c=='5' || c== '7' || c=='9')
                ++syllables;
        }

        LOGGER.debug("Syllable count word={} proc={} syllables={}", wordToProcess, proc, syllables);
        return syllables;
    }

    /**
     * Hyphenate word
     * @param wordToProcess Word to process
     * @return Hyphenated word
     */
    public String hyphenateWord(String wordToProcess) {
        if (wordToProcess.length() <= MIN_HYPHENATE_SIZE)
            return wordToProcess;

        // Check exceptions first
        if (exceptions.containsKey(wordToProcess))
            return exceptions.get(wordToProcess);

        // Process word
        StringBuilder buffer = new StringBuilder();
        char[] proc = processHypens(wordToProcess);
        for (int i=0; i<proc.length; i+=2) {
            buffer.append(proc[i]);
            char c = proc[i+1];
            // Do not hyphenate right after first letter or right before last letter
            if (c=='1' || c=='3' || c=='5' || c== '7' || c=='9')
                buffer.append('-');
        }
        return buffer.toString();
    }

    /**
     * Process word into hyphenation array
     * See https://github.com/hunspell/hyphen/blob/master/README.hyphen
     * @param wordToProcess Word to process, non-lowercase are ignored
     * @return char[] array contains letter followed by hyphen weight, odd weight is a hyphen
     */
    protected char[] processHypens(String wordToProcess) {
        Preconditions.checkState(wordToProcess.length() > MIN_HYPHENATE_SIZE, wordToProcess);

        LOGGER.debug("Processing word: {}", wordToProcess);
        String word = wordToProcess+DELIMITER;
        char[] proc = new char[word.length() * 2 - 2];
        for (int i=0; i<wordToProcess.length(); ++i) {
            proc[2 * i] = wordToProcess.charAt(i);
            proc[2 * i + 1] = '0';
        }

        // Apply first character patterns
        Optional<String> startKeyFound = lookupFromStart.keySet().stream().filter(word::startsWith).findFirst();
        if (startKeyFound.isPresent()) {
            String value = lookupFromStart.get(startKeyFound.get());
            applyValue(0, proc, value);
        }

        // Apply rest of the word
        for (int i=1; i<wordToProcess.length(); ++i) {
            char c = wordToProcess.charAt(i);
            if (Character.isLowerCase(c)) {
                //LOGGER.debug("Processing letter `{}`", c);
                Map<String, String> bucket = lookupByLetter.get(c - 'a');
                final int index = i;
                Optional<String> keyFound = bucket.keySet().stream().filter(key -> wordToProcess.indexOf(key, index) == index).findFirst();
                if (keyFound.isPresent()) {
                    String value = bucket.get(keyFound.get());
                    LOGGER.debug("Found value=`{}` for key=`{}`", value, keyFound);
                    applyValue(i * 2, proc, value);
                }
            }
        }

        // By default hyphen after first letter and before/after last letter to be ignored
        proc[1] = '0';
        proc[proc.length - 1] = '0';
        proc[proc.length - 3] = '0';

        return proc;
    }

    /**
     * Apply hyphenation value to processing array
     * Letters should match and numbers are only applied if greater than what is there
     *
     * @param pos start position in the processing array (which is 2 * word_position)
     * @param proc processing array
     * @param value value to apply
     */
    void applyValue(int pos, char[] proc, String value) {
        LOGGER.debug("Applying {} to proc={} at position={}", value, proc, pos);

        int valueIndex = 0;
        if (Character.isDigit(value.charAt(0)) && pos > 0) {
            // If leading value is a number apply it before the current letter
            // If this is at the start of the word, ignore this number
            if (proc[pos-1] < value.charAt(0))
                proc[pos-1] = value.charAt(0);

            valueIndex = 1;
        }

        // At this point value start has to match proc at startpos of we have something wrong
        if (LOGGER.isDebugEnabled()) {
            // Make sure that we are actually starting on the letter
            Preconditions.checkState(value.charAt(valueIndex) == proc[pos], "Start letters need to match proc={} value={}", proc, value);
        }

        // Apply value to proc
        while (valueIndex < value.length() && pos < proc.length) {
            char valueChar = value.charAt(valueIndex);
            if (LOGGER.isDebugEnabled()) {
                if (Character.isLowerCase(valueChar))
                    Preconditions.checkState(valueChar == proc[pos], "Letter in value does not align, proc={} value={}", proc, value);
            }
            if (proc[pos] < valueChar)
                proc[pos] = valueChar;

            ++valueIndex;
            ++pos;
        }

        LOGGER.debug("Applied  {} to proc={}", value, proc);
    }
}
