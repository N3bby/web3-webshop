package controller.handler;

import controller.Handler;
import controller.WebAction;
import service.ShopService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebAction("personOverview")
public class PersonOverviewHandler extends Handler {

    public PersonOverviewHandler(ShopService service) {
        super(service);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("personList", getService().getPersons());
        forward("personOverview.jsp", request, response);

    }

}
