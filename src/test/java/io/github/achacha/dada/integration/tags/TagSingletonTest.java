package io.github.achacha.dada.integration.tags;

import io.github.achacha.dada.engine.data.WordData;
import io.github.achacha.dada.engine.hyphen.HyphenData;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TagSingletonTest {

    @Test
    void testInit() throws IOException {
        GlobalData.init();

        assertNotNull(GlobalData.getWordData());
        assertNotNull(GlobalData.getHyphenData());

        TestJspContext jspContext = new TestJspContext();
        NounTag tag = new NounTag();
        tag.setJspContext(jspContext);

        tag.doTag();

        assertTrue(StringUtils.isNotBlank(jspContext.getBackingJspWriter().getBackingSw().toString()));
    }

    @Test
    public void testLazyLoad() {
        WordData wordData1 = GlobalData.getWordData();
        assertNotNull(wordData1);
        assertEquals(wordData1, GlobalData.getWordData());  // No reload is happening

        HyphenData hyphenData1 = GlobalData.getHyphenData();
        assertNotNull(hyphenData1);
        assertEquals(hyphenData1, GlobalData.getHyphenData());  // No reload is happening
    }
}