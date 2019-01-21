package io.github.achacha.dada.engine.builder;

import io.github.achacha.dada.engine.data.WordData;
import io.github.achacha.dada.integration.tags.GlobalData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class SentenceRendererBuilderTest {
    @Test
    public void testConversionFromSentence() {
        Sentence sentence = new Sentence("resource:/data/test")
                .parse("noun ADVERBLY swum & swim me noun");
        assertEquals(13, sentence.getWords().size());

        SentenceRendererBuilder renderers = new SentenceRendererBuilder(sentence);
        renderers.setWordData(new WordData(GlobalData.DADA2018_WORDDATA_BASE_RESOURCE_PATH));
        assertEquals(7, renderers.getRenderers().size());

        assertTrue(!renderers.execute().isEmpty());
    }
}