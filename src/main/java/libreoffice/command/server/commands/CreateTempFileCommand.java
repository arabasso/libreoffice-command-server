package libreoffice.command.server.commands;

import java.nio.file.Files;
import java.nio.file.Path;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;

import libreoffice.command.server.State;

public class CreateTempFileCommand extends Command {
    @JsonProperty("extension")
    private String extension;
    @JsonProperty("return")
    private String ret;

    @Override
    public void execute(State state) throws Exception {
        Path temp = Files.createTempFile("", extension);

        if (Strings.isNullOrEmpty(ret)) {
            ret = state.getName("$temp");
        }

        state.assign(ret, temp);
    }
}
