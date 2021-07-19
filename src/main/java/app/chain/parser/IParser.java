package app.chain.parser;

import java.util.List;

/**
 * Implementing classes must be able to parse some List of String.
 */
public interface IParser {
    boolean parse(List<String> content);
}
