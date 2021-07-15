package app.chain.document;

import app.chain.BaseChain;

import java.util.List;

/**
 * Implements the first link in the chain, reads the file.
 */
public class FileDocumentLoader extends BaseChain implements IDocumentLoader {

    public List<String> sourceList;

    /**
     * the constructor
     * @param sourceList A list of sources, that is, file paths. Right now it's a single file
     */
    public FileDocumentLoader(List<String> sourceList) {
        this.sourceList = sourceList;
    }

    /**
     * The method calls the execution of the main work and then passes control to the following classes.
     * @param request
     */
    public void handleRequest(Object request) {
        if (loadDocument(request)) {
            System.out.println("FileDocumentLoader. succesful");
            super.handleRequest(request);
        } else {
            System.out.println("FileDocumentLoader. Error Loader.  STOP");
        }
    }

    /**
     * Reads a html-document from a file.
     *
     * @param request
     * @return
     */
    @Override
    public boolean loadDocument(Object request) {
        System.out.println("FileDocumentLoader - load...");
        return true;
    }
}
