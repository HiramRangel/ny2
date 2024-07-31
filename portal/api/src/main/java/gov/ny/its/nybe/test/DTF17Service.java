package gov.ny.its.nybe.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.oracle.determinations.server.interview._12_2_5.rulebase.types.BooleanControl;
import com.oracle.determinations.server.interview._12_2_5.rulebase.types.ContainerControl;
import com.oracle.determinations.server.interview._12_2_5.rulebase.types.ControlBase;
import com.oracle.determinations.server.interview._12_2_5.rulebase.types.InterviewScreen;
import com.oracle.determinations.server.interview._12_2_5.rulebase.types.InvestigateRequest;
import com.oracle.determinations.server.interview._12_2_5.rulebase.types.InvestigateResponse;
import com.oracle.determinations.server.interview._12_2_5.rulebase.types.LabelControl;
import com.oracle.determinations.server.interview._12_2_5.rulebase.types.OdsInterviewService1225DTF17Type;
import com.oracle.determinations.server.interview._12_2_5.rulebase.types.StartInterviewRequest;
import com.oracle.determinations.server.interview._12_2_5.rulebase.types.StartInterviewResponse;
import com.oracle.determinations.server.interview._12_2_5.rulebase.types.TextControl;
import com.oracle.determinations.server.interview._12_2_5.rulebase.types.VersionInfoType;
import com.oracle.xmlns.connector.webservice._12_2_1.data.types.LoadData;

import gov.ny.its.nybe.factory.InterviewServiceFactory;
import gov.ny.its.nybe.factory.SoapServiceFactory;
import gov.ny.its.nybe.models.BooleanControlDTO;
import gov.ny.its.nybe.models.ContainerControlDTO;
import gov.ny.its.nybe.models.ControlBaseDTO;
import gov.ny.its.nybe.models.InvestigateRequestDTO;
import gov.ny.its.nybe.models.LabelControlDTO;
import gov.ny.its.nybe.models.TextControlDTO;
import gov.ny.its.nybe.util.InterviewObjectSerializer;
import jakarta.xml.ws.BindingProvider;

@Service
public class DTF17Service {
    private OdsInterviewService1225DTF17Type port;
    SoapServiceFactory factory;

    public DTF17Service() {
        this.factory = new InterviewServiceFactory();
        this.port = (OdsInterviewService1225DTF17Type) factory.createService();
    }

    public String startInterview() {
        String jsonResponse = "";

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

    public String investigate(String requestJson) throws JsonProcessingException {
        // Enable session maintenance
        Map < String, Object > requestContext = ((BindingProvider) port).getRequestContext();
        requestContext.put(BindingProvider.SESSION_MAINTAIN_PROPERTY, true);

        // Deserialize JSON to DTO
        InvestigateRequestDTO investigateRequestDTO = deSerializeRequest(requestJson);

        // Map DTO to actual InvestigateRequest object
        InvestigateRequest investigateRequest = mapToInvestigateRequest(investigateRequestDTO);

        // Call the investigate method from the SOAP client
        InvestigateResponse investigateResponse = port.investigate(investigateRequest);

        // Serialize the response to JSON
        return serializeResponse(investigateResponse);
    }

    private String serializeResponse(Object response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        InterviewObjectSerializer ioSerializer = new InterviewObjectSerializer();
        module.addSerializer(Object.class, ioSerializer);
        mapper.registerModule(module);

        return mapper.writeValueAsString(response);
    }

    private InvestigateRequestDTO deSerializeRequest(String requestJson) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(requestJson, InvestigateRequestDTO.class);
    }

    private InvestigateRequest mapToInvestigateRequest(InvestigateRequestDTO dto) {
        InvestigateRequest request = new InvestigateRequest();
        request.setGoalState(dto.getGoalState());

        InterviewScreen screen = new InterviewScreen();
        screen.setTextControlOrDateControlOrDatetimeControl(mapControls(dto.getScreen().getControls()));
        request.setScreen(screen);

        VersionInfoType versionInfo = new VersionInfoType();
        versionInfo.setBuildTime(dto.getVersionInfo().getBuildTime());
        versionInfo.setDeploymentVersion(dto.getVersionInfo().getDeploymentVersion());
        versionInfo.setVersionUid(dto.getVersionInfo().getVersionUid());        

        return request;
    }

    private List<ControlBase> mapControls(List<ControlBaseDTO> dtoControls) {
        List <ControlBase> controls = new ArrayList<>();
        for (ControlBaseDTO dtoControl: dtoControls) {
            if (dtoControl instanceof LabelControlDTO) {
                LabelControl control = new LabelControl();
                control.setId(dtoControl.getId());
                control.setCaption(dtoControl.getCaption());
                control.setStyle(((LabelControlDTO) dtoControl).getStyle());
                control.setWidth(((LabelControlDTO) dtoControl).getWidth());
                controls.add(control);
            } else if (dtoControl instanceof ContainerControlDTO) {
                ContainerControl control = new ContainerControl();
                control.setId(dtoControl.getId());
                control.setCaption(dtoControl.getCaption());
                control.setControlState(mapControls(((ContainerControlDTO) dtoControl).getControls()));
                controls.add(control);
            } else if (dtoControl instanceof TextControlDTO) {
                TextControl control = new TextControl();
                control.setId(dtoControl.getId());
                control.setCaption(dtoControl.getCaption());
                control.setAttributeId(((TextControlDTO) dtoControl).getAttributeId());
                control.setControlState(((TextControlDTO) dtoControl).getControlState());
                control.setControlOrientation(((TextControlDTO) dtoControl).getControlOrientation());
                controls.add(control);
            } else if (dtoControl instanceof BooleanControlDTO) {
                BooleanControl control = new BooleanControl();
                control.setId(dtoControl.getId());
                control.setCaption(dtoControl.getCaption());
                control.setAttributeId(((BooleanControlDTO) dtoControl).getAttributeId());
                control.setCurrentValue(((BooleanControlDTO) dtoControl).isCurrentValue());
                controls.add(control);
            }
        }
        return controls;
    }
}