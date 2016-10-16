package net.swiftos.eventposter.Impls.ViewEvent.Entity;

import net.swiftos.eventposter.Core.Injecter;
import net.swiftos.eventposter.Exception.EventInvokeException;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Vector;

public class DynamicHandler implements InvocationHandler {

    private Vector<Class> handlerTypes = new Vector<>();
    private final HashMap<String, Method> methodMap = new HashMap<String, Method>(
            1);


    public void addMethod(String name, Method method) {
        methodMap.put(name, method);
    }


    public void addHandler(Class handler) {
        if (!handlerTypes.contains(handler))
            handlerTypes.add(handler);
    }

    public void rmHandler(Class handler){
        handlerTypes.remove(handler);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        String methodName = method.getName();
        method = methodMap.get(methodName);
        if (method != null) {
            for (Class handlerType:handlerTypes) {
                Vector handlers = Injecter.getInsts(handlerType);
                if (handlers == null) continue;
                for (Object handler:handlers) {
                    try {
                        method.invoke(handler, args);
                    } catch (Exception e){
                        throw new EventInvokeException(e.getMessage());
                    }
                }
            }
        }
        return null;
    }
}
