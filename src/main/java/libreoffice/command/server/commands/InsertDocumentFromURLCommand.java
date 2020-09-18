package libreoffice.command.server.commands;

import java.nio.file.Path;

import com.fasterxml.jackson.annotation.JsonProperty;

import libreoffice.command.server.State;

import com.sun.star.lang.XComponent;
import com.sun.star.text.XTextDocument;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.beans.PropertyValue;
import com.sun.star.document.XDocumentInsertable;

public class InsertDocumentFromURLCommand extends Command {
    @JsonProperty("component")
    private String component;
    @JsonProperty("file")
    private String file;

    @Override
    public void execute(State state) throws Exception {
        XComponent xComponent = state.get(this.component);
        Path path = state.get(this.file);
        
        XTextDocument xTextDocument = UnoRuntime.queryInterface(XTextDocument.class, xComponent);
        
        XDocumentInsertable xDocumentInsertable = UnoRuntime.queryInterface(XDocumentInsertable.class, xTextDocument.getText().getEnd()); 
        
        xDocumentInsertable.insertDocumentFromURL(path.toUri().toString(), new PropertyValue[0]);
    }
}