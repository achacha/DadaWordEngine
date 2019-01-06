package org.achacha.dada.engine.data;

import com.google.common.collect.Multimap;
import org.achacha.dada.engine.phonemix.PhoneticTransformerBuilder;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WordsByTypeTest {
    private WordsByType<Verb> verbs = new WordsByType<>(
            Word.Type.Verb,
            "resource:/data/test/"+ Word.Type.Verb.getTypeName()+".csv",
            PhoneticTransformerBuilder.builder().build(),
            PhoneticTransformerBuilder.builder().withReverse().build());

    @Test
    public void testLoadingVerb() {
        assertEquals("resource:/data/test/"+ Word.Type.Verb.getTypeName()+".csv", verbs.getResourcePath());
        assertEquals(Word.Type.Verb, verbs.getType());
        assertEquals(1, verbs.getWordsData().size());

        Verb verb = verbs.getWordsData().get(0);
        assertEquals("swim", verb.getWord());
        assertEquals("swam", verb.getPast());
        assertEquals("swum", verb.getPastParticiple());
        assertEquals("swims", verb.getSingular());
        assertEquals("swimming", verb.getPresent());
        assertEquals("to swim", verb.getInfinitive());
    }

    @Test
    public void testAllForms() {
        // There are 5 forms to swim (excluding infinitive)
        assertEquals(5, verbs.wordsByText.size());
    }

    /**
     * Process test data and generate the withReverse phonetic -> word multimap
     */
    @Test
    public void testPhonetics() {
        assertEquals(5, verbs.wordsByPhonetic.size());
    }

    @Test
    public void testReversePhonetics() {
        Multimap<String, SavedWord> bucket = verbs.wordBucketsByReversePhonetic.get('m');
        assertNotNull(bucket);
        assertEquals(3, bucket.size());
    }

    @Test
    public void testSave() {
        WordData wordData = new WordData();

        wordData.getWordsByTypeStream().forEach(this::saveWordsAndVerify);
    }

    private void saveWordsAndVerify(WordsByType wordByType) {
        StringWriter sw = new StringWriter();
        BufferedWriter writer = new BufferedWriter(sw);
        try {
            wordByType.writeWords(writer);
        }
        catch(IOException e) {
            throw new RuntimeException("Failed to write", e);
        }
        String result = sw.toString();

        String outputHeader = wordByType.getOutputHeader();
        assertTrue(result.startsWith(outputHeader));

        int count = StringUtils.countMatches(result, "\n");
        assertEquals(wordByType.wordsData.size()+1, count);
    }

}
