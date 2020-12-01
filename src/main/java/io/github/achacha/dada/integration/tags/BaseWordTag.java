package io.github.achacha.dada.integration.tags;

import com.google.common.annotations.VisibleForTesting;
import io.github.achacha.dada.engine.base.RendererPredicates;
import io.github.achacha.dada.engine.data.Word;
import io.github.achacha.dada.engine.render.ArticleMode;
import io.github.achacha.dada.engine.render.BaseWordRenderer;
import io.github.achacha.dada.engine.render.CapsMode;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.StringWriter;

public abstract class BaseWordTag<T extends Word, R extends BaseWordRenderer<T>> extends SimpleTagSupport {
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
        // Write body from fragment
        if (jspBody != null) {
            StringWriter sw = new StringWriter();
            try {
                jspBody.invoke(sw);
                String text = sw.toString();
                LOGGER.debug("text={}", text);
                wordRenderer.setFallback(text);
            } catch (Exception e) {
                LOGGER.error("Failed to read body of text element", e);
            }
        }
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
     * @param value String rhyme this word with another word
     * @see #setSave(String)
     */
    public void setRhymeWith(String value) {
        wordRenderer.setRhymeWith(value);
    }

    /**
     * Called by Jasper
     * @param value String converted to int, syllables desired
     */
    public void setSyllables(String value) {
        wordRenderer.setSyllablesDesired(Integer.parseInt(value));
    }

    /**
     * Manually set fallback string
     * This is normally done when body of the tag is read
     * @see #setJspBody(JspFragment)
     * @param fallback String
     */
    @VisibleForTesting
    public void setFallback(String fallback) {
        wordRenderer.setFallback(fallback);
    }

    /**
     * Probability that the fallback should be shown
     * valid range = [0.0,1.0)
     *
     * @param probability String converted to double, must be [0.0,1.0), if not then defaults to always false (i.e. never show fallback)
     */
    public void setFallbackProbability(String probability) {
        if (NumberUtils.isParsable(probability)) {
            double p = Float.parseFloat(probability);
            if (p >= 0.0 && p <= 1.0) {
                wordRenderer.setFallbackPredicate(RendererPredicates.trueIfProbability(p));
                return;
            }
            else {
                LOGGER.debug("Probability must be 0.0 <= p <= 1.0, provided p="+p);
            }
        }
        else {
            LOGGER.debug("Unable to parse fallbackProbability="+probability);
        }
        wordRenderer.setFallbackPredicate(null);
        throw new IllegalArgumentException("Invalid probability specified: probability="+probability);
    }
}
