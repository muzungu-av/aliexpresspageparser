package app.threads;

import app.chain.BaseChain;

import java.util.concurrent.Callable;

/**
 * A callable Task that should start the first stage for execution
 * by calling its method handleRequest() from the extended BaseChain-class
 */
public class Task implements Callable<Integer> {
    private BaseChain firstLink;

    /**
     * the Constructor
     * @param firstLink
     */
    public Task(BaseChain firstLink) {
        this.firstLink = firstLink;
    }

    /**
     * The main method starts the entire chain for execution.
     * @return number of processed entities.
     * @throws Exception
     */
    @Override
    public Integer call() throws Exception {
        firstLink.handleRequest(new Object());
        Thread.sleep(500);
        return 1;
    }
}
