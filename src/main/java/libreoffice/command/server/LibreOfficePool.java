package libreoffice.command.server;

import com.sun.star.uno.XComponentContext;

public class LibreOfficePool extends ObjectPool<com.sun.star.uno.XComponentContext> {
    public LibreOfficePool() {
        super(5, 5, 5, 20);
    }

    @Override
    protected XComponentContext create() {
        try {
            return Bootstrap.bootstrap();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void close(XComponentContext object) {
    }
}