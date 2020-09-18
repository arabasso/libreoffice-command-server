package libreoffice.command.server.commands;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.sun.star.uno.UnoRuntime;
import com.sun.star.util.XReplaceable;
import com.sun.star.util.XReplaceDescriptor;
import com.sun.star.lang.XComponent;

import libreoffice.command.server.State;

public class ReplaceCommand extends Command {
    @JsonProperty("component")
    private String component;
    @JsonProperty("searchString")
    private String searchString;
    @JsonProperty("replaceString")
    private String replaceString;

    @Override
    public void execute(State state) throws Exception {

        XComponent xComponent = state.get(this.component);

        XReplaceable xReplaceable = UnoRuntime.queryInterface(XReplaceable.class, xComponent);
        XReplaceDescriptor xReplaceDescr = xReplaceable.createReplaceDescriptor();

        xReplaceDescr.setSearchString(searchString);
        xReplaceDescr.setReplaceString(replaceString);

        xReplaceable.replaceAll(xReplaceDescr);
    }
}
