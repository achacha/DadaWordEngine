package io.github.achacha.dada.integration.tags;

import javax.el.ELContext;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.el.ExpressionEvaluator;
import javax.servlet.jsp.el.VariableResolver;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class TestJspContext extends JspContext {
    Map<String,Object> backingMap = new HashMap<>();
    TestJspWriter jspWriter = new TestJspWriter();

    /**
     * Backing Map used to intercept/mock getter/setter calls for attributes
     * @return Map of String to Object
     */
    public Map<String, Object> getBackingMap() {
        return backingMap;
    }

    public TestJspWriter getTestJspWriter() {
        return jspWriter;
    }

    @Override
    public void setAttribute(String name, Object value) {
        backingMap.put(name, value);
    }

    @Override
    public void setAttribute(String name, Object value, int scope) {
        backingMap.put(name, value);
    }

    @Override
    public Object getAttribute(String name) {
        return backingMap.get(name);
    }

    @Override
    public Object getAttribute(String name, int scope) {
        return backingMap.get(name);
    }

    @Override
    public Object findAttribute(String name) {
        return backingMap.get(name);
    }

    @Override
    public void removeAttribute(String name) {
        backingMap.remove(name);
    }

    @Override
    public void removeAttribute(String name, int scope) {
        backingMap.remove(name);
    }

    @Override
    public int getAttributesScope(String name) {
        return 0;
    }

    /**
     * LEGACY
     * @param scope int
     * @return Enumeration
     */
    @Override
    public Enumeration<String> getAttributeNamesInScope(int scope) {
        return new Vector(backingMap.keySet()).elements();
    }

    @Override
    public JspWriter getOut() {
        return this.jspWriter;
    }

    @Override
    public ExpressionEvaluator getExpressionEvaluator() {
        return null;
    }

    @Override
    public ELContext getELContext() {
        return null;
    }

    @Override
    public VariableResolver getVariableResolver() {
        return null;
    }
}
