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

@WebAction("personAdd")
public class PersonAddHandler extends Handler {

    public PersonAddHandler(ShopService service) {
        super(service);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
                getService().addPerson(person);
            } catch (Exception e) {
                errors.add(e.getMessage());
            }
        }

        if (errors.size() != 0) {
            request.setAttribute("errors", errors);
            forward("personAdd.jsp", request, response);
        } else {
            forward("index.jsp", request, response);
        }

    }

}
