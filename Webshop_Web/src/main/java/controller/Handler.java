package controller;

import service.ShopService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class Handler {

    private ShopService service;

    public Handler(ShopService service) {
        this.service = service;
    }

    public ShopService getService() {
        return service;
    }

    public abstract void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

    public void forward(String url, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(url).forward(request, response);
    }

}