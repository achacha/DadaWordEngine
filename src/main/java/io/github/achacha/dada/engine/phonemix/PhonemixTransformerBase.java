package io.github.achacha.dada.engine.phonemix;

import com.google.common.annotations.VisibleForTesting;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Given a string will transform into phonemix format
 * <p>
 *     Phonemix is a made-up word to represent form of a word converted and compacted into it's phoneme-like
 *     representation that can be used in forward and reverse
 * <p>
 * Algorithm is very loosely based on ideas in Russell Soundex (1918)
 * Original algorithm below was designed in 1993 by Alex Chachanashvili for Dada Wallpaper Poem BBS art project
 * Updates in 2004, 2017 by Alex Chachanashvili
 *
 * Algorithm is designed to be used forwards and backwards to allow sound based matching and rhyme detection
 * Some consonant pairs follow custom remapping based on American English
 * Aggressive compactor removes and compacts based on American English
 */
public abstract class PhonemixTransformerBase implements PhoneticTransformer {
    protected static final Logger LOGGER = LogManager.getLogger(PhonemixTransformerBase.class);
    protected static final char NONE = '_';

    /**
     * Start position inside the text passed into transform
     */
    protected final int startPos;

    /**
     * If to keep the leading vowel when processing, vowels may be dropped in some transformers
     * When withReverse is enabled, this acts as a trailing vowel
     */
    protected final boolean keepLeadingVowel;

    /**
     * If reverse all data before transform
     */
    protected final boolean reverse;

    protected PhonemixTransformerBase(PhoneticTransformerBuilder builder) {
        this.startPos = builder.startPos;
        this.keepLeadingVowel = builder.keepLeadingVowel;
        this.reverse = builder.reverse;
    }

    @Nonnull
    @Override
    public String transform(String text) {
        if (text.length() >= startPos) {
            return Arrays.stream(text.substring(startPos).toLowerCase().split(" "))
                    .map(word -> transformWord(word.toCharArray()))
                    .map(this::processAfterXform)
                    .collect(Collectors.joining(" "));
        }

        return "";
    }

    @Nonnull
    @Override
    public List<Pair<String, Integer>> transformAndIndex(String text) {
        if (text.length() >= startPos) {
            ArrayList<Pair<String, Integer>> list = new ArrayList<>();
            Pattern p = Pattern.compile("\\S+");
            Matcher m = p.matcher(text.substring(startPos).toLowerCase());
            while (m.find()) {
                String word = m.group();
                String transformed = transformWord(word.toCharArray());
                list.add(Pair.of(processAfterXform(transformed), m.start() + startPos));
            }
            return list;
        } else
            return Collections.emptyList();
    }

    /**
     * Apply any rules after xform of each text block
     * @param text String
     * @return String
     */
    @Nonnull
    protected String processAfterXform(@Nonnull String text) {
        if (reverse)
            return StringUtils.reverse(text);
        else
            return text;
    }

    /**
     * Transform a word (does not split sentences)
     *
     * @param word char[]
     * @return Resulting phonemix word
     */
    protected String transformWord(char[] word) {
        LOGGER.debug("=|word={}", word);
        int leftInWord = word.length;
        if (leftInWord > 2)
            transformThree(word);
        if (leftInWord > 1)
            transformTwo(word);
        if (leftInWord > 0)
            transformOne(word);
        return postprocess(word);
    }

    @VisibleForTesting
    void transformThree(char[] s) {
        int i = 0;
        int endPos = s.length - 2;
        while (i < endPos) {
            switch (s[i]) {
                // ght -> __t
                case 'g':
                    if (s[i + 1] == 'h' && s[i + 2] == 't') {
                        LOGGER.debug("> ght detected, i={} s={}", i, s);
                        s[i] = NONE;
                        s[i + 1] = NONE;
                        s[i + 2] = 't';
                        i += 2;
                        LOGGER.debug("< ght->t__, i={} s={}", i, s);
                    }
                    break;

                // sch -> _sk
                case 's':
                    if (s[i + 1] == 'c' && s[i + 2] == 'h') {
                        LOGGER.debug("> sch detected, i={} s={}", i, s);
                        s[i] = NONE;
                        s[i + 1] = 's';
                        s[i + 2] = 'k';
                        i += 2;
                        LOGGER.debug("< sch->sk_, i={} s={}", i, s);
                    }
                    break;
            }
            ++i;
        }
    }

