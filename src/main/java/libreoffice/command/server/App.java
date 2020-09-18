package libreoffice.command.server;

import javax.servlet.MultipartConfigElement;
import libreoffice.command.server.commands.Command;
import com.sun.star.uno.XComponentContext;

import static spark.Spark.*;

public class App 
{
    public static void main( String[] args )
    {
        int port = 4567;

        for (int i = 0; i < args.length; i++) {
            switch ( args[i] ) {
                case "--port":
                    port = Integer.parseInt(args[++i]);
                break;
            }
        }

        port(port);

        final LibreOfficePool pool = new LibreOfficePool();

        put("/", (request, response) -> {
            request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement(System.getProperty("java.io.tmpdir")));

            XComponentContext xContext = pool.borrowObject();

            State state = new State(xContext, request, response);

            for (Command command : state.getCommands()) {
                command.execute(state);
            }

            state.close();

            return response;
        });

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                pool.shutdown();
                System.out.println("Shutting down ...");
            }
        });
    }
}
