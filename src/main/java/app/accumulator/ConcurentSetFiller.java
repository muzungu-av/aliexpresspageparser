package app.accumulator;

import app.chain.BaseChain;
import app.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Final class in the chain of responsibility.
 * Its task is to send products to the summarizing collection.
 */
public class ConcurentSetFiller extends BaseChain {
    private static final Logger logger = LoggerFactory.getLogger(ConcurentSetFiller.class);

    /**
     * Filters the list selects only products and sends them to the summarizing collection.
     *
     * @param somelist Product List
     * @param clazz    Product.class to filter by this class.
     * @return count of processed products
     */
    public int handleRequest(List<?> somelist, Class<?> clazz) {
        List<Product> products = somelist.stream()
                .filter(clazz::isInstance)
                .map(element -> (Product) element)
                .collect(Collectors.toList());

        int countProducts = addToSet(products);
        logger.info("The thread '{}' has added all products to the collection", Thread.currentThread().getName());
        return countProducts;
    }

    /**
     * Sends them to the summarizing collection
     *
     * @param products Product List
     * @return count processed products.
     */
    private int addToSet(List<Product> products) {
        products.forEach(ConcurrentSetWorker::add);
        return products.size();
    }
}
