package controller.handler;

import controller.Handler;
import controller.WebAction;
import domain.Person;
import service.ShopService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebAction("requestCheckPassword")
public class RequestCheckPasswordHandler extends Handler {

    public RequestCheckPasswordHandler(ShopService service) {
        super(service);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Person person = getService().getPerson(request.getParameter("id"));
        request.setAttribute("person", person);

        forward("checkPassword.jsp", request, response);

    }

}
