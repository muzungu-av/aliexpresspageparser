package app.provider;

import org.jsoup.nodes.Document;

import java.util.Optional;
import java.util.ResourceBundle;

public interface IProvider {
    Optional<Document> getDocument(ResourceBundle resourceBundle);
}
