package app.provider.source;

import java.util.ArrayList;
import java.util.List;

/**
 * Is an implementation of the ISource that works with URl resources
 */
public class UrlSource implements ISource {
    private List<String> sourceList;

    /**
     * Notifies about its resource type
     * @return enum type of resource
     */
    @Override
    public sourceType getSourceType() {
        return sourceType.URL;
    }

    /**
     * Adds a single URL resource
     * @param path The Url.
     */
    @Override
    public void addSources(String path) {
        if (sourceList == null) {
            sourceList = new ArrayList<>();
        }
        sourceList.add(path);
    }

    /**
     * Adds multiple Url resources.
     * @param paths List of urls.
     */
    @Override
    public void addSources(List<String> paths) {
        if (sourceList == null) {
            sourceList = new ArrayList<>();
        }
        sourceList.addAll(paths);
    }

    /**
     * Returns a list of resources
     *
     * @return List of resources
     */
    @Override
    public List<String> getSources() {
        return sourceList;
    }
}
