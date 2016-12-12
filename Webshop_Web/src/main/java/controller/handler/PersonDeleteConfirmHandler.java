package controller.handler;

import controller.Handler;
import controller.WebAction;
import service.ShopService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebAction("personDeleteConfirm")
public class PersonDeleteConfirmHandler extends Handler {

    public PersonDeleteConfirmHandler(ShopService service) {
        super(service);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String userId = request.getParameter("id");
        getService().deletePerson(userId);

        response.sendRedirect("Controller?action=personOverview");

    }

}
