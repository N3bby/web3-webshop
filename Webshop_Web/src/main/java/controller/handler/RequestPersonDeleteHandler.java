package controller.handler;

import controller.Handler;
import controller.WebAction;
import domain.Person;
import service.ShopService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebAction("requestPersonDelete")
public class RequestPersonDeleteHandler extends Handler {

    public RequestPersonDeleteHandler(ShopService service) {
        super(service);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Person person = getService().getPerson(request.getParameter("id"));
        request.setAttribute("person", person);

        forward("personDelete.jsp", request, response);

    }

}
