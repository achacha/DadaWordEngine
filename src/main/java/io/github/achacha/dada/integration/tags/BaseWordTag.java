package io.github.achacha.dada.integration.tags;

import io.github.achacha.dada.engine.data.Word;
import io.github.achacha.dada.engine.render.ArticleMode;
import io.github.achacha.dada.engine.render.BaseWordRenderer;
import io.github.achacha.dada.engine.render.CapsMode;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.SimpleTag;
import java.io.IOException;

public abstract class BaseWordTag<T extends Word, R extends BaseWordRenderer<T>> implements SimpleTag {
    protected static final Logger LOGGER = LogManager.getLogger(BaseWordTag.class);

    /** Renderer used by the word type specific tag */
    protected final R wordRenderer;

    BaseWordTag(R wordRenderer) {
        this.wordRenderer = wordRenderer;
    }

    @Override
    public String toString() {
        return wordRenderer.toString();
    }

    @Override
    public void doTag() throws IOException {
        String word = wordRenderer.execute();
        LOGGER.debug("doTag: word={}", word);
        wordRenderer.getRendererContext().getWriter().write(word);
    }

    @Override
    public void setParent(JspTag parent) {
        LOGGER.debug("setParent={}", parent);
        ((RenderContextToJspTag<T>)wordRenderer.getRendererContext()).setParentJspTag(parent);
    }

    @Override
    public JspTag getParent() {
        LOGGER.debug("getParent");
        return ((RenderContextToJspTag<T>)wordRenderer.getRendererContext()).getParentJspTag();
    }

    @Override
    public void setJspContext(JspContext jspContext) {
        LOGGER.debug("setJspContext={}", jspContext);
        ((RenderContextToJspTag<T>)wordRenderer.getRendererContext()).setJspContext(jspContext);
    }

    @Override
    public void setJspBody(JspFragment jspBody) {
        LOGGER.debug("setJspBody={}", jspBody);
        // At this time the tags are defined in TLD as body-content of empty, so this would never get called
        // If this changes the body can be contained in the context
    }

    /**
     * @return Renderer for the word
     */
    public R getWordRenderer() {
        return wordRenderer;
    }

    /**
     * Called by Jasper
     * If word is preceded by article 'a' or 'the' or 'none' for no  article
     * @param value String of ArticleMode
     * @see io.github.achacha.dada.engine.render.ArticleMode
     */
    public void setArticle(String value) {
        try {
            wordRenderer.setArticle(ArticleMode.valueOf(StringUtils.trim(value.toLowerCase())));
        }
        catch(IllegalArgumentException e) {
            LOGGER.warn("Invalid ArticleMode is ignored, value="+value);
        }
    }

    /**
     * Called by Jasper
     * Form of the word (specific to the Word type being used
     * @param value String name of the form of the word
     */
    public void setForm(String value) {
        wordRenderer.setForm(value);
    }

    /**
     * Called by Jasper
     * Capitalization mode
     * @param value String of CapsMode
     * @see io.github.achacha.dada.engine.render.CapsMode
     */
    public void setCapsMode(String value) {
        try {
            wordRenderer.setCapsMode(CapsMode.valueOf(StringUtils.trim(value.toLowerCase())));
        }
        catch(IllegalArgumentException e) {
            LOGGER.warn("Invalid CapsMode is ignored, value="+value);
        }
    }

    /**
     * Called by Jasper
     * @param value String key to load for this word
     */
    public void setLoad(String value) {
        wordRenderer.setLoadKey(value);
    }

    /**
     * Called by Jasper
     * @param value String Save key
     */
    public void setSave(String value) {
        wordRenderer.setSaveKey(value);
    }

    /**
     * Called by Jasper
     * @param value String rhyme this word to the one saved by the keyword
     * @see #setSave(String)
     */
    public void setRhyme(String value) {
        wordRenderer.setRhymeKey(value);
    }


    /**
     * Called by Jasper
     * @param value String converted to int, syllables desired
     */
    public void setSyllables(String value) {
        wordRenderer.setSyllablesDesired(Integer.valueOf(value));
    }
}
