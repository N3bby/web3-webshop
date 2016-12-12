package controller.handler;

import controller.Handler;
import controller.WebAction;
import domain.Product;
import service.ShopService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebAction("requestProductUpdate")
public class RequestProductUpdateHandler extends Handler {

    public RequestProductUpdateHandler(ShopService service) {
        super(service);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id = request.getParameter("id");
        Product product = getService().getProduct(id);

        request.setAttribute("id", product.getId());
        request.setAttribute("description", product.getDescription());
        request.setAttribute("price", product.getPrice());

        forward("productUpdate.jsp", request, response);

    }

}
