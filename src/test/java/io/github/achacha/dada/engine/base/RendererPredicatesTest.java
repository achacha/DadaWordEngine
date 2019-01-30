package io.github.achacha.dada.engine.base;

import io.github.achacha.dada.engine.builder.SentenceRendererBuilder;
import io.github.achacha.dada.test.GlobalTestData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RendererPredicatesTest {

    @Test
    public void truePercent() {
        SentenceRendererBuilder sr = new SentenceRendererBuilder(GlobalTestData.WORD_DATA)
                .nounBuilder()
                    .withFallback("-should-always-happen-", RendererPredicates.truePercent(100))
                    .build();

        for (int i=0; i<100; ++i) {
            assertEquals("-should-always-happen-", sr.execute());   // 100 tries
        }
    }
}