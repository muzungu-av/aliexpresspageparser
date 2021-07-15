package app;

import app.provider.IProvider;
import app.provider.ProviderConstructor;
import app.provider.source.ISource;
import app.threads.ThreadBuilder;

import java.util.Optional;
import java.util.ResourceBundle;

public class AliParser {

    private IProvider provider;
    private ISource source;

    public static void main(String[] args) {
        AliParser aliParser = new AliParser();
        ResourceBundle resourceBundle = ResourceBundle.getBundle("app");
        String target = resourceBundle.getString("target");
        ProviderConstructor providerConstructor = new ProviderConstructor();
        try {
            aliParser.provider = providerConstructor.getProvider(target);
        } catch (Exception e) {
            //todo log
            System.out.println(e.getMessage());
            System.exit(1);
        }

        Optional<ISource> optionalSources = aliParser.provider.getSources(resourceBundle);
        if (!optionalSources.isPresent()) {
            System.exit(2);
            //todo log
            System.out.println("Document not found");
        }
        aliParser.source = optionalSources.get();

        ThreadBuilder threadBuilder = new ThreadBuilder(aliParser.source);
        threadBuilder.runTasks();
        while (!threadBuilder.allTasksIsDone()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
            }
        }
        Optional<Integer> summ = threadBuilder.getAllResults();
        if (summ.isPresent()) {
            System.out.println("RESULT = " + summ.get());
        }
        threadBuilder.stopTasks();
    }
}
