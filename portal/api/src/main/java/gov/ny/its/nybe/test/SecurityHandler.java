package gov.ny.its.nybe.test;

import java.util.Set;

import javax.xml.namespace.QName;

import gov.ny.its.nybe.util.ConfigLoader;
import jakarta.xml.soap.SOAPElement;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPFactory;
import jakarta.xml.soap.SOAPMessage;
import jakarta.xml.ws.handler.MessageContext;
import jakarta.xml.ws.handler.soap.SOAPHandler;
import jakarta.xml.ws.handler.soap.SOAPMessageContext;

public class SecurityHandler implements SOAPHandler<SOAPMessageContext> {

	 ConfigLoader configLoader = new ConfigLoader();
     String username = configLoader.getProperty("username");
     String password = configLoader.getProperty("password");

    public SecurityHandler() {
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        Boolean outboundProperty = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        if (outboundProperty) {
            try {
                SOAPMessage soapMessage = context.getMessage();
                SOAPFactory soapFactory = SOAPFactory.newInstance();
                
                // Create Security header
                SOAPElement securityElem = soapFactory.createElement("Security", "o", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
                SOAPElement usernameTokenElem = soapFactory.createElement("UsernameToken", "o", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
                SOAPElement usernameElem = soapFactory.createElement("Username", "o", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
                usernameElem.addTextNode(username);
                SOAPElement passwordElem = soapFactory.createElement("Password", "o", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
                passwordElem.addTextNode(password);

                usernameTokenElem.addChildElement(usernameElem);
                usernameTokenElem.addChildElement(passwordElem);
                securityElem.addChildElement(usernameTokenElem);
                
                soapMessage.getSOAPHeader().addChildElement(securityElem);
                soapMessage.saveChanges();
            } catch (SOAPException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return true;
    }

    @Override
    public void close(MessageContext context) {
    }

    @Override
    public Set<QName> getHeaders() {
        return null;
    }
}
