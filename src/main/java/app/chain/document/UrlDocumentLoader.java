package app.chain.document;

import app.chain.BaseChain;

import java.util.List;

/**
 * Implements the first link in the chain, downloads data via a request.
 */
public class UrlDocumentLoader extends BaseChain implements IDocumentLoader {

    public List<String> sourceList;

    /**
     * the constructor
     * @param sourceList A list of sources, that is, URLs.
     */
    public UrlDocumentLoader(List<String> sourceList) {
        this.sourceList = sourceList;
    }

    /**
     * The method calls the execution of the main work and then passes control to the following classes.
     * @param request
     */
    public void handleRequest(Object request) {
        if (loadDocument(request)) {
            System.out.println("UrlDocumentLoader. succesful");
            super.handleRequest(request);
        } else {
            System.out.println("UrlDocumentLoader. Error Loader.  STOP");
        }
    }

    /**
     * Executes a GET request receives a response as json
     *
     * @param request
     * @return
     */
    @Override
    public boolean loadDocument(Object request) {
        System.out.println("UrlDocumentLoader - load...");
        return true;
    }
}
