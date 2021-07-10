package app.provider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Connects to the url-resource and provides its document
 */
public class UrlProvider implements IProvider {
    /**
     * @param resourceBundle binding to application resources
     * @return Optional<Document> The document, if it exists.
     */
    @Override
    public Optional<Document> getDocument(ResourceBundle resourceBundle) {
        Document doc = null;
        String url = resourceBundle.getString("target_Url_Resource");
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            //todo log
        }
        return Optional.ofNullable(doc);
    }
}