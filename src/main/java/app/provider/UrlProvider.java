package app.provider;

import app.provider.source.ISource;
import app.provider.source.UrlSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Connects to the url-resource and provides its document
 */
public class UrlProvider implements IProvider {

    private ISource source;

    /**
     * Creates a Source and fills it with information about the sources.
     *
     * @param resourceBundle binding to application resources
     * @return Optional<ISource>
     */
    @Override
    public Optional<ISource> getSources(ResourceBundle resourceBundle) {
        source = new UrlSource();
        if (source.getSourceType() == ISource.sourceType.URL) {
            String targetUrl = resourceBundle.getString("target_Url_Resource");
            source.addSources(getMultipleUrl(targetUrl));
        } else {
            return Optional.empty();
        }
        return Optional.of(source);
    }

    /**
     * Converts a string with the base URL to a list of five strings with changing parameters 'limit', 'offset'
     *
     * @param baseUrl Url-string
     * @return List of converted URLs
     */
    private List<String> getMultipleUrl(String baseUrl) {
        Pattern pattern = Pattern.compile("^(.*limit\\=)(\\d*)(.*offset\\=)(\\d*)(.*)$");
        Matcher matcher = pattern.matcher(baseUrl);
        List<String> multipleUrl = new ArrayList<>();
        if (matcher.find()) {
            multipleUrl.add(matcher.replaceFirst("$120$30$5"));  //the same: $1+20+$3+0+$5
            multipleUrl.add(matcher.replaceFirst("$120$320$5"));
            multipleUrl.add(matcher.replaceFirst("$120$340$5"));
            multipleUrl.add(matcher.replaceFirst("$120$360$5"));
            multipleUrl.add(matcher.replaceFirst("$120$380$5"));
        } else {
            //todo log not matched
        }
        return multipleUrl;
    }

}
//  ^.*\((.*)\).$    это для парсера Json



















