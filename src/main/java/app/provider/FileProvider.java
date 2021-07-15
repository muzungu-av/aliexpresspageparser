package app.provider;

import app.provider.source.FileSource;
import app.provider.source.ISource;

import java.util.Optional;
import java.util.ResourceBundle;


/**
 * Connects to the file-resource and provides its document
 */
public class FileProvider implements IProvider {
    private ISource source;

    /**
     * @param resourceBundle binding to application resources
     * @return Optional<Document> The document, if it exists.
     */
    @Override
    public Optional<ISource> getSources(ResourceBundle resourceBundle) {
        source = new FileSource();
        if (source.getSourceType() == ISource.sourceType.FILE) {
            String fileMame = resourceBundle.getString("target_File_Resource");
            source.addSources(fileMame);
        } else {
            return Optional.empty();
        }
        return Optional.of(source);
    }
}
