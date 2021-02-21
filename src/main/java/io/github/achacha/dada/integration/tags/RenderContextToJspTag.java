package io.github.achacha.dada.integration.tags;

import com.google.common.base.Preconditions;
import io.github.achacha.dada.engine.data.SavedWord;
import io.github.achacha.dada.engine.data.Word;
import io.github.achacha.dada.engine.data.WordsByType;
import io.github.achacha.dada.engine.render.RenderContext;
import jakarta.servlet.jsp.JspContext;
import jakarta.servlet.jsp.tagext.JspTag;

import javax.annotation.Nullable;
import java.io.Writer;

public class RenderContextToJspTag<T extends Word> extends RenderContext<T> {
    /** Context of the JSP page */
    private JspContext jspContext;

    /** Parent of the JSP tag */
    private JspTag parentJspTag;

    public RenderContextToJspTag(WordsByType<T> words) {
        super(words);
    }

    public void setJspContext(JspContext jspContext) {
        this.jspContext = jspContext;
    }

    public JspTag getParentJspTag() {
        return parentJspTag;
    }

    public void setParentJspTag(JspTag parentJspTag) {
        this.parentJspTag = parentJspTag;
    }

    @Override
    public Writer getWriter() {
        return Preconditions.checkNotNull(jspContext).getOut();
    }

    @Nullable
    @Override
    public SavedWord getAttribute(String name) {
        return (SavedWord)Preconditions.checkNotNull(jspContext).getAttribute(name);
    }

    @Override
    public void setAttribute(String name, SavedWord value) {
        Preconditions.checkNotNull(jspContext).setAttribute(name, value);
    }
}
