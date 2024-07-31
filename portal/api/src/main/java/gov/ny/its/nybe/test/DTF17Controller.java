package gov.ny.its.nybe.test;

import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class DTF17Controller  {

	private final DTF17Service dtf17Service;
	 
    @Autowired
    public DTF17Controller(DTF17Service dtf17Service) {
        this.dtf17Service = dtf17Service;
    }
    
	@RequestMapping(value = "/startInterview", method = RequestMethod.GET, produces="application/json")
	public String getInterview()
	{
		return dtf17Service.startInterview();		
	}
	
	@PostMapping(value = "/investigate")
	public String postInvestigate(@RequestBody String request, BindingResult result) throws JsonProcessingException
	{
		return dtf17Service.investigate(request);		
	}
}


