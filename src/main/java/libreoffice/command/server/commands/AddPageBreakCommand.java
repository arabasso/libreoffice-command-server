package libreoffice.command.server.commands;

import com.fasterxml.jackson.annotation.JsonProperty;

import libreoffice.command.server.State;

import com.sun.star.frame.XDispatchProvider;
import com.sun.star.lang.XComponent;
import com.sun.star.text.XTextDocument;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.beans.PropertyValue;

public class AddPageBreakCommand extends Command {
    @JsonProperty("component")
    private String component;
    
    @Override
    public void execute(State state) throws Exception {
        XComponent xComponent = state.get(this.component);

        XTextDocument xTextDocument = UnoRuntime.queryInterface(XTextDocument.class, xComponent);

        XDispatchProvider provider = UnoRuntime.queryInterface(XDispatchProvider.class, xTextDocument.getCurrentController().getFrame());

        state.getDispatcher().executeDispatch(provider, ".uno:GoToEndOfDoc", "", 0, new PropertyValue[0]);
        state.getDispatcher().executeDispatch(provider, ".uno:InsertPagebreak", "", 0, new PropertyValue[0]);
    }
}