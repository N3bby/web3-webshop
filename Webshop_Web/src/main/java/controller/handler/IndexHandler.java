package controller.handler;

import controller.Handler;
import controller.WebAction;
import service.ShopService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebAction("index")
public class IndexHandler extends Handler {

    public IndexHandler(ShopService service) {
        super(service);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        forward("index.jsp", request, response);
    }

}
