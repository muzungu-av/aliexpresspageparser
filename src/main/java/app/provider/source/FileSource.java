package app.provider.source;

import java.util.ArrayList;
import java.util.List;

/**
 * Is an implementation of the ISource that works with File resources
 */
public class FileSource implements ISource {
    private List<String> sourceList;

    /**
     * Notifies about its resource type
     * @return enum type of resource
     */
    @Override
    public sourceType getSourceType() {
        return sourceType.FILE;
    }

    /**
     * Adds a single file resource
     * @param path The path to file.
     */
    @Override
    public void addSources(String path) {
        if (sourceList == null) {
            sourceList = new ArrayList<>();
        }
        sourceList.add(path);
    }

    /**
     * Adds multiple file resources.
     * @param paths List of paths to file.
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
