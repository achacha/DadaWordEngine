package io.github.achacha.dada.integration.tags;

import jakarta.servlet.jsp.JspWriter;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class TestJspWriter extends JspWriter {
    protected StringWriter sw;
    protected PrintWriter backingWriter;

    public TestJspWriter() {
        this(1024, true);
    }

    /**
     * Protected constructor.
     *
     * @param bufferSize the size of the buffer to be used by the JspWriter
     * @param autoFlush
     */
    protected TestJspWriter(int bufferSize, boolean autoFlush) {
        super(bufferSize, autoFlush);
        sw = new StringWriter(this.bufferSize);
        backingWriter = new PrintWriter(sw, this.autoFlush);
    }

    /**
     * Direct access to the data that Backing PrintWriter will write to
     * @return StringWriter used in Backing PrintWriter
     */
    public StringWriter getBackingSw() {
        return sw;
    }

    /**
     * @return Backing PrintWriter (contains Backing StringWriter)
     */
    public PrintWriter getBackingWriter() {
        return backingWriter;
    }

    @Override
    public void newLine() throws IOException {
        backingWriter.println();
    }

    @Override
    public void print(boolean b) throws IOException {
        backingWriter.print(b);
    }

    @Override
    public void print(char c) throws IOException {
        backingWriter.print(c);
    }

    @Override
    public void print(int i) throws IOException {
        backingWriter.print(i);
    }

    @Override
    public void print(long l) throws IOException {
        backingWriter.print(l);
    }

    @Override
    public void print(float f) throws IOException {
        backingWriter.print(f);
    }

    @Override
    public void print(double d) throws IOException {
        backingWriter.print(d);
    }

    @Override
    public void print(char[] s) throws IOException {
        backingWriter.print(s);
    }

    @Override
    public void print(String s) throws IOException {
        backingWriter.print(s);
    }

    @Override
    public void print(Object obj) throws IOException {
        backingWriter.print(obj);
    }

    @Override
    public void println() throws IOException {
        backingWriter.println();
    }

    @Override
    public void println(boolean x) throws IOException {
        backingWriter.println(x);
    }

    @Override
    public void println(char x) throws IOException {
        backingWriter.println(x);
    }

    @Override
    public void println(int x) throws IOException {
        backingWriter.println(x);
    }

    @Override
    public void println(long x) throws IOException {
        backingWriter.println(x);
    }

    @Override
    public void println(float x) throws IOException {
        backingWriter.println(x);
    }

    @Override
    public void println(double x) throws IOException {
        backingWriter.println(x);
    }

    @Override
    public void println(char[] x) throws IOException {
        backingWriter.println(x);
    }

    @Override
    public void println(String x) throws IOException {
        backingWriter.println(x);
    }

    @Override
    public void println(Object x) throws IOException {
        backingWriter.println(x);
    }

    @Override
    public void clear() throws IOException {
        sw = new StringWriter();
        backingWriter = new PrintWriter(sw);
    }

    @Override
    public void clearBuffer() throws IOException {
        sw = new StringWriter(this.bufferSize);
        backingWriter = new PrintWriter(sw, this.autoFlush);
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        backingWriter.write(cbuf, off, len);
    }

    @Override
    public void flush() throws IOException {
        backingWriter.flush();
    }

    @Override
    public void close() throws IOException {
        backingWriter.close();
    }

    @Override
    public int getRemaining() {
        return 0;
    }
}
