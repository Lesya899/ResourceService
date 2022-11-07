package servlets;


import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.xml.sax.SAXException;
import resources.ResourceServer;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Servlet extends HttpServlet {
    public static final String PAGE_URL = "/resources";
    private ResourceServer resourceServer;

    public Servlet(ResourceServer resourceServer) {
        this.resourceServer = resourceServer;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        String path = request.getParameter("path");
        try {
            resourceServer.setInstanceFromXml(path);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
}