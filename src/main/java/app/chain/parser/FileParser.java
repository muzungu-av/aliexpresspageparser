package app.chain.parser;

import app.AliParser;
import app.chain.BaseChain;
import app.model.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Parsing html file with JSOUP
 */
public class FileParser extends BaseChain implements IParser {
    private static final Logger logger = LoggerFactory.getLogger(FileParser.class);
    private List<Product> listProduct;

    /**
     * The method calls the execution of the main work and then passes control to the following classes.
     *
     * @param someList List of objects that will be filtered by the second parameter
     * @param clazz    Class type
     * @return number of processed elements.
     */
    @Override
    public int handleRequest(List<?> someList, Class<?> clazz) {
        List<String> content = someList.stream()
                .filter(clazz::isInstance)
                .map(element -> (String) element)
                .collect(Collectors.toList());
        if (parse(content)) {
            logger.info("Html-file succesful parsed.");
            return super.handleRequest(listProduct, Product.class);
        } else {
            logger.error("Failed to parse the file.");
            return 0;
        }
    }

    /**
     * Converts a collection of strings to a collection of Products.
     * Before parsing, concatenates all lines because it is not known whether it was one line or several
     *
     * @param htmlContent List of String with some html content.
     * @return The fact of successful parsing.
     */
    @Override
    public boolean parse(List<String> htmlContent) {
        listProduct = new ArrayList<>();
        String selector = AliParser.resourceBundle.getString("elements_container_selector");
        String joinedHtmlString = String.join("\n", htmlContent);
        Document doc = Jsoup.parse(joinedHtmlString, "UTF-8");
        Elements productElements = doc.select(selector).first().children();
        productElements.forEach(elem -> {
            try {
                Elements links = elem.select("a[href]");
                String productDetailUrl = links.first().attributes().get("href");
                String productImage = links.select("div.item-image > img").first().attributes().get("src");
                links = links.select("div.item-details");
                String productTitle = links.select("h3.item-details-title").first().childNode(0).toString();
                String minPrice = links.select("p.current-price").first().childNode(0).toString().trim();
                minPrice = minPrice.replaceFirst("^.*;", "");
                String oriMinPrice = links.select("p.original-price > del").first().childNode(0).toString().trim();
                oriMinPrice = oriMinPrice.replaceFirst("^.*;", "").trim();
                String discount = links.select("p.original-price > span.folatR").first().childNode(0).toString().trim();
                /*sold*/
                String orders = links.select("div.stock > div.stock-text > span.solder").first().childNode(0).toString().trim();
                String claimed = links.select("div.stock > div.stock-text > span.float-r").first().childNode(0).toString().trim();
                Product product = new Product();
                product.setProductId(getIdFromUrl(productDetailUrl));
                product.setProductDetailUrl(productDetailUrl);
                product.setProductTitle(productTitle);
                product.setProductImage(productImage);
                product.setMinPrice(minPrice);
                product.setOriMinPrice(oriMinPrice);
                product.setDiscount(discount);
                product.setOrders(orders);
                product.setClaimed(claimed);
                listProduct.add(product);
            } catch (Exception ex) {
                logger.error("Unsuccessful attempt to read product from html file: {}", ex.getMessage());
            }
        });
        if (listProduct.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * Returns id of product from url-string.
     *
     * @param url string with Url.
     * @return Id string.
     */
    private String getIdFromUrl(String url) {
        Pattern pattern = Pattern.compile("^.*item\\/(\\d*)\\..*$");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }
}
