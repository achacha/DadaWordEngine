package io.github.achacha.dada.engine.base;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FileHelperTest {
    @Test
    public void testStreamFromPath() throws IOException {
        try (InputStream is = FileHelper.getStreamFromPath("resource:data/test.data")) {
            assertNotNull(is);
        }
    }

}