package io.github.achacha.dada.integration.tags;

import com.google.common.base.Preconditions;
import io.github.achacha.dada.engine.data.WordData;
import io.github.achacha.dada.engine.hyphen.HyphenData;

/**
 * Before JSP engine comes up this needs to contain the WordData, HypenData, etc
 *
 * This is usually called when container is starting up and TagSingleton.init() is enough to get good working data
 */
public class TagSingleton {
    /** Default word data path in resource directory */
    public static final String DEFAULT_WORDDATA_BASE_RESOURCE_PATH = "resource:/data/extended2018";
    /** Default hyphen data path is resource directory */
    public static final String DEFAULT_HYPHENDATA_BASE_RESOURCE_PATH = "resource:/data/hyphen";

    private static WordData jspWordDataInstance;
    private static HyphenData jspHypenDataInstance;

    /**
     * Initialize with default data
     * Can also call the individual load calls to customize data
     * @see #loadWordData(String)
     * @see #loadHyphenData(String)
     */
    public static void init() {
        loadWordData(DEFAULT_WORDDATA_BASE_RESOURCE_PATH);
        loadHyphenData(DEFAULT_HYPHENDATA_BASE_RESOURCE_PATH);
    }

    /**
     * Load WordData from resource base path
     * @param baseResoureDir String (e.g.  resource:/data/extended2018)
     */
    public static void loadWordData(String baseResoureDir) {
        jspWordDataInstance = new WordData(baseResoureDir);
    }

    public static void setWordData(WordData jspWordDataInstance) {
        TagSingleton.jspWordDataInstance = jspWordDataInstance;
    }

    public static void loadHyphenData(String baseResourcePath) {
        jspHypenDataInstance = new HyphenData(baseResourcePath);
    }

    public static void setHypenData(HyphenData jspHypenDataInstance) {
        TagSingleton.jspHypenDataInstance = jspHypenDataInstance;
    }


    public static HyphenData getHypenData() {
        Preconditions.checkNotNull(jspHypenDataInstance);
        return jspHypenDataInstance;
    }

    public static WordData getWordData() {
        Preconditions.checkNotNull(jspWordDataInstance, "Must have WordData initialized before tags are instantiated by the container");
        return jspWordDataInstance;
    }

}
