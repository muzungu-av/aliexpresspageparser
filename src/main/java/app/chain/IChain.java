package app.chain;

import java.util.List;

/**
 * Its who implement this should be able to call the next class in the handleRequest() method
 * and also be able to configure the next one.
 */
public interface IChain {

    void handleRequest(List<?> soumList, Class<?> clazz);

    void setNext(IChain next);
}
