package gov.ny.its.nybe.factory;

import com.oracle.determinations.server.interview._12_2_5.rulebase.types.OdsInterviewService1225DTF17;
import com.oracle.determinations.server.interview._12_2_5.rulebase.types.OdsInterviewService1225DTF17Type;
import gov.ny.its.nybe.test.SecurityHandler;
import gov.ny.its.nybe.util.CustomHandlerResolver;
import jakarta.xml.ws.handler.Handler;

import java.util.ArrayList;
import java.util.List;

public class InterviewServiceFactory implements SoapServiceFactory {

    public InterviewServiceFactory() {
    }

    @Override
    public OdsInterviewService1225DTF17Type createService() {
        OdsInterviewService1225DTF17 service = new OdsInterviewService1225DTF17();

        List<Handler> handlerChain = new ArrayList<>();
        handlerChain.add(new SecurityHandler());
        CustomHandlerResolver handlerResolver = new CustomHandlerResolver(handlerChain);
        service.setHandlerResolver(handlerResolver);

        return service.getOdsInterviewService1225DTF17();
    }
}
