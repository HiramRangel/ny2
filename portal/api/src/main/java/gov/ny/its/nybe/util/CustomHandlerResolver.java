package gov.ny.its.nybe.util;

import jakarta.xml.ws.handler.Handler;
import jakarta.xml.ws.handler.HandlerResolver;
import jakarta.xml.ws.handler.PortInfo;
import java.util.List;

public class CustomHandlerResolver implements HandlerResolver {

    private final List<Handler> handlerChain;

    public CustomHandlerResolver(List<Handler> handlerChain) {
        this.handlerChain = handlerChain;
    }

    @Override
    public List<Handler> getHandlerChain(PortInfo portInfo) {
        return handlerChain;
    }
}
