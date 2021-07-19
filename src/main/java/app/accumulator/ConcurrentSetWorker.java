package app.accumulator;

import app.chain.BaseChain;
import app.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

/**
 * A static class that works (adds) with ConcurrentSkipListSet.
 */
public class ConcurrentSetWorker extends BaseChain {

    private static final Logger logger = LoggerFactory.getLogger(ConcurrentSetWorker.class);

    private static final ConcurrentSkipListSet<Product> concurrentSet = new ConcurrentSkipListSet<Product>();

    /**
     * Adds one product to the collection.
     *
     * @param product Product class
     */
    public static void add(Product product) {
        try {
            concurrentSet.add(product);
        } catch (Exception e) {
            logger.error("When trying to add value to ConcurrentSkipListSet happened this: {}", e.getMessage());
        }
    }

    /**
     * Converts the collection to a list of strings, by calling the method product.toString().
     *
     * @return List of String
     */
    public static List<String> getCollection() {
        List<String> products = concurrentSet
                .stream()
                .map(product -> product.toString())
                .collect(Collectors.toList());
        return products;
    }

    /**
     * Return collection size.
     *
     * @return number of Collection size.
     */
    public static int getCount() {
        return concurrentSet.size();
    }

}
