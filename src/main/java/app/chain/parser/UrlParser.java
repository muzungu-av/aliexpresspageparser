package app.chain.parser;

import app.chain.BaseChain;
import app.model.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Converts a collection of strings to a collection of Products.
 */
public class UrlParser extends BaseChain implements IParser {
    private static final Logger logger = LoggerFactory.getLogger(UrlParser.class);

    private List<Product> listProduct;

    /**
     * The method calls the execution of the main work and then passes control to the following classes.
     *
     * @param someList List of objects that will be filtered by the second parameter
     * @param clazz    Class type
     * @return number of processed elements.
     */
    public int handleRequest(List<?> someList, Class<?> clazz) {
        List<String> content = someList.stream()
                .filter(clazz::isInstance)
                .map(element -> (String) element)
                .collect(Collectors.toList());
        if (parse(content)) {
            logger.info("Parse succesful.");
            return super.handleRequest(listProduct, Product.class);
        } else {
            logger.error("Parsing failed, nothing to do.");
            return 0;
        }
    }

    /**
     * Converts a collection of strings to a collection of Products.
     * Before parsing, concatenates all lines because it is not known whether it was one line or several
     *
     * @param jsonContent List of String with some json content.
     * @return The fact of successful parsing.
     */
    @Override
    public boolean parse(List<String> jsonContent) {
        String joinedJsonString = String.join("\n", jsonContent);

// Postman returned next string:
// /**/jQuery183014526979136703821_1626085021514({"contextId"...
// if it happens so use this code:
//
//    Pattern pattern = Pattern.compile("^.+\\((.*)\\);$");
//    Matcher matcher = pattern.matcher(joinedString);
//    if (matcher.find()) {
//        logger.info(matcher.group(1));
//    } else {
//        logger.warn("WARNING Do not matches");
//    }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            jsonNode = objectMapper.readTree(joinedJsonString).get("results");
            listProduct = objectMapper.readValue(jsonNode.toString(), new TypeReference<List<Product>>() {
            });
        } catch (JsonProcessingException e) {
            logger.error("Don't can read jsonNode from string: {}", e.getMessage());
            return false;
        }
        return true;
    }


}
