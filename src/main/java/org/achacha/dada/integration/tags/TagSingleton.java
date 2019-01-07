package org.achacha.dada.integration.tags;

import com.google.common.base.Preconditions;
import org.achacha.dada.engine.data.WordData;
import org.achacha.dada.engine.hyphen.HyphenData;

/**
 * Before JSP engine comes up this needs to contain the WordData, HypenData, etc
 *
 * This may not be the most efficient way but is generic enough
 * that we can set it during container/app startup
 */
public class TagSingleton {
    private static WordData jspWordDataInstance;
    private static HyphenData jspHypenDataInstance;

    public static WordData getWordData() {
        Preconditions.checkNotNull(jspWordDataInstance, "Must have WordData initialized before tags are instantiated by the container");
        return jspWordDataInstance;
    }

    public static void setWordData(WordData jspWordDataInstance) {
        TagSingleton.jspWordDataInstance = jspWordDataInstance;
    }

    public static HyphenData getHypenData() {
        Preconditions.checkNotNull(jspHypenDataInstance);
        return jspHypenDataInstance;
    }

    public static void setHypenData(HyphenData jspHypenDataInstance) {
        TagSingleton.jspHypenDataInstance = jspHypenDataInstance;
    }
}
