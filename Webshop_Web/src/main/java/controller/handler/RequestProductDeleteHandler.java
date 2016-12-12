package controller.handler;

import controller.Handler;
import controller.WebAction;
import domain.Product;
import service.ShopService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebAction("requestProductDelete")
public class RequestProductDeleteHandler extends Handler {

    public RequestProductDeleteHandler(ShopService service) {
        super(service);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Product product = getService().getProduct(request.getParameter("id"));
        request.setAttribute("product", product);

        forward("productDelete.jsp", request, response);

    }

}
