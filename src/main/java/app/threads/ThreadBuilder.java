package app.threads;

import app.accumulator.ConcurentSetFiller;
import app.chain.BaseChain;
import app.chain.document.FileDocumentLoader;
import app.chain.document.UrlDocumentLoader;
import app.chain.parser.FileParser;
import app.chain.parser.IParser;
import app.chain.parser.UrlParser;
import app.provider.source.ISource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger logger = LoggerFactory.getLogger(ThreadBuilder.class);
    private ISource source;
    private ExecutorService executorService;
    private List<Future<Integer>> futures;
    private int countThreadsAndTask = 0;
    private BaseChain[] documentLoader;

    /**
     * The Constructor
     *
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
        logger.info("Start to prepare tasks.");
        switch (source.getSourceType()) {
            case FILE: {
                countThreadsAndTask = 1;
                documentLoader = new FileDocumentLoader[countThreadsAndTask];
                documentLoader[countThreadsAndTask - 1] = new FileDocumentLoader(source);
                IParser parser = new FileParser();
                documentLoader[countThreadsAndTask - 1].setNext((BaseChain) parser);
                ConcurentSetFiller setFiller = new ConcurentSetFiller();
                ((BaseChain) parser).setNext(setFiller);
                break;
            }
            case URL: {
                countThreadsAndTask = source.getSources().size();
                documentLoader = new UrlDocumentLoader[countThreadsAndTask];
                for (int j = 0; j <= countThreadsAndTask - 1; j++) {
                    documentLoader[j] = new UrlDocumentLoader(source.getSources().get(j));
                    IParser parser = new UrlParser();
                    documentLoader[j].setNext((BaseChain) parser);
                    ConcurentSetFiller setFiller = new ConcurentSetFiller();
                    ((BaseChain) parser).setNext(setFiller);
                }
                break;
            }
            default: {
                logger.error("Unknown resource type.");
                return;
            }
        }

        if (executorService != null) {
            logger.error("ExecutorService was been null. Stop.");
            stopTasks();
        }
        logger.info("Thread pool is configured for {} tasks.", countThreadsAndTask);
        executorService = Executors.newFixedThreadPool(countThreadsAndTask);
        futures = new ArrayList<>();
    }

    /**
     * Create tasks and Run all the prepared chains.
     */
    public void runTasks() {
        prepareTasks();
        logger.info("Tasks start.");
        for (int i = 0; i <= countThreadsAndTask - 1; i++) {
            Callable<Integer> task = new Task(documentLoader[i]);
            futures.add(executorService.submit(task));
        }
    }

    /**
     * Checks if all threads are stopped
     *
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
     *
     * @return Optional<Integer> If all tasks are completed,
     * then this is the total amount of goods that were received.
     * Otherwise it is null.
     */
    public Optional<Integer> getAllResults() {
        if (!allTasksIsDone()) {
            return Optional.empty();
        }
        logger.info("Completion status of all tasks is TRUE.");
        int summ = 0;
        for (Future<Integer> future : futures) {
            try {
                summ += (int) future.get();
            } catch (InterruptedException | ExecutionException e) {
                logger.error("Some problem when run getAllResults() method: {}", e.getMessage());
            }
        }
        return Optional.of(summ);
    }

    /**
     * Allows threads to stop or stops them forcibly.
     */
    public void stopTasks() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
        logger.info("Threads were stoped");

    }

}
