package app.chain.document;

import app.chain.BaseChain;
import app.provider.source.ISource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Implements the first link in the chain, reads the file.
 * <p>
 * Note: The parameters in the handleRequest method will be ignored.
 * So because the first class of the chain of responsibilities does not need any parameters.
 */
public class FileDocumentLoader extends BaseChain implements IDocumentLoader {
    private static final Logger logger = LoggerFactory.getLogger(FileDocumentLoader.class);
    private ISource source;
    private List<String> readedLines;

    /**
     * the constructor
     *
     * @param source A list of sources, that is, file paths. Right now it's a single file
     */
    public FileDocumentLoader(ISource source) {
        this.source = source;
    }

    /**
     * The method calls the execution of the main work and then passes control to the following classes.
     * The parameters in the handleRequest method will be ignored.
     * So because the first class of the chain of responsibilities does not need any parameters.
     * @return number of processed elements.
     */
    public int handleRequest(List<?> ignored1, Class<?> ignored2) {
        logger.info("Start uploading documents:");
        source.getSources().forEach(doc -> logger.info("   ->{}", doc));
        if (loadDocument()) {
            logger.info("Successfully loaded document.");
            return super.handleRequest(readedLines, String.class);
        } else {
            logger.info("Failed to load documents.");
            return 0;
        }
    }

    /**
     * Reads a html-document from a file.
     *
     * @return True if there was a successful download.
     */
    @Override
    public boolean loadDocument() {
        if (this.source.getSources() == null || this.source.getSources().isEmpty()) {
            logger.error("The source is empty.");
            return false;
        } else if (this.source.getSources().size() > 1) {
            logger.warn("Too many files in source should be 1, take only the first!");
        }

        if (this.source.getSources().size() >= 1) {
            try {
                readedLines = Files.readAllLines(Paths.get(this.source.getSources().get(0)), StandardCharsets.UTF_8);
            } catch (IOException e) {
                logger.error("File read error: {}", e.getMessage());
                return false;
            }
        }
        return true;
    }
}
