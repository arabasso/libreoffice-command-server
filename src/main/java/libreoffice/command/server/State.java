package libreoffice.command.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.Part;

import com.sun.star.uno.XComponentContext;

import libreoffice.command.server.commands.Command;
import spark.Request;
import spark.Response;

import com.sun.star.uno.Exception;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.frame.XDesktop;
import com.sun.star.lang.XMultiComponentFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.star.frame.XComponentLoader;

public class State {
    private final HashMap<String, Object> variables = new HashMap<String, Object>();
    private final XComponentContext xContext;
    private final XMultiComponentFactory xMultiComponentFactory;
    private final XDesktop xDesktop;
    private final XComponentLoader xComponentLoader;
    private final Request request;
    private final Response response;

    public State(final XComponentContext xContext, final Request request, final Response response) throws Exception {
        this.xContext = xContext;
        this.request = request;
        this.response = response;
        this.xMultiComponentFactory = xContext.getServiceManager();
        this.xDesktop = UnoRuntime.queryInterface(XDesktop.class, this.xMultiComponentFactory.createInstanceWithContext("com.sun.star.frame.Desktop", xContext));
        this.xComponentLoader = UnoRuntime.queryInterface(com.sun.star.frame.XComponentLoader.class, xDesktop);
    }

    public XComponentContext getXContext() {
        return this.xContext;
    }

    public XDesktop getXDesktop() {
        return this.xDesktop;
    }

    public XComponentLoader getXComponentLoader() {
        return this.xComponentLoader;
    }

    public XMultiComponentFactory getXMultiComponentFactory() {
        return this.xMultiComponentFactory;
    }

    public Response getResponse() {
        return this.response;
    }

    public Request getRequest() {
        return this.request;
    }

    public ArrayList<Command> getCommands() throws Exception, IOException, ServletException {
        final Part modelPart = request.raw().getPart("commands");

        try (InputStream is2 = modelPart.getInputStream()) {
            final ObjectMapper mapper = new ObjectMapper();

            final ArrayList<Command> commands = mapper.readValue(is2, new TypeReference<ArrayList<Command>>() { });

            is2.close();

            return commands;
        }
    }

    public void assign(final String name, final Object value) {
        this.variables.put(name, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(final String name) {
        return (T)this.variables.get(name);
    }

    private final HashMap<String, Integer> names = new HashMap<String, Integer>();

    public String getName(final String prefix) {
        if (!names.containsKey(prefix)) {
            names.put(prefix, 0);
        }

        final String name = prefix + names.get(prefix);

        names.merge(prefix, 1, Integer::sum);

        return name;
    }
}
