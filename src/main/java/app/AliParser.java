package app;

import app.provider.IProvider;
import app.provider.ProviderConstructor;
import app.provider.source.ISource;
import app.threads.ThreadBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

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
        threadBuilder.runTasks();

        logger.info("Waiting when tasks are finished...");
        while (!threadBuilder.allTasksIsDone()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {
            }
        }
        logger.info("Trying to get results...");
        Optional<Integer> summ = threadBuilder.getAllResults();
        if (summ.isPresent()) {
            System.out.println("RESULT = " + summ.get());
        }
        threadBuilder.stopTasks();

        date = sdf.format(new Date());
        logger.info("Completed. ({})", date);
    }
}
