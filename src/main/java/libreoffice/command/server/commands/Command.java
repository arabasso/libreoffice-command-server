package libreoffice.command.server.commands;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import libreoffice.command.server.State;

@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "command")
@JsonSubTypes({
    @Type(value = CreateTempFileFromUploadCommand.class, name = "CreateTempFileFromUpload"),
    @Type(value = CreateTempFileCommand.class, name = "CreateTempFile"),
    @Type(value = OpenCommand.class, name = "Open"),
    @Type(value = CreateCommand.class, name = "Create"),
    @Type(value = CloseCommand.class, name = "Close"),
    @Type(value = ReplaceCommand.class, name = "Replace"),
    @Type(value = StoreSelfCommand.class, name = "StoreSelf"),
    @Type(value = StoreAsURLCommand.class, name = "StoreAsURL"),
    @Type(value = StoreToURLCommand.class, name = "StoreToURL"),
    @Type(value = DownloadFileCommand.class, name = "DownloadFile"),
 })
public abstract class Command {
    public abstract void execute(State state) throws Exception;
}