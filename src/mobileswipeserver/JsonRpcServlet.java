package mobileswipeserver;

import java.io.IOException;
import javax.servlet.http.*;
import java.io.BufferedReader;
import org.json.rpc.server.*;
import org.slf4j.*;

import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.logging.Logger;
import com.google.gson.Gson;

@SuppressWarnings("serial")
public class JsonRpcServlet extends HttpServlet {

    private final JsonRpcExecutor executor;
	private static final Logger log = Logger.getLogger(JsonRpcServlet.class.getName());

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
    	
    	
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = req.getReader();
            reader.mark(10000);

            String line;
            do {
                line = reader.readLine();
                sb.append(line).append("\n");
            } while (line != null);
            reader.reset();
            // do NOT close the reader here, or you won't be able to get the post data twice
        } catch(IOException e) {
            log.warning("getPostData couldn't.. get the post data");  // This has happened if the request's reader is closed    
        }
        log.warning("getReader = " + sb);
        String res = sb.toString();
    	    	
    	  	
    	log.warning("query string = " + req.getQueryString());
    	log.warning("URI = " + req.getRequestURI());
    	log.warning("URL = " + req.getRequestURL());
    	
//        executor.execute(new JsonRpcServletTransport(req, resp));
      Gson gson = new Gson();
      //GsonObject gsonobject = new GsonObject();
      GsonObject gsonobject = gson.fromJson(res, GsonObject.class);
      log.warning("gsonobject.method = " + gsonobject.method);
      log.warning("gsonobject.params = " + gsonobject.params);
      log.warning("gsonobject.version = " + gsonobject.version);
      log.warning("gsonobject.id = " + gsonobject.id);
      
      
      
    }
    
   

}