package libreoffice;

import java.io.IOException;

import com.sun.star.uno.UnoRuntime;
import com.sun.star.util.CloseVetoException;
import com.sun.star.util.XCloseable;

public class XComponentVariable extends Variable {

    public XComponentVariable(final Object value) {
        super(value);
    }

    @Override
    public void close() throws IOException {
        XCloseable xCloseable = UnoRuntime.queryInterface(XCloseable.class, getValue());

        try {
            xCloseable.close(Boolean.FALSE);
        } catch (CloseVetoException e) {
            throw new IOException(e);
        }
    }
}
