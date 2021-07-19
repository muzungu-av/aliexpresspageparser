package app.writer;

import java.util.List;

/**
 * Classes that inherit this interface should be able to write a strings collection
 */
public interface IWriter {
    void write(List<String> list);
}
