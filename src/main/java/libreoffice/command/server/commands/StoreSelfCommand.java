package libreoffice.command.server.commands;

import libreoffice.command.server.State;
import com.sun.star.frame.XStorable2;
import com.sun.star.lang.XComponent;
import com.sun.star.uno.UnoRuntime;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.star.beans.PropertyValue;

public class StoreSelfCommand extends Command {
    @JsonProperty("component")
    private String component;

    @Override
    public void execute(State state) throws Exception {
        XComponent xComponent = state.get(this.component);

        XStorable2 xStorable = UnoRuntime.queryInterface(XStorable2.class, xComponent);

        xStorable.storeSelf(new PropertyValue[0]);
    }
}
