package app;

import app.provider.IProvider;
import app.provider.ProviderConstructor;
import app.provider.source.ISource;
import app.accumulator.ConcurrentSetWorker;
import app.threads.ThreadBuilder;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import app.writer.CsvFileWriter;
import app.writer.IWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AliParser {
    private static final Logger logger = LoggerFactory.getLogger(AliParser.class);
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private IProvider provider;
    private ISource source;

    public static void main(String[] args) {
        String date = sdf.format(new Date());
        logger.info("Application started. ({})", date);

        AliParser aliParser = new AliParser();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("app");
        String target = resourceBundle.getString("target");
        ProviderConstructor providerConstructor = new ProviderConstructor();
        try {
            aliParser.provider = providerConstructor.getProvider(target);
        } catch (Exception e) {
            logger.error("Couldn't get a provider. {}", e.getMessage());
            System.exit(1);
        }
        logger.info("Provider built.");

        Optional<ISource> optionalSources = aliParser.provider.getSources(resourceBundle);
        if (!optionalSources.isPresent()) {
            System.exit(2);
            logger.error("The provider could not find the document from the resource.");
        }
        aliParser.source = optionalSources.get();
        logger.info("Resource found - {}", aliParser.source.getSourceType().toString());
        ThreadBuilder threadBuilder = new ThreadBuilder(aliParser.source);

        logger.info("Waiting when tasks are finished...");
        do {
            threadBuilder.runTasks();
            while (!threadBuilder.allTasksIsDone()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {
                }
            }
        } while (ConcurrentSetWorker.getCount() < 100);

        logger.info("Trying to get results...");
        logger.info("Collected all items in the collection: {}", ConcurrentSetWorker.getCount());
        Optional<Integer> summ = threadBuilder.getAllResults();
        if (summ.isPresent()) {
            logger.info("Total processed products: {}", summ.get());
        }

        threadBuilder.stopTasks();

//        IWriter fileWriter = new CsvFileWriter();
//        fileWriter.write(ConcurrentSetWorker.getCollection());

        date = sdf.format(new Date());
        logger.info("Completed. ({})", date);
    }
}
