package gov.ny.its.nybe.util;

import java.util.ArrayList;
import java.util.List;

import com.oracle.determinations.server.interview._12_2_5.rulebase.types.ControlBase;
import com.oracle.determinations.server.interview._12_2_5.rulebase.types.InterviewScreen;

public class InvestigateRequestDTO extends InterviewScreen {

        protected List<ControlBase> controls;
            public List<ControlBase> getControls() {
        if (controls == null) {
            controls = new ArrayList<>();
        }
        return this.controls;
    }

}
