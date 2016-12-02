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

    }

    @Override
    public void destroy() {

        super.destroy();

        shopService.close();

    }

    //  Delegation methods ----------------------------------------------------------------------------------------------------------------------------------------------

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        String destination = "index.jsp";

        if (action != null) {
            switch (action) {
                case "switchColor":
                    processSwitchTheme(request, response);
                    return;
                case "personOverview":
                    destination = processPersonOverview(request, response);
                    break;
                case "requestPersonAdd":
                    destination = "personAdd.jsp";
                    break;
                case "personAdd":
                    destination = processPersonAdd(request, response);
                    break;
                case "productOverview":
                    destination = processProductOverview(request, response);
                    break;
                case "requestProductUpdate":
                    destination = processRequestProductUpdate(request, response);
                    break;
                case "requestProductAdd":
                    destination = "productAdd.jsp";
                    break;
                case "productAdd":
                    destination = processProductAdd(request, response);
                    break;
                case "productUpdate":
                    destination = processProductUpdate(request, response);
                    break;
                case "requestProductDelete":
                    destination = processRequestProductDelete(request, response);
                    break;
                case "productDeleteConfirm":
                    destination = processProductDeleteConfirm(request, response);
                    break;
                case "requestPersonDelete":
                    destination = processRequestPersonDelete(request, response);
                    break;
                case "personDeleteConfirm":
                    destination = processPersonDeleteConfirm(request, response);
                    break;
                case "requestCheckPassword":
                    destination = processRequestCheckPassword(request, response);
                    break;
                case "checkPassword":
                    destination = processCheckPassword(request, response);
                    break;
                case "login":
                    destination = processLogin(request, response);
                    break;
                case "logout" :
                    destination = processLogout(request, response);
                    break;
            }
        }

        //Process cookies
        processCookieTheme(request);

        RequestDispatcher dispatcher = request.getRequestDispatcher(destination);
        dispatcher.forward(request, response);

    }

    //  Normal processing ------------------------------------------------------------------------------------------------------------------------------------------------

    private String processPersonOverview(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("personList", shopService.getPersons());
        return "personOverview.jsp";
    }

    private String processPersonAdd(HttpServletRequest request, HttpServletResponse response) {

        List<String> errors = new ArrayList<>();

        Person person = new Person();

        String userid = request.getParameter("userid");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            request.setAttribute("old_userid", userid);
            person.setUserid(userid);
        } catch (Exception e) {
            errors.add(e.getMessage());
        }
        try {
            request.setAttribute("old_firstName", firstName);
            person.setFirstName(firstName);
        } catch (Exception e) {
            errors.add(e.getMessage());
        }
        try {
            request.setAttribute("old_lastName", lastName);
            person.setLastName(lastName);
        } catch (Exception e) {
            errors.add(e.getMessage());
        }
        try {
            request.setAttribute("old_email", email);
            person.setEmail(email);
        } catch (Exception e) {
            errors.add(e.getMessage());
        }
        try {
            person.setPassword(password);
        } catch (Exception e) {
            errors.add(e.getMessage());
        }

        if (errors.size() == 0) {
            try {
                shopService.addPerson(person);
            } catch (Exception e) {
                errors.add(e.getMessage());
            }
        }

        if (errors.size() != 0) {
            request.setAttribute("errors", errors);
            return "personAdd.jsp";
        } else {
            return "index.jsp";
        }

    }

    private String processProductOverview(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("productList", shopService.getProducts());
        return "productOverview.jsp";
    }

    private String processProductAdd(HttpServletRequest request, HttpServletResponse response) {

        List<String> errors = new ArrayList<>();

        Product product = new Product();

        String id = request.getParameter("id");
        String description = request.getParameter("description");
        String price = request.getParameter("price");

        try {
            request.setAttribute("old_id", id);
            product.setId(id);
        } catch (Exception e) {
            errors.add(e.getMessage());
        }
        try {
            request.setAttribute("old_description", description);
            product.setDescription(description);
        } catch (Exception e) {
            errors.add(e.getMessage());
        }
        try {
            request.setAttribute("old_price", price);
            product.setPrice(Double.parseDouble(price));
        } catch (NumberFormatException e) {
            errors.add("Price must be a number.");
        } catch (Exception e) {
            errors.add(e.getMessage());
        }

        if (errors.size() == 0) {
            try {
                shopService.addProduct(product);
            } catch (Exception e) {
                errors.add(e.getMessage());
            }
        }

        if (errors.size() != 0) {
            request.setAttribute("errors", errors);
            return "productAdd.jsp";
        } else {
            return processProductOverview(request, response);
        }


    }

    private String processRequestProductUpdate(HttpServletRequest request, HttpServletResponse response) {

        String id = request.getParameter("id");
        Product product = shopService.getProduct(id);

        request.setAttribute("id", product.getId());
        request.setAttribute("description", product.getDescription());
        request.setAttribute("price", product.getPrice());

        return "productUpdate.jsp";

    }

    private String processProductUpdate(HttpServletRequest request, HttpServletResponse response) {

        String id = request.getParameter("id");
        String description = request.getParameter("description");
        String price = request.getParameter("price");

        List<String> errors = new ArrayList<>();

        Product product = new Product();

        try {
            request.setAttribute("id", id);
            product.setId(id);
        } catch (Exception e) {
            errors.add(e.getMessage());
        }
        try {
            request.setAttribute("description", description);
            product.setDescription(description);
        } catch (Exception e) {
            errors.add(e.getMessage());
        }
        try {
            request.setAttribute("price", price);
            product.setPrice(Double.parseDouble(price));
        } catch (NumberFormatException e) {
            errors.add("Price must be a number.");
        } catch (Exception e) {
            errors.add(e.getMessage());
        }

        if (errors.size() == 0) {
            try {
                shopService.updateProduct(product);
            } catch (Exception e) {
                errors.add(e.getMessage());
            }
        }

        if (errors.size() != 0) {
            request.setAttribute("errors", errors);
            return "productUpdate.jsp";
        } else {
            return processProductOverview(request, response);
        }

    }

    private String processRequestProductDelete(HttpServletRequest request, HttpServletResponse response) {

        Product product = shopService.getProduct(request.getParameter("id"));
        request.setAttribute("product", product);

        return "productDelete.jsp";

    }

    private String processProductDeleteConfirm(HttpServletRequest request, HttpServletResponse response) {

        shopService.deleteProduct(request.getParameter("id"));
        return processProductOverview(request, response);

    }

    private String processRequestPersonDelete(HttpServletRequest request, HttpServletResponse response) {

        Person person = shopService.getPerson(request.getParameter("id"));
        request.setAttribute("person", person);

        return "personDelete.jsp";

    }

    private String processPersonDeleteConfirm(HttpServletRequest request, HttpServletResponse response) {

        String userId = request.getParameter("id");
        shopService.deletePerson(userId);

        return processPersonOverview(request, response);

    }

    private String processRequestCheckPassword(HttpServletRequest request, HttpServletResponse response) {

        Person person = shopService.getPerson(request.getParameter("id"));
        request.setAttribute("person", person);

        return "checkPassword.jsp";

    }

    private String processCheckPassword(HttpServletRequest request, HttpServletResponse response) {

        Person person = shopService.getPerson(request.getParameter("id"));
        String password = request.getParameter("password");

        String resultMessage;

        if (person.isCorrectPassword(password)) {
            resultMessage = "The password is correct";
        } else {
            resultMessage = "The password is NOT correct";
        }

        request.setAttribute("person", person);
        request.setAttribute("resultMessage", resultMessage);

        return "checkPassword.jsp";

    }

    //  Cookie processing ------------------------------------------------------------------------------------------------------------------------------------------------

    //Fixed this with redirects for the moment...
    private void processSwitchTheme(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Cookie theme = getCookieWithNameFromRequest(request, "theme");

        if (theme == null) {
            theme = new Cookie("theme", "red");
        } else {
            switch (theme.getValue()) {
                case "yellow":
                    theme.setValue("red");
                    break;
                case "red":
                    theme.setValue("yellow");
                    break;
                default:
                    theme.setValue("yellow");
                    break;
            }
        }

        response.addCookie(theme);
        response.sendRedirect("Controller?action=" + request.getParameter("redirect"));

    }

    private void processCookieTheme(HttpServletRequest request) {

        Cookie theme = getCookieWithNameFromRequest(request, "theme");

        if(theme != null) {
            request.setAttribute("theme", theme.getValue());
        }

    }

    // Authentication processing -----------------------------------------------------------------------------------------------------------------------------------------

    private String processLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String userid = request.getParameter("userid");
        String password = request.getParameter("password");

        Person user = shopService.getUserIfAuthenticated(userid, password);

        if(user != null) { // Logged in

            request.getSession().setAttribute("user", user);
            request.setAttribute("user", user);

        } else { // Wrong user/password

            List<String> errors = new ArrayList<>();
            errors.add("Invalid user id and/or password");

            request.setAttribute("errors", errors);

        }

        return "index.jsp";

    }

    private String processLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getSession().invalidate();
        return "index.jsp";

    }

    //  Helper methods ---------------------------------------------------------------------------------------------------------------------------------------------------

    private Cookie getCookieWithNameFromList(List<Cookie> cookies, String name) {

        return cookies.stream()
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .orElse(null);

    }

    private Cookie getCookieWithNameFromRequest(HttpServletRequest request, String name) {

        if(request.getCookies() == null) return null;

        return Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .orElse(null);

    }

}