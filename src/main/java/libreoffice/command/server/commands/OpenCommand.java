package libreoffice.command.server.commands;

import java.nio.file.Path;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;

import libreoffice.command.server.State;
import com.sun.star.lang.XComponent;
import com.sun.star.beans.PropertyValue;

public class OpenCommand extends Command {
    @JsonProperty("path")
    private String path;
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

        Path path = state.get(this.path);

        XComponent xComponent = state.getXComponentLoader().loadComponentFromURL(path.toUri().toString(), "_blank", 0, xEmptyArgs);

        if (Strings.isNullOrEmpty(ret)) {
            ret = state.getName("$component");
        }

        state.assign(ret, xComponent);
    }
}
