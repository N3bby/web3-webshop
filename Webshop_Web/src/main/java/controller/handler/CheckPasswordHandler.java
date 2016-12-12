package controller.handler;

import controller.Handler;
import controller.WebAction;
import domain.Person;
import service.ShopService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebAction("checkPassword")
public class CheckPasswordHandler extends Handler {

    public CheckPasswordHandler(ShopService service) {
        super(service);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Person person = getService().getPerson(request.getParameter("id"));
        String password = request.getParameter("password");

        String resultMessage;

        if (person.isCorrectPassword(password)) {
            resultMessage = "The password is correct";
        } else {
            resultMessage = "The password is NOT correct";
        }

        request.setAttribute("person", person);
        request.setAttribute("resultMessage", resultMessage);

        forward("checkPassword.jsp", request, response);

    }

}
