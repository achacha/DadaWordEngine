package io.github.achacha.dada.engine.render;

import io.github.achacha.dada.engine.data.Conjunction;
import io.github.achacha.dada.engine.data.WordData;
import io.github.achacha.dada.engine.hyphen.HyphenData;
import io.github.achacha.dada.integration.tags.GlobalData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConjunctionRendererTest {
    @BeforeAll
    public static void beforeClass() {
        GlobalData.setWordData(new WordData("resource:/data/test"));
        GlobalData.setHypenData(new HyphenData());
    }

    @Test
    public void testInternals() {
        ConjunctionRenderer tag = new ConjunctionRenderer();
        assertNotNull(tag.toString());
        assertEquals(Conjunction.Form.none.name(), tag.getFormName());

        tag.setForm("none");
        assertEquals(Conjunction.Form.none.name(), tag.getFormName());

        tag.setForm("invalid");
        assertEquals(Conjunction.Form.none.name(), tag.getFormName());
    }

    @Test
    public void testConjunctionForm() {
        ConjunctionRenderer tag = new ConjunctionRenderer();
        assertEquals("&", tag.execute());
    }

    @Test
    public void testExtendedConstructor() {
        ConjunctionRenderer tag = new ConjunctionRenderer(ArticleMode.the, CapsMode.first, null);
        assertEquals("The &", tag.execute());
    }
}
