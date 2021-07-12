package app;

import app.provider.IProvider;
import app.provider.ProviderConstructor;
import org.jsoup.nodes.Document;

import java.util.Optional;
import java.util.ResourceBundle;

public class AliParser {

    private static IProvider provider;

    public static void main(String[] args) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("app");
        String target = resourceBundle.getString("target");
        ProviderConstructor providerConstructor = new ProviderConstructor();
        try {
            provider = providerConstructor.getProvider(target);
        } catch (Exception e) {
            //todo log
            System.out.println(e.getMessage());
            System.exit(1);
        }

        Optional<Document> optionalDocument = provider.getDocument(resourceBundle);
        if (!optionalDocument.isPresent()) {
            System.exit(2);
            //todo log
            System.out.println("Document not found");
        }
        Document document = optionalDocument.get();

        /* Stop. this is not the right way because jsoup cannot work with js  */
    }
}
