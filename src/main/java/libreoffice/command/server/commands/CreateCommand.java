package libreoffice.command.server.commands;

import libreoffice.XComponentVariable;
import libreoffice.command.server.State;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;
import com.sun.star.lang.XComponent;
import com.sun.star.beans.PropertyValue;

public class CreateCommand extends Command {

    @JsonProperty("hidden")
    private Boolean hidden = Boolean.FALSE;
    @JsonProperty("showTrackedChanges")
    private Boolean showTrackedChanges = Boolean.FALSE;
    @JsonProperty("return")
    private String ret;

    @Override
    public void execute(State state) throws Exception {
        PropertyValue xEmptyArgs[] = new PropertyValue[] { new PropertyValue() {
            {
                Name = "Hidden";
                Value = hidden;
            }
        }, new PropertyValue() {
            {
                Name = "ShowTrackedChanges";
                Value = showTrackedChanges;
            }
        } };

        XComponent xComponent = state.getXComponentLoader().loadComponentFromURL("private:factory/swriter", "_blank", 0, xEmptyArgs);

        if (Strings.isNullOrEmpty(ret)) {
            ret = state.getName("$component");
        }

        state.assign(ret, new XComponentVariable(xComponent));
    }
}
