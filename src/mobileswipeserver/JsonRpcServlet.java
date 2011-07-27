package mobileswipeserver;

import java.io.IOException;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.json.rpc.server.*;
import org.slf4j.*;

import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.logging.Logger;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

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
    	
    	
/*        StringBuilder sb = new StringBuilder();
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
*/    	    	
	    Enumeration names = req.getParameterNames();
    	String callAttribute = req.getParameter("call");
    	if(callAttribute.equals("processBarcode"))
	    {
		    StringBuilder stringBuilder = new StringBuilder();
		    BufferedReader bufferedReader = null;
		    try {
				InputStream inputStream = req.getInputStream();
				if (inputStream != null) {
					bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
					char[] charBuffer = new char[128];
					int bytesRead = -1;
					while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
						stringBuilder.append(charBuffer, 0, bytesRead);
					}
				} else {
					stringBuilder.append("");
				}
			} catch (IOException ex) {
				throw ex;
			} finally {
				if (bufferedReader != null) {
					try {
						bufferedReader.close();
					} catch (IOException ex) {
					  throw ex;
				  }
				}
			}
		   	Gson gson = new Gson();
		//    GsonObject gsonobject;
		   	GsonObject gsonobject = gson.fromJson(stringBuilder.toString(), GsonObject.class);
	 /*    	try {
	    		gsonobject = gson.fromJson(stringBuilder.toString(), GsonObject.class);
	    	} catch (JsonSyntaxException ex) {
	    		    // crash and burn
	    		throw new IOException("Error parsing JSON request string");
	    	}
	*/   	
	    	
	//    	String body = stringBuilder.toString();    	  	
	    	log.warning("query string = " + req.getQueryString());
	    	log.warning("URI = " + req.getRequestURI());
	    	log.warning("URL = " + req.getRequestURL());
	    	
	//      Gson gson = new Gson();
	//      GsonObject gsonobject = new GsonObject();
	//      GsonObject gsonobject = gson.fromJson(res, GsonObject.class);
	      log.warning("gsonobject.method = " + gsonobject.getMethod());
	      int[] test = gsonobject.getParams();
	      log.warning("gsonobject.params = " + test[0]);
	      log.warning("gsonobject.version = " + gsonobject.getVersion());
	      log.warning("gsonobject.id = " + gsonobject.getId());
		  DisplayResponse disp = new SimpleDisplayResponse();
			  
		  resp.setContentType("text/plain");
		  BarcodeResults barcoderesult = disp.processBarcode(test[0]);
		  String gsonBarcodeResult = gson.toJson(barcoderesult);
		  resp.getWriter().println(gsonBarcodeResult);
	    }
	    else
	    	executor.execute(new JsonRpcServletTransport(req, resp));
     
    }
    
   

}