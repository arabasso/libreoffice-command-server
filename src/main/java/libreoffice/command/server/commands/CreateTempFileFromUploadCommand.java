package libreoffice.command.server.commands;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.servlet.http.Part;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;

import libreoffice.command.server.State;

public class CreateTempFileFromUploadCommand extends Command {
    @JsonProperty("name")
    private String name;
    @JsonProperty("return")
    private String ret;

    @Override
    public void execute(State state) throws Exception {
        Part part = state.getRequest().raw().getPart(name);

        String ext = findExtension(part.getSubmittedFileName());

        Path temp = Files.createTempFile("", ext);

        try (InputStream is = part.getInputStream()) {
            try (OutputStream os = new FileOutputStream(temp.toFile())) {
                is.transferTo(os);
                os.close();
            }
            is.close();
        }

        if (Strings.isNullOrEmpty(ret)) {
            ret = state.getName("$temp");
        }

        state.assign(ret, temp);
    }

    public static String findExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf('.');
        if (lastIndex == -1) {
            return "";
        }
        return fileName.substring(lastIndex);
    }
}