    @VisibleForTesting
    void transformTwo(char[] s) {
        int i = 0;
        int endPos = s.length - 1;
        while (i < endPos) {
            switch (s[i]) {
                case 'c':
                    switch (s[i + 1]) {
                        // c that sounds like k
                        case 'a':
                        case 'o':
                        case 'u':
                            LOGGER.debug(" > c sounds like k, i={} s={}", i, s);
                            s[i] = 'k';
                            LOGGER.debug(" < c->k, i={} s={}", i, s);
                            break;

                        // c? sounds like k
                        case 'c':
                        case 'k':
                            LOGGER.debug(" > c? sounds like k, i={} s={}", i, s);
                            s[i] = NONE;
                            s[i+1] = 'k';
                            LOGGER.debug(" < c?->_k, i={} s={}", i, s);
                            ++i;
                            break;


                        // c that sounds like s
                        case 'e':
                        case 'i':
                            LOGGER.debug(" > c sounds like s, i={} s={}", i, s);
                            s[i] = 's';
                            LOGGER.debug(" < c->s, i={} s={}", i, s);
                            break;

                        // ch -> C
                        case 'h':
                            LOGGER.debug(" > ch detected, i={} s={}", i, s);
                            s[i] = '_';
                            s[i+1] = 'C';
                            ++i;
                            LOGGER.debug(" < ch->_C, i={} s={}", i, s);
                            break;
                    }
                    break;

                case 's':
                    switch (s[i + 1]) {
                        // sh -> S
                        case 'h':
                            LOGGER.debug(" > sh detected, i={} s={}", i, s);
                            s[i] = NONE;
                            s[i+1] = 'S';
                            LOGGER.debug(" < sh->_S, i={} s={}", i, s);
                            ++i;
                            break;
                    }
                    break;

                case 'g':
                    switch (s[i + 1]) {
                        // gh -> f
                        case 'h':
                            LOGGER.debug(" > gh sounds like f, i={} s={}", i, s);
                            s[i] = NONE;
                            s[i + 1] = 'f';
                            ++i;
                            LOGGER.debug(" < gh->_f, i={} s={}", i, s);
                            break;

                        // gn -> n
                        case 'n':
                            LOGGER.debug(" > gn sounds like n, i={} s={}", i, s);
                            s[i] = NONE;
                            s[i + 1] = 'n';
                            ++i;
                            LOGGER.debug(" < gn->_n, i={} s={}", i, s);
                            break;
                    }
                    break;

                case 'k':
                    switch (s[i + 1]) {
                        // kn -> n
                        case 'n':
                            LOGGER.debug(" > kn sounds like n, i={} s={}", i, s);
                            s[i] = NONE;
                            s[i + 1] = 'n';
                            ++i;
                            LOGGER.debug(" < kn->_n, i={} s={}", i, s);
                            break;
                    }
                    break;

                case 'z':
                    // zz -> tz
                    switch (s[i + 1]) {
                        case 'z':
                            LOGGER.debug(" > zz detected, i={} s={}", i, s);
                            s[i] = 't';
                            ++i;
                            LOGGER.debug(" < zz->tz, at i={} s={}", i, s);
                            break;

                        // zh -> Z sounds like z
                        case 'h':
                            LOGGER.debug(" > zh sounds like z, i={} s={}", i, s);
                            s[i] = '_';
                            s[i+1] = 'z';
                            LOGGER.debug(" < zh->_z, i={} s={}", i, s);
                            break;
                    }
                    break;

                case 'a':
                case 'o':
                    // au -> O, ou -> O
                    switch (s[i + 1]) {
                        case 'u':
                            LOGGER.debug(" > au detected, i={} s={}", i, s);
                            s[i] = NONE;
                            s[i + 1] = 'O';
                            ++i;
                            LOGGER.debug(" < au->O_, i={} s={}", i, s);
                            break;
                    }
                    break;

                case 'e':
                    // eu -> _u
                    switch (s[i + 1]) {
                        case 'u':
                            LOGGER.debug(" > eu detected, i={} s={}", i, s);
                            s[i] = NONE;
                            s[i + 1] = 'u';
                            ++i;
                            LOGGER.debug(" < eu->_u, i={} s={}", i, s);
                            break;
                    }
                    break;

                case 'w':
                    // wh -> w
                    switch (s[i + 1]) {
                        case 'h':
                            LOGGER.debug(" > wh detected, i={} s={}", i, s);
                            s[i] = NONE;
                            s[i + 1] = 'w';
                            ++i;
                            LOGGER.debug(" < wh->w_, i={} s={}", i, s);
                            break;

                        case 'r':
                            LOGGER.debug(" > wr detected, i={} s={}", i, s);
                            s[i] = NONE;
                            s[i + 1] = 'r';
                            ++i;
                            LOGGER.debug(" < wr->r_, i={} s={}", i, s);
                            break;
                    }
                    break;

                case 't':
                    // th -> z
                    switch (s[i + 1]) {
                        case 'h':
                            LOGGER.debug(" > th detected, i={} s={}", i, s);
                            s[i] = NONE;
                            s[i + 1] = 'z';
                            ++i;
                            LOGGER.debug(" < th->_z, i={} s={}", i, s);
                            break;
                    }
                    break;

                case 'd':
                    // dg -> j
                    switch (s[i + 1]) {
                        case 'g':
                            LOGGER.debug(" > dg detected, i={} s={}", i, s);
                            s[i] = NONE;
                            s[i + 1] = 'j';
                            ++i;
                            LOGGER.debug(" < dg->_j, i={} s={}", i, s);
                            break;
                    }
                    break;

                default:
                    if (s[i] == s[i + 1]) {
                        LOGGER.debug(" > Duplicate detected, i={} s={}", i, s);
                        // Compact duplicate letters but removing the first of the two and continuing
                        s[i] = NONE;
                        ++i;
                        LOGGER.debug(" < Duplicate compacted, i={} s={}", i, s);
                    }
            }
            ++i;
        }
    }

