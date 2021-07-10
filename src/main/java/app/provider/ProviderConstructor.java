package app.provider;

/**
 * Factory Method that provides the desired supplier depending on the target type.
 */
public class ProviderConstructor {

    /**
     * Will build a resource provider.
     *
     * @param targetType a string indicating which type of provider to provide
     * @return objects that implements the interface IProvider
     * @throws Exception if the correct type is not found
     */
    public IProvider getProvider(String targetType) throws Exception {
        if ("file".equals(targetType)) {
            return new FileProvider();
        } else if ("url".equals(targetType)) {
            return new UrlProvider();
        } else
            throw new Exception("Error! Unknown type of resource target.");
    }
}
