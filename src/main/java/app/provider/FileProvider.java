package app.provider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Connects to the file-resource and provides its document
 */
public class FileProvider implements IProvider {
    /**
     * @param resourceBundle binding to application resources
     * @return Optional<Document> The document, if it exists.
     */
    @Override
    public Optional<Document> getDocument(ResourceBundle resourceBundle) {
        Document doc = null;
        String fileMame = resourceBundle.getString("target_File_Resource");
        File input = new File(fileMame);
        try {
            doc = Jsoup.parse(input, "UTF-8", "");
        } catch (IOException e) {
            e.printStackTrace();
            //todo log
        }
        return Optional.ofNullable(doc);
    }
}