    protected void transformOne(char[] s) {
        for (int i = 0; i < s.length; ++i) {
            // Remove vowels and h, does not remove compound vowels
            switch (s[i]) {
                case 'a':
                case 'e':
                case 'i':
                case 'o':
                case 'u':
                case 'h':
                    if (i == 0 && keepLeadingVowel) {
                        LOGGER.debug(" | Keeping leading vowel, i={} s={}", i, s);
                    } else {
                        LOGGER.debug(" < vowel removal, i={} s={}", i, s);
                        s[i] = NONE;
                        LOGGER.debug(" > ?->_, i={} s={}", i, s);
                    }
                    break;

                case 'q':
                    LOGGER.debug(" < q detected, i={} s={}", i, s);
                    s[i] = 'k';
                    LOGGER.debug(" > q->k, i={} s={}", i, s);
                    break;
            }
        }
    }

    /**
     * Post processing and compacting
     *
     * @param sentence char[]
     * @return String
     */
    protected String postprocess(char[] sentence) {
        StringBuilder str = new StringBuilder(sentence.length);

        // Compact by removing duplicates and NONE
        char lastChar = 0;
        for (char value : sentence)
            if (value != NONE && value != lastChar && CharUtils.isAsciiAlphanumeric(value)) {
                str.append(value);
                lastChar = value;
            }

        return str.toString();
    }
}
