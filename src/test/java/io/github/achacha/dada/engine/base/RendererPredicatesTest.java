package io.github.achacha.dada.engine.base;

import io.github.achacha.dada.engine.builder.SentenceRendererBuilder;
import io.github.achacha.dada.test.GlobalTestData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class RendererPredicatesTest {

    @Test
    public void truePercent() {
        SentenceRendererBuilder sr = new SentenceRendererBuilder(GlobalTestData.WORD_DATA)
                .nounBuilder()
                    .withFallback("-should-always-happen-", RendererPredicates.trueIfPercent(100))
                    .build();

        for (int i=0; i<100; ++i) {
            assertEquals("-should-always-happen-", sr.execute());   // 100 tries
        }
    }

    @Test
    public void trueProbability() {
        SentenceRendererBuilder sr = new SentenceRendererBuilder(GlobalTestData.WORD_DATA)
                .nounBuilder()
                .withFallback("-should-never-happen-", RendererPredicates.trueIfProbability(0.0))
                .build();

        for (int i=0; i<100; ++i) {
            assertNotEquals("-should-never-happen-", sr.execute());   // 100 tries
        }
    }

    @Test
    public void trueAlways() {
        SentenceRendererBuilder sr = new SentenceRendererBuilder(GlobalTestData.WORD_DATA)
                .nounBuilder()
                .withFallback("-should-always-happen-", RendererPredicates.trueAlways())
                .build();

        assertEquals("-should-always-happen-", sr.execute());
    }

    @Test
    public void trueNever() {
        SentenceRendererBuilder sr = new SentenceRendererBuilder(GlobalTestData.WORD_DATA)
                .nounBuilder()
                .withFallback("-should-never-happen-", RendererPredicates.falseAlways())
                .build();

        assertNotEquals("-should-never-happen-", sr.execute());
    }
}