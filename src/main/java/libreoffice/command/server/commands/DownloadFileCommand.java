package libreoffice.command.server.commands;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;

import com.fasterxml.jackson.annotation.JsonProperty;

import libreoffice.command.server.State;

public class DownloadFileCommand extends Command {
    @JsonProperty("path")
    private String path;
    @JsonProperty("mimetype")
    private String mimetype = "application/octet-stream";

    @Override
    public void execute(State state) throws Exception {
        Path path = state.get(this.path);
        
        state.getResponse().type(this.mimetype);
        state.getResponse().header("Content-disposition", "attachment; filename=" + path.toFile().getName());

        try (InputStream is = new FileInputStream(path.toFile())) {
            is.transferTo(state.getResponse().raw().getOutputStream());
            is.close();
        }

    }
    
}
