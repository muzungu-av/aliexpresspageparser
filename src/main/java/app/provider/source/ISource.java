package app.provider.source;

import java.util.List;

/**
 * Implementing classes should be able to add a string or strings containing references to resources,
 * get them , and also determine the type of resource, which be type of enum (FILE, URL)
 */
public interface ISource {
    enum sourceType {FILE, URL};
    sourceType getSourceType ();
    void addSources(String path);
    void addSources(List<String> paths);
    List<String> getSources();
}
