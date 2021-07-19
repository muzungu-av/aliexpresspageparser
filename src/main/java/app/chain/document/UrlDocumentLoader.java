package app.chain.document;

import app.chain.BaseChain;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements the first link in the chain, downloads data via a request.
 */
public class UrlDocumentLoader extends BaseChain implements IDocumentLoader {
    private static final Logger logger = LoggerFactory.getLogger(UrlDocumentLoader.class);
    private String sourcePath;
    private List<String> readedLines;

    /**
     * the constructor
     *
     * @param sourcePath A string of source, that is, URL.
     */
    public UrlDocumentLoader(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    /**
     * The method calls the execution of the main work and then passes control to the following classes.
     * Note:
     * It unused parameters, because nothing is passed to the first object in the chain,
     * but the interface requires these parameters.
     *
     * @param ignored1 unused List parameter.
     * @param ignored2 unused Class type parameter.
     * @return number of processed elements.
     */
    public int handleRequest(List<?> ignored1, Class<?> ignored2) {
        if (loadDocument()) {
            logger.info("Successfully loaded URL-document.");
            logger.info("Transfer control to the Next.");
            return super.handleRequest(readedLines, String.class);
        } else {
            logger.error("Failed to load URL-documents.");
            return 0;
        }
    }

    /**
     * Redirects to another method,
     * the presence of this method requires the interface.
     *
     * @return true is document was loaded.
     */
    @Override
    public boolean loadDocument() {
        return sendGetRequest();
    }

    /**
     * Executes a GET request receives a response as json.
     *
     * @return true is document was loaded.
     */
    private boolean sendGetRequest() {
        logger.info("Loading a document from the URL {}", this.sourcePath);
        HttpGet request = new HttpGet(this.sourcePath);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                readedLines = new ArrayList<>();
                readedLines.add(EntityUtils.toString(entity));
            }

        } catch (IOException e) {
            logger.error("Error while downloading the url document: {}", e.getMessage());
            return false;
        }
        return true;
    }
}
