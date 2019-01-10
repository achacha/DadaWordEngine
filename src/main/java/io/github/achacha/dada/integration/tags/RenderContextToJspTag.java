package io.github.achacha.dada.integration.tags;

import com.google.common.base.Preconditions;
import io.github.achacha.dada.engine.data.Word;
import io.github.achacha.dada.engine.data.WordsByType;
import io.github.achacha.dada.engine.render.RenderContext;

import javax.annotation.Nullable;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.tagext.JspTag;
import java.io.Writer;

public class RenderContextToJspTag<T extends Word> extends RenderContext<T> {
    /** Context of the JSP page */
    private JspContext jspContext;

    /** Parent of the JSP tag */
    private JspTag parentJspTag;

    public RenderContextToJspTag(WordsByType<T> words) {
        super(words);
    }

    public JspContext getJspContext() {
        return jspContext;
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
    public Object getAttribute(String name) {
        return Preconditions.checkNotNull(jspContext).getAttribute(name);
    }

    @Override
    public void setAttribute(String name, Object value) {
        Preconditions.checkNotNull(jspContext).setAttribute(name, value);
    }
}
