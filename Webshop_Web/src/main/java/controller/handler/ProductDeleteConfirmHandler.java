package controller.handler;

import controller.Handler;
import controller.WebAction;
import service.ShopService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebAction("productDeleteConfirm")
public class ProductDeleteConfirmHandler extends Handler {

    public ProductDeleteConfirmHandler(ShopService service) {
        super(service);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        getService().deleteProduct(request.getParameter("id"));
        response.sendRedirect("Controller?action=productOverview");

    }

}
