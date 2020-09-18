package libreoffice.command.server.commands;

import libreoffice.command.server.State;
import com.sun.star.frame.XStorable2;
import com.sun.star.lang.XComponent;
import com.sun.star.uno.UnoRuntime;

import java.nio.file.Path;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.star.beans.PropertyValue;

public class StoreToURLCommand extends Command {
    @JsonProperty("component")
    private String component;
    @JsonProperty("path")
    private String path;
    @JsonProperty("filterName")
    private String filterName;
    @JsonProperty("overwrite")
    private Boolean overwrite = Boolean.TRUE;

    @Override
    public void execute(State state) throws Exception {
        PropertyValue storeProps[] = new PropertyValue[] { new PropertyValue() {
            {
                Name = "Overwrite";
                Value = overwrite;
            }
        }, new PropertyValue() {
            {
                Name = "FilterName";
                Value = filterName;
            }
        } };

        XComponent xComponent = state.get(this.component);
        Path path = state.get(this.path);

        XStorable2 xStorable = UnoRuntime.queryInterface(XStorable2.class, xComponent);

        xStorable.storeToURL(path.toUri().toString(), storeProps);
    }
}
