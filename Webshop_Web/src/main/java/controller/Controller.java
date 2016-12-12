package controller;

import domain.Person;
import domain.Product;
import service.ShopService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@WebServlet("/Controller")
public class Controller extends HttpServlet {

    private HandlerFactory handlerFactory;
    private ShopService shopService;

    //  Predefined methods ----------------------------------------------------------------------------------------------------------------------------------------------

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public void init() throws ServletException {

        super.init();

        ServletContext servletContext = getServletContext();
        String dbinfo_path = servletContext.getInitParameter("dbinfo_path");

        Properties properties = new Properties();
        try {
            properties.load(new FileReader(dbinfo_path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        shopService = new ShopService(properties);
        handlerFactory = new HandlerFactory(shopService);

    }

    @Override
    public void destroy() {

        super.destroy();

        shopService.close();

    }

    //  Delegation methods ----------------------------------------------------------------------------------------------------------------------------------------------

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        processCookieTheme(request);

        String action = request.getParameter("action");
        handlerFactory.getHandler(action).handle(request, response);

    }

    //  Cookie processing ------------------------------------------------------------------------------------------------------------------------------------------------

    private void processCookieTheme(HttpServletRequest request) {

        Cookie theme = getCookieWithNameFromRequest(request, "theme");

        if (theme != null) {
            request.setAttribute("theme", theme.getValue());
        }

    }

    private Cookie getCookieWithNameFromRequest(HttpServletRequest request, String name) {

        if (request.getCookies() == null) return null;

        return Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .orElse(null);

    }

}