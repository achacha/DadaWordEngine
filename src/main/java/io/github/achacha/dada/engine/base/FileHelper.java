package io.github.achacha.dada.engine.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FileHelper {
    private static final Logger LOGGER = LogManager.getLogger(FileHelper.class);

    /**
     * Open an input stream from relative path (leading / is ignored)
     * <p>
     * FORMAT:
     * resource:data/default/nouns.csv    - opens resource from data/default/nouns.csv using classloader
     * data/custom/nouns.csv              - opens file with relative path
     *
     * @param path Path of the file to open (with optional resource: for resource files)
     * @return InputStream or null if unable to open stream
     * @throws FileNotFoundException when physical file specified is not found
     */
    @Nullable
    public static InputStream getStreamFromPath(String path) throws FileNotFoundException {
        if (!path.startsWith("resource:")) {
            // Assume physical file path
            LOGGER.debug("Opening file input stream at path={}", path);

            File file = new File(path);
            if (file.canRead())
                return new FileInputStream(path);
            else {
                LOGGER.debug("Unable to read file: {}"+path);
                return null;
            }
        }
        else {
            // Default is relative stream from resource
            String rp = path.substring(9);
            if (rp.charAt(0) == '/')
                rp = rp.substring(1);  // Remove leading /

            LOGGER.debug("Opening resource input stream at path={}", rp);
            return Thread.currentThread().getContextClassLoader().getResourceAsStream(rp);
        }
    }
}
