package io.github.achacha.dada.integration.tags;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TagSingletonTest {

    @Test
    void testInit() throws IOException {
        TagSingleton.init();

        assertNotNull(TagSingleton.getWordData());
        assertNotNull(TagSingleton.getHypenData());

        TestJspContext jspContext = new TestJspContext();
        NounTag tag = new NounTag();
        tag.setJspContext(jspContext);

        tag.doTag();

        assertTrue(StringUtils.isNotBlank(jspContext.getBackingJspWriter().getBackingSw().toString()));
    }
}