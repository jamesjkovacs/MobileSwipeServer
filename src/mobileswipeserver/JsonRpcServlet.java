package mobileswipeserver;

import java.io.IOException;
import javax.servlet.http.*;

import org.json.rpc.server.*;
import org.slf4j.*;

@SuppressWarnings("serial")
public class JsonRpcServlet extends HttpServlet {

    private final JsonRpcExecutor executor;

    public JsonRpcServlet() {
        executor = bind();
    }

    private JsonRpcExecutor bind() {
        JsonRpcExecutor executor = new JsonRpcExecutor();

        DisplayResponse disres = new SimpleDisplayResponse();
        executor.addHandler("disp", disres, DisplayResponse.class);
        // add more servicessssss here

        return executor;
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        executor.execute(new JsonRpcServletTransport(req, resp));
    }
    
   

}