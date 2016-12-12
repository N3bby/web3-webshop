package controller.handler;

import controller.Handler;
import controller.WebAction;
import domain.Person;
import service.ShopService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebAction("login")
public class LoginHandler extends Handler {

    public LoginHandler(ShopService service) {
        super(service);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String userid = request.getParameter("userid");
        String password = request.getParameter("password");

        Person user = getService().getUserIfAuthenticated(userid, password);

        if(user != null) { // Logged in

            request.getSession().setAttribute("user", user);
            request.setAttribute("user", user);

        } else { // Wrong user/password

            List<String> errors = new ArrayList<>();
            errors.add("Invalid user id and/or password");

            request.setAttribute("errors", errors);

        }

        forward("index.jsp", request, response);

    }

}
