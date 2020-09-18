package libreoffice.command.server.commands;

import libreoffice.command.server.State;

import com.sun.star.uno.UnoRuntime;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.star.lang.XComponent;
import com.sun.star.util.XCloseable;

public class CloseCommand extends Command {
    @JsonProperty("component")
    private String component;
    @JsonProperty("arg0")
    private boolean arg0;

    @Override
    public void execute(State state) throws Exception {
        XComponent xComponent = state.get(component);
        
        XCloseable xCloseable = UnoRuntime.queryInterface(XCloseable.class, xComponent);

        xCloseable.close(arg0);
    }
}
