package libreoffice;

import java.io.Closeable;
import java.io.IOException;

public class Variable implements Closeable {
    private Object value;

    public Variable(Object value) {
        this.value = value;
    }

    @SuppressWarnings("unchecked")
    public <T> T getValue() {
        return (T) this.value;
    }

    @Override
    public void close() throws IOException {
    }
}
