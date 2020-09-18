package libreoffice;

import java.io.IOException;
import java.nio.file.Path;

public class PathVariable extends Variable {

    public PathVariable(final Object value) {
        super(value);
    }

    @Override
    public void close() throws IOException {
        Path path = getValue();

        path.toFile().delete();
    }
}
