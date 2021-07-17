package app.chain;

import java.util.List;

/**
 * Abstract base class of the Chain of Responsibilities pattern.
 * The extended classes must call the H() method at the end of their work
 * to transfer control further down the chain.
 */
public abstract class BaseChain implements IChain {

    private IChain next;

    /**
     * Searches for the Next one and passes control to it.
     */
    @Override
    public void handleRequest(List<?> somelist, Class<?> clazz) {
        if (this.next != null) {
            this.next.handleRequest(somelist, clazz);
        }
    }

    /**
     * Configures the next one.
     * @param next IChain extended class.
     */
    @Override
    public void setNext(IChain next) {
        this.next = next;
    }
}
