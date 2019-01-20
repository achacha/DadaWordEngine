package io.github.achacha.dada.engine.render;

import io.github.achacha.dada.engine.data.Adjective;
import io.github.achacha.dada.engine.data.WordData;
import io.github.achacha.dada.engine.hyphen.HyphenData;
import io.github.achacha.dada.integration.tags.GlobalData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AdjectiveRendererTest {
    @BeforeAll
    public static void beforeClass() {
        GlobalData.setWordData(new WordData("resource:/data/test"));
        GlobalData.setHypenData(new HyphenData());
    }

    @Test
    public void testInternals() {
        AdjectiveRenderer tag = new AdjectiveRenderer();
        assertNotNull(tag.toString());
        assertEquals(Adjective.Form.positive.name(), tag.getFormName());
    }

    @Test
    public void renderAdjectiveForms() {
        AdjectiveRenderer tag = new AdjectiveRenderer();

        assertEquals("subtle", tag.execute());

        tag.setForm(Adjective.Form.comparative);
        assertEquals(Adjective.Form.comparative, tag.getForm());
        assertEquals("more subtle", tag.execute());

        tag.setForm(Adjective.Form.superlative);
        assertEquals("most subtle", tag.execute());

        tag.setForm(Adjective.Form.positive);
        assertEquals("positive", tag.getFormName());
        assertEquals("subtle", tag.execute());

        tag.setForm("superlative");
        assertEquals(Adjective.Form.superlative, tag.getForm());
        assertEquals("most subtle", tag.execute());
    }
}
