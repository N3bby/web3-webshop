package controller;

import org.reflections.Reflections;
import service.ShopService;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HandlerFactory {

    private Map<String, Handler> handlers = new HashMap<>();

    public HandlerFactory(ShopService service) {
        Set<Class<?>> handlerClasses = new Reflections("controller.handler").getTypesAnnotatedWith(WebAction.class);
        for (Class<?> c : handlerClasses) {
            try {
                String action = c.getAnnotation(WebAction.class).value();
                Handler handler = (Handler) c.getConstructor(ShopService.class).newInstance(service);
                handlers.put(action, handler);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Handler getHandler(String action) {
        return handlers.get(action) == null ?
                handlers.get("index") :
                handlers.get(action);
    }

}
