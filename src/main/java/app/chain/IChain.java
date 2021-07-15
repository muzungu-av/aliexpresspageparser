package app.chain;

/**
 * Its who implement this should be able to call the next class in the handleRequest() method
 * and also be able to configure the next one.
 */
public interface IChain {
    void handleRequest(Object request);

    void setNext(IChain next);
}
