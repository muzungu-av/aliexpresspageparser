package app.writer;

import app.accumulator.ConcurrentSetWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Creates csv data file.
 */
public class CsvFileWriter implements IWriter {
    private static final Logger logger = LoggerFactory.getLogger(CsvFileWriter.class);

    /**
     * Searches for a suitable filename.
     *
     * @return Path with filename.
     */
    private static Path getNewFileName() {
        String tempFileName = "PRODUCTS";
        int count = 0;
        Path path;
        do {
            count++;
            path = buildFileName(tempFileName, count);
        } while (Files.exists(path));
        return path;
    }

    /**
     * Builds the fully qualified filename.
     *
     * @param tempFileName Self file name
     * @param count        Number marker of file
     * @return Object Path with new file name.
     */
    private static Path buildFileName(String tempFileName, int count) {
        return Paths.get(tempFileName.concat(String.valueOf(count)).concat(".csv"));
    }

    /**
     * Writes a file to the file system.
     *
     * @param list Lines wich need to be written.
     */
    @Override
    public void write(List<String> list) {
        Path out = getNewFileName();
        try {
            Files.write(out, ConcurrentSetWorker.getCollection(), Charset.defaultCharset());
            logger.info("DONE! You can see result file: {}", out.getFileName());
        } catch (IOException e) {
            logger.error("Can't write file: {}", e.getMessage());
        }
    }
}
