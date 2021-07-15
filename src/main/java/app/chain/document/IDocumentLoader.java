package app.chain.document;

/**
 * Implementing classes should be able to load a document from some sources.
 */
public interface IDocumentLoader {
    boolean loadDocument(Object request);
}
