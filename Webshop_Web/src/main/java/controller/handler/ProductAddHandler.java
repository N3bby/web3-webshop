package controller.handler;

import controller.Handler;
import controller.WebAction;
import domain.Product;
import service.ShopService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebAction("productAdd")
public class ProductAddHandler extends Handler {

    public ProductAddHandler(ShopService service) {
        super(service);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<String> errors = new ArrayList<>();

        Product product = new Product();

        String id = request.getParameter("id");
        String description = request.getParameter("description");
        String price = request.getParameter("price");

        try {
            request.setAttribute("old_id", id);
            product.setId(id);
        } catch (Exception e) {
            errors.add(e.getMessage());
        }
        try {
            request.setAttribute("old_description", description);
            product.setDescription(description);
        } catch (Exception e) {
            errors.add(e.getMessage());
        }
        try {
            request.setAttribute("old_price", price);
            product.setPrice(Double.parseDouble(price));
        } catch (NumberFormatException e) {
            errors.add("Price must be a number.");
        } catch (Exception e) {
            errors.add(e.getMessage());
        }

        if (errors.size() == 0) {
            try {
                getService().addProduct(product);
            } catch (Exception e) {
                errors.add(e.getMessage());
            }
        }

        if (errors.size() != 0) {
            request.setAttribute("errors", errors);
            forward("productAdd.jsp", request, response);
        } else {
            response.sendRedirect("Controller?action=productOverview");
        }

    }

}
