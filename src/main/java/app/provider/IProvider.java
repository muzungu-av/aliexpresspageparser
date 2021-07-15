package app.provider;

import app.provider.source.ISource;

import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Implementing classes should be able to handle ResourceBundle and provide some ISource
 */
public interface IProvider {
    Optional<ISource> getSources(ResourceBundle resourceBundle);
}
