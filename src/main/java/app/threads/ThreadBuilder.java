package app.threads;

import app.chain.BaseChain;
import app.chain.document.FileDocumentLoader;
import app.chain.document.UrlDocumentLoader;
import app.chain.parser.FileParser;
import app.chain.parser.IParser;
import app.chain.parser.UrlParser;
import app.provider.source.ISource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

/**
 * Builds and runs tasks in the form of a Chain of Responsibilities.
 * The number of tasks and threads for parsing the file selects 1, for parsing the URL 5.
 * It depends on the ISource object in the constructor
 */
public class ThreadBuilder {

    private ISource source;
    private ExecutorService executorService;
    private List<Future<Integer>> futures;
    private int countThreadsAndTask = 0;
    private BaseChain documentLoader;
    private IParser parser;

    /**
     * The Constructor
     * @param source An object implementing ISource with properties.
     */
    public ThreadBuilder(ISource source) {
        this.source = source;
    }

    /**
     * Depending on the ISource's properties prepares and configures
     * the necessary classes for their collaboration in the chain and
     * Prepares the pool
     */
    private void prepareTasks() {
        switch (source.getSourceType()) {
            case FILE: {
                countThreadsAndTask = 1;
                documentLoader = new FileDocumentLoader(source.getSources());
                parser = new FileParser();
                break;
            }
            case URL: {
                countThreadsAndTask = 5;
                documentLoader = new UrlDocumentLoader(source.getSources());
                parser = new UrlParser();
                break;
            } default: return; //todo make throw
        }
        documentLoader.setNext((BaseChain)parser);

        if (executorService != null) {
            stopTasks();
        }
        executorService = Executors.newFixedThreadPool(countThreadsAndTask);
        futures = new ArrayList<>();
    }

    /**
     * Create tasks and Run all the prepared chains.
     */
    public void runTasks() {
        prepareTasks();
        for (int i = 0; i < countThreadsAndTask; i++) {
            Callable<Integer> task = new Task(documentLoader);
            futures.add(executorService.submit(task));
        }
    }

    /**
     * Checks if all threads are stopped
     * @return boolean if all threads have stopped it will return true
     */
    public boolean allTasksIsDone() {
        boolean allDone = true;
        for (Future<Integer> future : futures) {
            allDone &= future.isDone();
        }
        return allDone;
    }

    /**
     * Traversing and getting the results of all tasks.
     * @return Optional<Integer> If all tasks are completed,
     *         then this is the total amount of goods that were received.
     *         Otherwise it is null.
     */
    public Optional<Integer> getAllResults() {
        if (!allTasksIsDone()) {
            return Optional.empty();
        }
        int summ = 0;
        for (Future<Integer> future : futures) {
            try {
                summ += (int) future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return Optional.of(summ);
    }

    /**
     * Allows threads to stop or stops them forcibly.
     */
    public void stopTasks() {
        /* правильная остановка */
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
        System.out.println("Threads were stoped");
    }

}
