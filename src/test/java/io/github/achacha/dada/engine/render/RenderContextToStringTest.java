package io.github.achacha.dada.engine.render;

import io.github.achacha.dada.engine.data.Noun;
import io.github.achacha.dada.engine.data.SavedWord;
import io.github.achacha.dada.engine.data.Text;
import io.github.achacha.dada.engine.data.WordData;
import io.github.achacha.dada.engine.data.WordsByType;
import io.github.achacha.dada.integration.tags.GlobalData;
import io.github.achacha.dada.test.GlobalTestData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RenderContextToStringTest {
    @BeforeAll
    public static void beforeClass() {
        GlobalData.setWordData(GlobalTestData.WORD_DATA);
    }

    @Test
    public void testAttribute() {
        RenderContextToString<Text> context = new RenderContextToString<>(WordsByType.empty());
        assertNull(context.getAttribute("lump"));

        context.setAttribute("lump", new SavedWord(new Text("loaf"), ""));
        assertEquals("loaf", context.getAttribute("lump").toString());

        context.setAttribute("lump", null);
        assertNull(context.getAttribute("lump"));
    }

    @Test
    public void getWriter() throws IOException {
        RenderContextToString<Text> context = new RenderContextToString<>(WordsByType.empty());
        context.getWriter().write("ThisIsATest");
        assertEquals("ThisIsATest", context.getWriter().toString());
    }

    @Test
    public void switchWordData() {
        WordData wordData = new WordData("resource:/data/test_parser");
        RenderContextToString<Noun> context = new RenderContextToString<>(wordData.getNouns());
        assertEquals(wordData.getNouns(), context.getWords());

        context.setWords(GlobalData.getWordData().getNouns());
        assertEquals(GlobalData.getWordData().getNouns(), context.getWords());
    }

    @Test
    public void useExternalData() throws IOException {
        HashMap<String, SavedWord> data = new HashMap<>();
        data.put("saved_word", new SavedWord(new Text("tree"), ""));

        try (
                StringWriter sw = new StringWriter()
        ) {
            RenderContextToString<Noun> context = new RenderContextToString<>(
                    GlobalData.getWordData().getNouns(),
                    data,
                    sw
            );

            NounRenderer nrender = new NounRenderer(context);
            nrender.setForm(Noun.Form.plural);
            nrender.setArticle(ArticleMode.a);
            nrender.setCapsMode(CapsMode.words);
            assertEquals("A Nouns", nrender.execute());

            NounRenderer saved = new NounRenderer(context);
            saved.setLoadKey("saved_word");
            saved.setCapsMode(CapsMode.words);
            assertEquals("Noun", saved.execute());   // Saved word is not a Noun, random one will be generated
        }
    }
}