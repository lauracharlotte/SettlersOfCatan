package server.handlers;

import java.util.*;
import java.io.*;
import com.sun.net.httpserver.*;
import java.util.logging.*;

public class Handlers 
{
    public abstract static class BaseFile implements HttpHandler
    {
        private static Logger LOGGER = Logger.getLogger(BaseFile.class.getName());
        public BaseFile(String rootPath){this.rootPath = rootPath;}
        protected String rootPath;
        protected String getRequestPath(HttpExchange exchange)
        {
            return exchange.getRequestURI().getPath().substring(1);
        }
        protected void sendFile(HttpExchange exchange, String filepath) throws IOException
        {
            try 
            {
                LOGGER.log(Level.FINE, "Requesting "+filepath);
                byte[] response = FileUtils.readFile(filepath);
                
                ArrayList<String> mimetypes = new ArrayList<String>();
                mimetypes.add(FileUtils.getMimeType(filepath));
                exchange.getResponseHeaders().put("Content-type", mimetypes);
                
                exchange.sendResponseHeaders(200, response.length);
                
                OutputStream os = exchange.getResponseBody();
                os.write(response);
                os.close();
                
            } 
            catch(IOException ioe)
            {
                LOGGER.log(Level.SEVERE, "Failed to retrieve" + filepath);
                exchange.sendResponseHeaders(404, -1);
                OutputStream os = exchange.getResponseBody();
                os.close();
                System.out.println("Couldn't find the file "+ new File(filepath).getAbsolutePath());
            }
        }
    }
    
    public static class BasicFile extends BaseFile 
    {
        public BasicFile(String rootPath){super(rootPath);}
        @Override
        public void handle(HttpExchange exchange) throws IOException
        {
            String filepath = this.rootPath + this.getRequestPath(exchange);
            this.sendFile(exchange, filepath);
        }
    }
    
    public static class JSONAppender extends BaseFile{
        public JSONAppender(String rootPath){ super(rootPath);}
        @Override
        public void handle(HttpExchange exchange) throws IOException
        {
            System.out.println(this.rootPath + "___" + this.getRequestPath(exchange));
            this.sendFile(exchange, this.rootPath + this.getRequestPath(exchange) + ".json");
        }
    }
    
}

