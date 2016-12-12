package controller.handler;

import controller.Handler;
import controller.WebAction;
import service.ShopService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebAction("productOverview")
public class ProductOverviewHandler extends Handler {

    public ProductOverviewHandler(ShopService service) {
        super(service);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("productList", getService().getProducts());
        forward("productOverview.jsp", request, response);

    }

}
