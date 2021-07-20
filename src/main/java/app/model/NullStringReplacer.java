package app.model;

import java.util.function.Function;

/**
 * Replaces null strings with empty strings, if string is not null does concatenation
 */
final class NullStringReplacer implements Function<String, String> {
    @Override
    public String apply(String s) {
        return s != null ? "|".concat(s) : "";
    }
}
