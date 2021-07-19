package app.chain.parser;

import app.chain.BaseChain;

import java.util.List;
import java.util.stream.Collectors;

public class FileParser extends BaseChain implements IParser {

    @Override
    public int handleRequest(List<?> somelist, Class<?> clazz) {
        List<String> content = somelist.stream()
                .filter(clazz::isInstance)
                .map(element -> (String) element)
                .collect(Collectors.toList());
        if (parse(content)) {
            System.out.println("FileParser. succesful");
            return super.handleRequest(somelist, clazz);
        } else {
            System.out.println("PARSER. Error Parsing.  STOP");
            return 0;
        }
    }

    @Override
    public boolean parse(List<String> content) {
        for (String str : content) {
            System.out.println(str);
        }
        return true;
    }
}
