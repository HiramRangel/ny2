package gov.ny.its.nybe.test;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.oracle.determinations.server.interview._12_2_5.rulebase.types.InvestigateRequest;
import com.oracle.determinations.server.interview._12_2_5.rulebase.types.InvestigateResponse;
import com.oracle.determinations.server.interview._12_2_5.rulebase.types.OdsInterviewService1225DTF17Type;
import com.oracle.determinations.server.interview._12_2_5.rulebase.types.StartInterviewRequest;
import com.oracle.determinations.server.interview._12_2_5.rulebase.types.StartInterviewResponse;
import com.oracle.xmlns.connector.webservice._12_2_1.data.types.LoadData;

import gov.ny.its.nybe.factory.InterviewServiceFactory;
import gov.ny.its.nybe.factory.SoapServiceFactory;
import gov.ny.its.nybe.util.InterviewObjectSerializer;
import gov.ny.its.nybe.util.InvestigateRequestDTO;
import jakarta.xml.ws.BindingProvider;

@Service
public class DTF17Service {
	private OdsInterviewService1225DTF17Type port;
	SoapServiceFactory factory;

	public DTF17Service(){
		this.factory = new InterviewServiceFactory();
		this.port = (OdsInterviewService1225DTF17Type) factory.createService();
	}
     
	public String startInterview()
	{
		String jsonResponse="";
		
	        try {

	            // Create request object
	            StartInterviewRequest request = new StartInterviewRequest();
	            // Populate the request object with required data if needed
	            request.setShowVersion(true); // Example field

	            // Create a new LoadData object if necessary and set it to the request
	            LoadData loadData = new LoadData();
	            // Populate loadData with necessary information
	            request.setData(loadData);

	            // Call the startInterview method
	            StartInterviewResponse response = port.startInterview(request);
	        
				jsonResponse = serializeResponse(response);
	           
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		return jsonResponse;
	}
	
	public String investigate(String request) throws JsonProcessingException
	{
		// Create request object
		StartInterviewRequest startInterviewRequest = new StartInterviewRequest();
		// Populate the request object with required data if needed
		startInterviewRequest.setShowVersion(true); // Example field

		// Create a new LoadData object if necessary and set it to the request
		LoadData loadData = new LoadData();
		// Populate loadData with necessary information
		startInterviewRequest.setData(loadData);
		StartInterviewResponse response = port.startInterview(startInterviewRequest);
		//Enable session maintenance
		 Map<String, Object> requestContext = ((BindingProvider) port).getRequestContext();
		 requestContext.put(BindingProvider.SESSION_MAINTAIN_PROPERTY, true);

		 // Create  new investigate request
		 InvestigateRequest investigateRequest = deSerializeRequest(request);
         //InvestigateResponse investigateResponse = port.investigate(investigateRequest);
		 //String investigateResponseJson = serializeResponse(investigateResponse);

		 return new String();
	}

	private String serializeResponse(Object response) throws JsonProcessingException
	{
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		InterviewObjectSerializer ioSerializer = new InterviewObjectSerializer();
		module.addSerializer(Object.class, ioSerializer);
		mapper.registerModule(module);

		return mapper.writeValueAsString(response);
	}

	private InvestigateRequest deSerializeRequest(String request) throws JsonProcessingException
	{
		ObjectMapper mapper = new ObjectMapper();
		InvestigateRequest investigateRequest = mapper.readValue(request, InvestigateRequest.class);

		return investigateRequest;
	}
}
