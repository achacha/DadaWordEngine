package org.achacha.dada.engine.phonemix;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AggresivePhonemixTransformerTest {
    @Test
    public void testAgggresiveVowelRemoval() {
        PhoneticTransformer transformer = PhoneticTransformerBuilder
                .builder()
                .withAggresiveTransformer()
                .withIgnoreLeadingVowel()
                .build();

        assertEquals("nf", transformer.transform("Enough"));
        assertEquals("ptz", transformer.transform("pizza"));
        assertEquals("k", transformer.transform("aqua"));
    }
}
