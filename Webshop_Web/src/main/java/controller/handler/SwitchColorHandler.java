package controller.handler;

import controller.Handler;
import controller.WebAction;
import service.ShopService;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebAction("switchColor")
public class SwitchColorHandler extends Handler {

    public SwitchColorHandler(ShopService service) {
        super(service);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Cookie theme = getCookieWithNameFromRequest(request, "theme");

        if (theme == null) {
            theme = new Cookie("theme", "red");
        } else {
            switch (theme.getValue()) {
                case "yellow":
                    theme.setValue("red");
                    break;
                case "red":
                    theme.setValue("yellow");
                    break;
                default:
                    theme.setValue("yellow");
                    break;
            }
        }

        response.addCookie(theme);
        response.sendRedirect("Controller?value=" + request.getParameter("redirect"));

    }

    private Cookie getCookieWithNameFromRequest(HttpServletRequest request, String name) {

        if(request.getCookies() == null) return null;

        return Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .orElse(null);

    }


}
