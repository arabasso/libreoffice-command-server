package libreoffice.command.server.commands;

import libreoffice.command.server.State;
import com.sun.star.frame.XStorable2;
import com.sun.star.lang.XComponent;
import com.sun.star.uno.UnoRuntime;

import java.nio.file.Path;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.star.beans.PropertyValue;

public class StoreAsURLCommand extends Command {
    @JsonProperty("component")
    private String component;
    @JsonProperty("path")
    private String path;

    @Override
    public void execute(State state) throws Exception {
        XComponent xComponent = state.get(this.component);
        Path path = state.get(this.path);

        XStorable2 xStorable = UnoRuntime.queryInterface(XStorable2.class, xComponent);

        xStorable.storeAsURL(path.toUri().toString(), new PropertyValue[0]);
    }
}
